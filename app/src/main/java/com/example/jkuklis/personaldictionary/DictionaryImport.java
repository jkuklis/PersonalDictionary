package com.example.jkuklis.personaldictionary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.OptionalPendingResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DictionaryImport extends AppCompatActivity
    implements View.OnClickListener {

    public String DICT_ID = "-1";

    private String URL_FAIL = "Failed to connect with URL!";
    private String JSON_FAIL = "Failed to parse JSON!";
    private String NAME_FAIL = "Empty database name!";
    private String LANGUAGES_MANY_FAIL = "More than 5 languages!";
    private String LANGUAGES_FEW_FAIL = "Less than 1 language!";
    private String ABBREVIATONS_COUNT_FAIL = "Languages and abbreviations counts do not match!";
    private String LANGUAGES_EMPTY_FAIL = "Empty language!";
    private String ABBREVIATIONS_EMPTY_FAIL = "Empty abbreviation!";
    private String ENTRY_LENGTH_FAIL = "Entry length different than languages count!";

    private EditText importPath;
    private TextView status;

    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary_import);

        findViewById(R.id.importButton).setOnClickListener(this);

        status = (TextView) findViewById(R.id.importStatus);
        status.setVisibility(View.INVISIBLE);

        importPath = (EditText) findViewById(R.id.importPath);

    }

    private void addDictionary(String result) {
        String dictionaryName;
        List<String> languages = new ArrayList<String>();
        List<String> abbreviations = new ArrayList<String>();
        List<ColumnValues> entries = new ArrayList<ColumnValues>();
        DbHelper db = new DbHelper(this);
        String owner = "0";

        try {
            JSONObject jObject = new JSONObject(result);
            JSONArray jLanguages = jObject.getJSONArray("languages");
            JSONArray jAbbreviations = jObject.getJSONArray("abbreviations");
            JSONArray jEntries = jObject.getJSONArray("entries");

            dictionaryName = jObject.getString("name");

            if (dictionaryName.equals("")) {
                status.setText(NAME_FAIL);
                status.setVisibility(View.VISIBLE);
                return;
            }

            if (jLanguages.length() < 1 || jLanguages.length() > 5) {
                status.setText(jLanguages.length() > 5 ? LANGUAGES_MANY_FAIL : LANGUAGES_FEW_FAIL);
                status.setVisibility(View.VISIBLE);
                return;
            }

            if (jAbbreviations.length() != jLanguages.length()) {
                status.setText(ABBREVIATONS_COUNT_FAIL);
                status.setVisibility(View.VISIBLE);
                return;
            }


            for (int i = 0; i < jLanguages.length(); i++) {
                String lang = jLanguages.getString(i);
                if (lang.equals("")) {
                    status.setText(LANGUAGES_EMPTY_FAIL);
                    status.setVisibility(View.VISIBLE);
                    return;
                }
                languages.add(lang);
            }

            for (int i = 0; i < jAbbreviations.length(); i++) {
                String abbr = jAbbreviations.getString(i);
                if (abbr.equals("")) {
                    status.setText(ABBREVIATIONS_EMPTY_FAIL);
                    status.setVisibility(View.VISIBLE);
                    return;
                }
                abbreviations.add(abbr);
            }

            for (int i = 0; i < jEntries.length(); i++) {
                JSONArray jEntry = jEntries.getJSONArray(i);
                if (jEntry.length() != jLanguages.length()) {
                    status.setText(ENTRY_LENGTH_FAIL);
                    status.setVisibility(View.VISIBLE);
                    return;
                }

                ColumnValues cv = new ColumnValues();
                for (int j = 0; j < jEntry.length(); j++) {
                    cv.add(jEntry.getString(j));
                }

                entries.add(cv);
            }

        } catch (final JSONException e) {
            status.setText(JSON_FAIL);
            status.setVisibility(View.VISIBLE);
            return;
        }

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(HelloScreen.getGoogleApi());
        if (opr.isDone()) {
            GoogleSignInResult googleResult = opr.get();
            GoogleSignInAccount acct = googleResult.getSignInAccount();
            owner = acct.getId();
        } else {
            finish();
        }

        Dictionary dict = new Dictionary(dictionaryName, owner);

        int dictId = (int) db.createDictionary(dict);

        for (int i = 0; i < languages.size(); i++) {
            Language lang = new Language(dictId, i, abbreviations.get(i), languages.get(i));
            db.createLanguage(lang);
        }

        for (int i = 0; i < entries.size(); i++) {
            Entry entry = new Entry(dictId);
            int entryId = (int) db.createEntry(entry);

            ColumnValues cv = entries.get(i);
            for (int j = 0; j < languages.size(); j++) {
                String wordString = cv.get(j);
                Word word = new Word(entryId, j, wordString);
                db.createWord(word);
            }
        }

        Intent intent = new Intent(this, DictionaryShow.class);
        intent.putExtra(DICT_ID, String.valueOf(dictId));

        startActivity(intent);

    }

    private class ColumnValues {
        public List<String> columns = new ArrayList<>();

        public void add(String value) {
            columns.add(value);
        }

        public String get(int position) {
            return columns.get(position);
        }
    }

    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(DictionaryImport.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            URL url;

            try {
                url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                return buffer.toString();

            } catch (Exception e) {
                reportUrl();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    reportUrl();
                }
            }
            return null;
        }

        private void reportUrl() {
            status.setText(URL_FAIL);
            status.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                if (pd.isShowing()) {
                    pd.dismiss();
                }
                addDictionary(result);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.importButton:
                new JsonTask().execute(importPath.getText().toString());
                break;
        }
    }
}
