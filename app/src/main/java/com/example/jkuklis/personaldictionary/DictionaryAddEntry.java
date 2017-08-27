package com.example.jkuklis.personaldictionary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DictionaryAddEntry extends AppCompatActivity implements
        View.OnClickListener {

    private int dictId;
    public static final String DICT_ID = "-1";

    private List<Language> langs = new ArrayList<Language>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_add_entry);

        Intent intent = getIntent();
        String dictIdString = intent.getStringExtra(DictionaryMain.DICT_ID);
        dictId = Integer.parseInt(dictIdString);

        LinearLayout rel1 = (LinearLayout) findViewById(R.id.abbreviations);
        LinearLayout rel2 = (LinearLayout) findViewById(R.id.words);

        DbHelper db = new DbHelper(getApplicationContext());

        langs = db.getDictLanguages(dictId);

        for (int i = 0; i < langs.size(); i++) {
            TextView abbr = new TextView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    500, 200);
            layoutParams.setMargins(20, 20, 20, 20);
            abbr.setLayoutParams(layoutParams);

            abbr.setText(langs.get(i).getName());
            rel1.addView(abbr);

            EditText word = new EditText(this);
//            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
//                    500, 210);
//            layoutParams2.setMargins(0, 0, 0, 0);
            word.setLayoutParams(layoutParams);

            rel2.addView(word);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                break;
            case R.id.button4:
                break;
        }
    }
}
