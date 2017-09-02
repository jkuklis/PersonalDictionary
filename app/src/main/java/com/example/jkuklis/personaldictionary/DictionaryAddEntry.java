package com.example.jkuklis.personaldictionary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DictionaryAddEntry extends AppCompatActivity implements
        View.OnClickListener {

    public static final String DICT_ID = "-1";
    private static final String REQUIREMENTS = "Please fill all word slots!";
    private static final String SUCCESS = "Entry added! Count: ";

    private List<Language> langs = new ArrayList<Language>();
    private List<String> newWords = new ArrayList<String>();
    private TextView status;
    private int dictId;
    private int counter;
    DbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_add_entry);

        counter = 0;

        status = (TextView) findViewById(R.id.addStatus);
        status.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        String dictIdString = intent.getStringExtra(DictionariesList.DICT_ID);
        dictId = Integer.parseInt(dictIdString);

        LinearLayout languages = (LinearLayout) findViewById(R.id.languages);
        LinearLayout words = (LinearLayout) findViewById(R.id.words);

        db = new DbHelper(getApplicationContext());

        langs = db.getDictLanguages(dictId);

        for (int i = 0; i < langs.size(); i++) {
            newWords.add("");

            TextView lang = new TextView(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    500, 200);
            layoutParams.setMargins(20, 20, 20, 20);
            lang.setLayoutParams(layoutParams);

            lang.setText(langs.get(i).getName());
            languages.addView(lang);

            final EditText word = new EditText(this);
            word.setLayoutParams(layoutParams);

            final int j = i;

            word.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    newWords.set(j, word.getText().toString());
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    status.setVisibility(View.INVISIBLE);
                }

                @Override
                public void afterTextChanged(Editable arg0) {}
            });

            words.addView(word);
        }
    }

    private boolean check_requirements() {
        for (String word : newWords) {
            if (word.equals("")) {
                return false;
            }
        }
        return true;
    }

    private void addEntry() {
        if (!check_requirements()) {
            Entry entry = new Entry(dictId, dictId);


            counter++;
        } else {
            status.setText(REQUIREMENTS);
            status.setVisibility(View.VISIBLE);
        }
    }

    private void goBack() {
        Intent intent = new Intent(this, DictionaryShow.class);
        intent.putExtra(DICT_ID, String.valueOf(dictId));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addEntryButton:
                addEntry();
                break;
            case R.id.goBackButton:
                goBack();
                break;
        }
    }
}
