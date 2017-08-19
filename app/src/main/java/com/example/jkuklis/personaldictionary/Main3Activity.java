package com.example.jkuklis.personaldictionary;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {

    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        adapter = new CustomAdapter();

        ListView listView = (ListView) findViewById(R.id.customlist);
        listView.setAdapter(adapter);
    }

    private class CustomAdapter extends BaseAdapter {
        private ArrayList<String> languages = new ArrayList<>();

        public CustomAdapter() {
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
        public View getView(int position, View view, ViewGroup parent) {

            ListViewHolder viewHolder;
            if (view == null) {
                viewHolder = new ListViewHolder();
                LayoutInflater inflater = Main3Activity.this.getLayoutInflater();
                view = inflater.inflate(R.layout.list_item, null, true);
                viewHolder.itmName = (TextView) view.findViewById(R.id.Item_name);
                viewHolder.itmPrice = (EditText) view.findViewById(R.id.Item_price);
                view.setTag(viewHolder);

            } else {
                viewHolder = (ListViewHolder) view.getTag();
// loadSavedValues();
            }

            viewHolder.itmName.setText("as");
            viewHolder.itmPrice.setId(position);
            viewHolder.id = position;
            if (languages != null && languages.get(position) != null) {
                viewHolder.itmPrice.setText(languages.get(position));
            } else {
                viewHolder.itmPrice.setText(null);
            }

// Add listener for edit text

            viewHolder.itmPrice
                    .setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {

/*

 * When focus is lost save the entered value for

 * later use

 */
                            if (!hasFocus) {
                                int itemIndex = v.getId();
                                String enteredPrice = ((EditText) v).getText()
                                        .toString();
                                languages.add(itemIndex, enteredPrice);
                            }
                        }
                    });

            return view;

        }

        private class ListViewHolder {
            TextView itmName;
            EditText itmPrice;
            int id;
        }
    }
}
