package com.example.jkuklis.personaldictionary;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

public class DictionaryCreate extends Activity implements
        View.OnClickListener {
    private MyListAdapter adapter;

    private String[] arrTemp;
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

    }

    private class MyListAdapter extends BaseAdapter implements ListAdapter {
        private ArrayList<String> languages = new ArrayList<>();

        public MyListAdapter() {
            super();
            languages.add("");
            languages.add("");
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

            holder.lang_name.setText(languages.get(position));
            holder.lang_abbr.setText(arrTemp[position]);
            holder.lang_abbr.addTextChangedListener(new TextWatcher() {


                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                        int arg3) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    // TODO Auto-generated method stub
                    arrTemp[holder.ref] = arg0.toString();
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
            languages.add("");

            this.notifyDataSetChanged();

        }

        public void delete_language(int position) {
            languages.remove(position);

            this.notifyDataSetChanged();
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}