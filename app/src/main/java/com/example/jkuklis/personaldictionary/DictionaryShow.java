package com.example.jkuklis.personaldictionary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DictionaryShow extends AppCompatActivity {

    private TextView PositionTextView;
    private TextView IDTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_show);

        Intent intent = getIntent();

        PositionTextView = (TextView) findViewById(R.id.position);
        IDTextView = (TextView) findViewById(R.id.id);

        PositionTextView.setText(intent.getStringExtra(DictionaryMain.POSITION_INFO));
        IDTextView.setText(intent.getStringExtra(DictionaryMain.ID_INFO));

    }
}
