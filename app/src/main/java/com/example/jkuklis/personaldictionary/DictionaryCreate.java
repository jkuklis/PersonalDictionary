package com.example.jkuklis.personaldictionary;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DictionaryCreate extends AppCompatActivity implements
        View.OnClickListener {
    private final String LANG_LOWER_LIMIT = "Min 1 language!";
    private final String LANG_UPPER_LIMIT = "Max 5 languages!";
    private final String REQUIREMENTS = "Fill all the fields!";

    private MyListAdapter adapter;

    private TextView warning;

    private String[] arrTemp;

    private ArrayList<Language> languages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_create);

        adapter = new MyListAdapter();

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

    private class Language {
        private String name = "";
        private String abbr = "";

        Language(String name, String abbr) {
            this.name = name;
            this.abbr = abbr;
        }

        Language() {

        }

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

    private class MyListAdapter extends BaseAdapter implements ListAdapter {
        public MyListAdapter() {
            super();
            languages.add(new Language("", ""));
            languages.add(new Language("", ""));
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            if(languages != null && languages.size() != 0){
                return languages.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return languages.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            arrTemp = new String[languages.size()];

            final ViewHolder holder;
            if (convertView == null) {

                holder = new ViewHolder();
                LayoutInflater inflater = DictionaryCreate.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.language_list_item, null);
                holder.lang_name = (EditText) convertView.findViewById(R.id.language_name);
                holder.lang_abbr = (EditText) convertView.findViewById(R.id.language_abbreviation);
                holder.del_btn = (FloatingActionButton) convertView.findViewById(R.id.delete_language_button);

                convertView.setTag(holder);

            } else {

                holder = (ViewHolder) convertView.getTag();
            }

            holder.ref = position;

            holder.del_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //MyListAdapter.this.delete_language(position);
                    delete_language(position);
                }
            });

            holder.lang_name.setText(languages.get(position).getName());
            holder.lang_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    //TextView txt = (TextView) findViewById(R.id.languageHeader);
                    //txt.setText(String.valueOf(arg0) + " " + String.valueOf(arg1) + " " + String.valueOf(arg2) + " " + String.valueOf(arg3));

                    languages.get(position).setName(String.valueOf(arg0));
                    //txt.setText(languages.get(position));
                    // TODO Auto-generated method stub

                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {
                    // TODO Auto-generated method stub
                    TextView warning = (TextView) findViewById(R.id.warningPlaceholder);
                    warning.setVisibility(View.INVISIBLE);

                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    // TODO Auto-generated method stub
                    arrTemp[holder.ref] = arg0.toString();
                }
            });

            holder.lang_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    warning.setVisibility(View.INVISIBLE);
                }
            });

            holder.lang_abbr.setText(languages.get(position).getAbbr());
            holder.lang_abbr.addTextChangedListener(new TextWatcher() {


                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

                    languages.get(position).setAbbr(String.valueOf(arg0));
                    // TODO Auto-generated method stub

                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    // TODO Auto-generated method stub
                    arrTemp[holder.ref] = arg0.toString();
                }
            });

            holder.lang_abbr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    warning.setVisibility(View.INVISIBLE);
                }
            });

            return convertView;
        }

        private class ViewHolder {
            EditText lang_name;
            EditText lang_abbr;
            FloatingActionButton del_btn;
            int ref;
        }

        public void add_language() {
            if (languages.size() < 5) {

                for (int i = 0; i < languages.size(); i++) {
                }

                languages.add(new Language());

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
            Language lang = languages.get(i);
            if (lang.getAbbr().equals("") || lang.getName().equals("")) {
                return false;
            }
        }
        return true;
    }

    private void create_language() {

        boolean requirements = check_requirements();

        if (requirements) {


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