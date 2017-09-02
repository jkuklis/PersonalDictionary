package com.example.jkuklis.personaldictionary;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.OptionalPendingResult;

import java.util.ArrayList;
import java.util.List;

public class DictionaryCreate extends AppCompatActivity implements
        View.OnClickListener {
    private final String LANG_LOWER_LIMIT = "Min 1 language!";
    private final String LANG_UPPER_LIMIT = "Max 5 languages!";
    private final String REQUIREMENTS = "Fill all the fields!";
    private final String CONNECTION_FAIL = "Failed to connect";
    private final String DB_NAME_EMPTY = "Database name empty!";
    private final String DICT_ID = "-1";

    //    private String[] arrTemp;
    private List<LanguageBuilder> languages = new ArrayList<>();
    private LanugageListAdapter adapter;
    private TextView warning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_create);

        adapter = new LanugageListAdapter();

        ListView listView = (ListView) findViewById(R.id.listViewMain);
        listView.setAdapter(adapter);

        FloatingActionButton myFab = (FloatingActionButton)  findViewById(R.id.addLanguage);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                adapter.add_language();
            }
        });

        warning = (TextView) findViewById(R.id.warningPlaceholder);

        Button createBtn = (Button) findViewById(R.id.createDictionary);
        createBtn.setOnClickListener(this);
    }

    private class LanguageBuilder {
        private String name = "";
        private String abbr = "";

        LanguageBuilder(String name, String abbr) {
            this.name = name;
            this.abbr = abbr;
        }

        LanguageBuilder() {}

        public void setName(String name) {
            this.name = name;
        }
        public void setAbbr(String abbr) {
            this.abbr = abbr;
        }

        public String getName() {
            return this.name;
        }
        public String getAbbr() {
            return this.abbr;
        }
    }

    private class LanugageListAdapter extends BaseAdapter implements ListAdapter {
        public LanugageListAdapter() {
            super();
            languages.add(new LanguageBuilder("", ""));
            languages.add(new LanguageBuilder("", ""));
        }

        @Override
        public int getCount() {
            if(languages != null && languages.size() != 0){
                return languages.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return languages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

//            arrTemp = new String[languages.size()];

            final ViewHolder holder;
            if (convertView == null) {

                holder = new ViewHolder();
                LayoutInflater inflater = DictionaryCreate.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.language_list_item, null);
                holder.langName = (EditText) convertView.findViewById(R.id.language_name);
                holder.langAbbr = (EditText) convertView.findViewById(R.id.language_abbreviation);
                holder.deleteBtn = (FloatingActionButton) convertView.findViewById(R.id.delete_language_button);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete_language(position);
                }
            });

            holder.langName.setText(languages.get(position).getName());
            holder.langName.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    languages.get(position).setName(String.valueOf(arg0));
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
//                    warning.setVisibility(View.INVISIBLE);
                }

                @Override
                public void afterTextChanged(Editable arg0) {
//                    arrTemp[holder.ref] = arg0.toString();
                }
            });

            holder.langName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    warning.setVisibility(View.INVISIBLE);
                }
            });

            holder.langAbbr.setText(languages.get(position).getAbbr());
            holder.langAbbr.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    languages.get(position).setAbbr(String.valueOf(arg0));
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                }

                @Override
                public void afterTextChanged(Editable arg0) {
//                    arrTemp[holder.ref] = arg0.toString();
                }
            });

            holder.langAbbr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    warning.setVisibility(View.INVISIBLE);
                }
            });

            return convertView;
        }

        private class ViewHolder {
            EditText langName;
            EditText langAbbr;
            FloatingActionButton deleteBtn;
        }

        public void add_language() {
            if (languages.size() < 5) {

                for (int i = 0; i < languages.size(); i++) {
                }

                languages.add(new LanguageBuilder());

                this.notifyDataSetChanged();
            } else {

                warning.setText(LANG_UPPER_LIMIT);
                warning.setVisibility(View.VISIBLE);
            }
        }

        public void delete_language(int position) {
            if (languages.size() > 1) {
                languages.remove(position);
                warning.setVisibility(View.INVISIBLE);
                this.notifyDataSetChanged();

            } else {
                warning.setText(LANG_LOWER_LIMIT);
                warning.setVisibility(View.VISIBLE);

            }
        }
    }

    private boolean check_requirements() {
        for (int i = 0; i < languages.size(); i++) {
            LanguageBuilder lang = languages.get(i);
            if (lang.getAbbr().equals("") || lang.getName().equals("")) {
                return false;
            }
        }
        return true;
    }

    private void create_language() {

        boolean requirements = check_requirements();

        if (requirements) {

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(DictionaryCreate.this);

            alertDialog.setTitle("Name your dictionary");

            final EditText input = new EditText(DictionaryCreate.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setLayoutParams(lp);

            alertDialog.setView(input);

            alertDialog.setPositiveButton("Approve", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {
                    String dbName = input.getText().toString();

                    if (dbName.equals("")) {

                        warning.setText(DB_NAME_EMPTY);
                        warning.setVisibility(View.VISIBLE);

                    } else {
                        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(HelloScreen.getGoogleApi());
                        if (opr.isDone()) {
                            GoogleSignInResult result = opr.get();
                            GoogleSignInAccount acct = result.getSignInAccount();

                            DbHelper db = new DbHelper(getApplicationContext());
                            Dictionary dict = new Dictionary(dbName, acct.getId());

                            int dictId = (int) db.createDictionary(dict);

                            List<Language> langs = new ArrayList<Language>();

                            for (int i = 0; i < languages.size(); i++) {
                                LanguageBuilder langBuilder = languages.get(i);
                                Language lang = new Language(i, langBuilder.getAbbr(), langBuilder.getName());
                                langs.add(lang);
                            }

                            for (Language lang : langs) {
                                lang.setDictId(dictId);
                                db.createLanguage(lang);
                            }

                            Intent intent = new Intent(DictionaryCreate.this, DictionaryShow.class);
                            intent.putExtra(DICT_ID, String.valueOf(dictId));

                            startActivity(intent);

                        } else {
                            warning.setText(CONNECTION_FAIL);
                            warning.setVisibility(View.VISIBLE);
                        }

                    }
                }
            });

            alertDialog.show();

        } else {
            warning.setText(REQUIREMENTS);
            warning.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createDictionary:
                create_language();
                break;
        }
    }
}