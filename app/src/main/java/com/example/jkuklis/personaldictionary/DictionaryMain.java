package com.example.jkuklis.personaldictionary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;

import java.util.ArrayList;

public class DictionaryMain extends AppCompatActivity implements
        View.OnClickListener
    {

    private static final String POSITION_INFO = "";
    private static final String ID_INFO = "";

    private static final String auth_fail = "STRANGE: Failed to authorize";
    private TextView mStatusTextView;

    private ArrayList<String> dictionaries = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_main);

        findViewById(R.id.create_dictionary).setOnClickListener(this);
        findViewById(R.id.import_dictionary).setOnClickListener(this);

        mStatusTextView = (TextView) findViewById(R.id.login_info);

        dictionaries.add("pl-en");
        dictionaries.add("pl-de");
        dictionaries.add("pl-de");
        dictionaries.add("pl-de");
        dictionaries.add("pl-de");
        dictionaries.add("pl-de");
        dictionaries.add("pl-de");
        dictionaries.add("pl-de");
        dictionaries.add("pl-de");
        dictionaries.add("pl-de");
        dictionaries.add("pl-de");
        dictionaries.add("pl-de");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dictionaries);
        ListView listView = (ListView) findViewById(R.id.dict_list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View childView, int position, long id) {
                show_dictionary(position, id);
            }
            public void onNothingSelected(AdapterView parentView) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(MainActivity.getGoogleApi());
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            GoogleSignInAccount acct = result.getSignInAccount();
            mStatusTextView.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
        } else {
            mStatusTextView.setText(auth_fail);
        }
    }


    private void create_dictionary() {
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(MainActivity.getGoogleApi());
        if (opr.isDone()) {
            Intent intent = new Intent(this, DictionaryCreate.class);
            startActivity(intent);
        } else {
            finish();
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
        }
    }

    private void import_dictionary() {
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(MainActivity.getGoogleApi());
        if (opr.isDone()) {
            Intent intent = new Intent(this, DictionaryImport.class);
            startActivity(intent);
        } else {
            finish();
        }
    }

    private void show_dictionary(int position, long id) {
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(MainActivity.getGoogleApi());
        if (opr.isDone()) {
            Intent intent = new Intent(this, DictionaryShow.class);
            intent.putExtra(POSITION_INFO, position);
            intent.putExtra(ID_INFO, id);
            startActivity(intent);
        } else {
            finish();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_dictionary:
                create_dictionary();
                break;
            case R.id.import_dictionary:
                import_dictionary();
                break;
        }
    }
}
