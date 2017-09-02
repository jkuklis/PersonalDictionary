package com.example.jkuklis.personaldictionary;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.OptionalPendingResult;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DictionaryShow extends AppCompatActivity
    implements View.OnClickListener {
    public static final String DICT_ID = "-1";

    private int dictId;
    private List<Language> langs = new ArrayList<Language>();
    private List<Entry> entries = new ArrayList<Entry>();
    private List<ColumnValues> values = new ArrayList<ColumnValues>();
    private DbHelper db;
    private RowComparator comparator;
    private EntriesAdapter adapter;
    private TextView header;
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_show);

        Intent intent = getIntent();

        String dictIdString = intent.getStringExtra(DictionariesList.DICT_ID);

        dictId = Integer.parseInt(dictIdString);

        db = new DbHelper(getApplicationContext());

        String dictionaryName = db.getDictionary(dictId).getName();

        header = (TextView) findViewById(R.id.header);
        header.setText(dictionaryName);

        status = (TextView) findViewById(R.id.showStatus);
        status.setVisibility(View.INVISIBLE);

        langs = db.getDictLanguages(dictId);

        entries = db.getDictEntriesSorted(dictId);

        int columns = langs.size() + 1;

        LinearLayout layout = (LinearLayout) findViewById(R.id.tableHeaders);
        for (int i = 0; i < columns; i++) {
            TextView textView = new TextView(DictionaryShow.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    1400/(columns), LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(20, 20, 20, 20);
            textView.setLayoutParams(layoutParams);
            textView.setText(langs.get(i).getAbbr());
            final int j = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sortLanguages(j);
                }
            });
            layout.addView(textView);
        }

        TextView textView = new TextView(DictionaryShow.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                1400/(columns), LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(20, 20, 20, 20);
        textView.setLayoutParams(layoutParams);
        textView.setText("del");
        layout.addView(textView);

        for (Entry entry : entries) {
            ColumnValues cv = new ColumnValues(entry.getId());
            List<Word> words = db.getEntryWords(entry.getId());
            for (Word word : words) {
                cv.add(word.getString());
            }
            values.add(cv);
        }

        ListView listView = (ListView) findViewById(R.id.wordsList);
        adapter = new EntriesAdapter(DictionaryShow.this);
        listView.setAdapter(adapter);
    }

    private void sortLanguages(int position) {
        comparator.setPosition(position);

        Collections.sort(values, comparator);

        adapter.notifyDataSetChanged();
    }

    private class RowComparator implements Comparator<ColumnValues> {
        int position;

        public int compare(ColumnValues cv1, ColumnValues cv2) {
            return cv1.get(position).compareTo(cv2.get(position));
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

    private class ColumnValues {
        public List<String> columns = new ArrayList<>();
        public int entryId;

        ColumnValues(int entryId) {
            this.entryId = entryId;
        }

        public void add(String value) {
            columns.add(value);
        }

        public String get(int position) {
            return columns.get(position);
        }
    }

    private class EntriesAdapter extends ArrayAdapter<ColumnValues> {
        private DictionaryShow activity;

        public EntriesAdapter(DictionaryShow activity) {
            super(activity, 0, values);

            this.activity = activity;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            int numOfColumns = values.get(position).columns.size();

            ViewHolder holder;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = new LinearLayout(activity);

                holder.layout = (LinearLayout) convertView;
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            for (int i = 0; i < numOfColumns; i++) {
                TextView textView = new TextView(activity);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        1400/(numOfColumns + 1), LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(20, 20, 20, 20);
                textView.setLayoutParams(layoutParams);

                textView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                    @Override
                    public void afterTextChanged(Editable editable) {}
                });

                holder.layout.addView(textView);
            }

            Button deleteBtn = new Button(activity);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    80, 100);
            layoutParams.setMargins(20, 20, 20, 20);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteEntry(position);
                }
            });
            deleteBtn.setLayoutParams(layoutParams);
            holder.layout.addView(deleteBtn);

            for (int i = 0; i < numOfColumns; i++) {
                ((TextView) holder.layout.getChildAt(i)).setText(values.get(position).get(i));
            }

            return convertView;
        }

        private void deleteEntry(int position) {
            db.deleteEntry(values.get(position).entryId);
            values.remove(position);
            this.notifyDataSetChanged();
        }

        private class ViewHolder {
            LinearLayout layout;
        }
    }

    private void addEntries() {
        Intent intent = new Intent(this, DictionaryAddEntry.class);
        intent.putExtra(DICT_ID, String.valueOf(dictId));
        startActivity(intent);
    }

    void deleteDictionary() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DictionaryShow.this);

        alertDialog.setTitle("Do you really want to delete this dictionary?");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                DbHelper db = new DbHelper(getApplicationContext());
                db.deleteDict(dictId);

                Intent intent = new Intent(DictionaryShow.this, DictionariesList.class);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {}
        });
    }

    void exportDictionary() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DictionaryShow.this);

        alertDialog.setTitle("Name your file");

        final EditText input = new EditText(DictionaryShow.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        alertDialog.setView(input);

        alertDialog.setPositiveButton("Approve", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                try {
                    Writer output = null;
                    File file = new File("storage/sdcard/" + input.getText() + ".json");
                    output = new BufferedWriter(new FileWriter(file));
                    output.write("TODO");
                    output.close();
                    status.setText("Export successful");

                } catch (Exception e) {
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addEntriesButton:
                addEntries();
                break;
            case R.id.deleteDictionaryButton:
                deleteDictionary();
                break;
        }
    }
}
