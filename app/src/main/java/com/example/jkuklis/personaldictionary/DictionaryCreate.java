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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

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

    private class MyListAdapter extends BaseAdapter{
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
        public View getView(int position, View convertView, ViewGroup parent) {

            arrTemp = new String[languages.size()];

            //ViewHolder holder = null;
            final ViewHolder holder;
            if (convertView == null) {

                holder = new ViewHolder();
                LayoutInflater inflater = DictionaryCreate.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.language_list_item, null);
                holder.textView1 = (EditText) convertView.findViewById(R.id.textView1);
                holder.editText1 = (EditText) convertView.findViewById(R.id.editText1);

                convertView.setTag(holder);

            } else {

                holder = (ViewHolder) convertView.getTag();
            }

            holder.ref = position;

            holder.textView1.setText(languages.get(position));
            holder.editText1.setText(arrTemp[position]);
            holder.editText1.addTextChangedListener(new TextWatcher() {

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
            EditText textView1;
            EditText editText1;
            int ref;
        }

        public void add_language() {
            languages.add("");

            this.notifyDataSetChanged();
//            ListView listView = (ListView) findViewById(R.id.listViewMain);
//            listView.setAdapter(new MyListAdapter());
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.addLanguage:
//                adapter.add_language();
//                TextView txt = (TextView) findViewById(R.id.languageHeader);
//                txt.setText("das");
//                break;
        }
    }
}