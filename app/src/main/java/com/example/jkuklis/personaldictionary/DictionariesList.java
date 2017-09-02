package com.example.jkuklis.personaldictionary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.OptionalPendingResult;

import java.util.ArrayList;
import java.util.List;

public class DictionariesList extends AppCompatActivity implements
        View.OnClickListener
    {

    public static final String DICT_ID = "-1";

    private static final String AUTH_FAIL = "STRANGE: Failed to authorize";
    private TextView footerInfo;

    private List<String> dictionariesNames = new ArrayList<String>();
    private List<Dictionary> dictionaries = new ArrayList<Dictionary>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionaries_list);

        findViewById(R.id.createDictionaryButton).setOnClickListener(this);
        findViewById(R.id.importDictionaryButton).setOnClickListener(this);

        footerInfo = (TextView) findViewById(R.id.loginInfo);

        DbHelper db = new DbHelper(getApplicationContext());

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(HelloScreen.getGoogleApi());
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            GoogleSignInAccount acct = result.getSignInAccount();
            dictionaries = db.getDictsOwned(acct.getId());
        } else {
            finish();
        }

        for (Dictionary dict : dictionaries) {
            dictionariesNames.add(dict.getName()); // + " " + dict.getOwner());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dictionariesNames);
        ListView listView = (ListView) findViewById(R.id.dictonariesList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View childView, int position, long id) {
                show_dictionary(position, id);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(HelloScreen.getGoogleApi());
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            GoogleSignInAccount acct = result.getSignInAccount();
            footerInfo.setText(getString(R.string.signed_in_fmt, acct.getDisplayName()));
        } else {
            footerInfo.setText(AUTH_FAIL);
        }
    }


    private void create_dictionary() {
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(HelloScreen.getGoogleApi());
        if (opr.isDone()) {
            Intent intent = new Intent(this, DictionaryCreate.class);
            startActivity(intent);
        } else {
            finish();
        }
    }

    private void import_dictionary() {
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(HelloScreen.getGoogleApi());
        if (opr.isDone()) {
            Intent intent = new Intent(this, DictionaryImport.class);
            startActivity(intent);
        } else {
            finish();
        }
    }

    private void show_dictionary(int position, long id) {
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(HelloScreen.getGoogleApi());
        if (opr.isDone()) {
            Intent intent = new Intent(this, DictionaryShow.class);
            intent.putExtra(DICT_ID, String.valueOf(dictionaries.get(position).getId()));
            startActivity(intent);
        } else {
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createDictionaryButton:
                create_dictionary();
                break;
            case R.id.importDictionaryButton:
                import_dictionary();
                break;
        }
    }
}
