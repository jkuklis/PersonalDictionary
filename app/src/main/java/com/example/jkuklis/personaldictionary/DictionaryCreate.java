package com.example.jkuklis.personaldictionary;

/*
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DictionaryCreate extends AppCompatActivity {

    private ArrayList<String> languages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_create);

        languages.add("");
        languages.add("");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.language_name, languages);
        ListView listView = (ListView) findViewById(R.id.languageList);
        listView.setAdapter(adapter);
    }
}
*/

import android.app.Activity;
import android.os.Bundle;
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

    private ArrayList<String> languages = new ArrayList<>();
    private MyListAdapter adapter;

    private String[] arrTemp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_create);

        languages.add("");
        languages.add("");
        arrTemp = new String[languages.size()];

        adapter = new MyListAdapter();

        ListView listView = (ListView) findViewById(R.id.listViewMain);
        listView.setAdapter(adapter);

    }

    private class MyListAdapter extends BaseAdapter{

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

            //ViewHolder holder = null;
            final ViewHolder holder;
            if (convertView == null) {

                holder = new ViewHolder();
                LayoutInflater inflater = DictionaryCreate.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.language_list_item, null);
                holder.textView1 = (TextView) convertView.findViewById(R.id.textView1);
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
            TextView textView1;
            EditText editText1;
            int ref;
        }


    }

    private void add_language() {
        languages.add("");
        arrTemp = new String[languages.size()];
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addLanguage:
                add_language();
                break;
        }
    }
}