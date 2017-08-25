package com.example.jkuklis.personaldictionary;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DictionaryShow extends AppCompatActivity {

    private int dictId;
    private List<Language> langs = new ArrayList<Language>();
    private List<Entry> entries = new ArrayList<Entry>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_show);

        Intent intent = getIntent();

        String dictIdString = intent.getStringExtra(DictionaryMain.DICT_ID);

        dictId = Integer.parseInt(dictIdString);

        DbHelper db = new DbHelper(getApplicationContext());

        langs = db.getDictLanguages(dictId);

        entries = db.getDictEntriesSorted(dictId);

        List<ColumnValues> toDisplay = new ArrayList<ColumnValues>();

        ColumnValues cv1 = new ColumnValues();
        cv1.columns.add("ads");
        cv1.columns.add("va");
        toDisplay.add(cv1);

        for (int i = 0; i < 50; i++) {
            ColumnValues cv2 = new ColumnValues();
            cv2.columns.add("awdaca");
            cv2.columns.add("acz");
            cv2.columns.add("czzz");
            cv2.columns.add("czzzs");
            toDisplay.add(cv2);
        }

        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(new EntriesAdapter2(DictionaryShow.this, toDisplay));

        // need column view
        // button to edit
    }

    private class ColumnValues {
        public List<String> columns = new ArrayList<>();
    }

    private class EntriesAdapter2 extends BaseAdapter {
        private List<ColumnValues> values;

        public EntriesAdapter2(Context context, List<ColumnValues> values) {
            super();
            this.values = values;
        }

        @Override
        public int getCount() {
            return values.size();
        }

        @Override
        public Object getItem(int position) {
            return values.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                LayoutInflater inflater = DictionaryShow.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.lin_layout, null);
                holder.layout = (LinearLayout) convertView.findViewById(R.id.layout);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder)convertView.getTag();
            }
        }

        private class ViewHolder {
            LinearLayout layout;
        }
    }

    private class EntriesAdapter extends ArrayAdapter<ColumnValues> {
        private DictionaryShow activity;
        private List<ColumnValues> values;

        public EntriesAdapter(DictionaryShow activity, List<ColumnValues> values) {
            super(activity, 0, values);

            this.activity = activity;
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            int numOfColumns = values.get(position).columns.size();

            LinearLayout layout;

            if (convertView == null) {
                convertView = new LinearLayout(activity);

                layout = (LinearLayout) convertView;
                for (int i = 0; i < numOfColumns; i++) {
                    TextView textView = new TextView(activity);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            1500/numOfColumns, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(20, 20, 20, 20);
                    textView.setLayoutParams(layoutParams);

                    layout.addView(textView);
                }
            } else {
                convertView.getTag();
            }

            layout = (LinearLayout) convertView;

            for (int i = 0; i < numOfColumns; i++) {
                ((TextView) layout.getChildAt(i)).setText(values.get(position).columns.get(i));
            }

            return convertView;
        }
    }

}
