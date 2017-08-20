import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;


public class LanguageListDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LanguageList.db";

    private static final String TABLE_LANGUAGES = "dictionary_languages";
    private static final String TABLE_ENTRIES = "entries";
    private static final String TABLE_WORDS = "words";

    private static final String DICT_LANG_ID = "lang_id";
    private static final String DICT_ID = "dict_id";
    private static final String DICT_LANG_NO = "lang_pos";
    private static final String DICT_LANG_ABBR = "lang_abbr";
    private static final String DICT_LANG_NAME = "lang_name";

    private static final String ENTRY_ID = "entry_id";


    private static final String WORD_ID = "word_id";
    private static final String WORD_DICT_LANG_ID = "dict_lang_id";
    private static final String WORD_DICT_LANG_POS = "lang_pos";
    private static final String WORD_STRING = "word";

    private static final String CREATE_DICTIONARIES_TABLE = "CREATE TABLE " + TABLE_LANGUAGES + "("
            + DICT_LANG_ID + " INTEGER PRIMARY KEY, "
            + DICT_ID + " INTEGER NOT NULL, "
            + DICT_LANG_NO + " INTEGER NOT NULL, "
            + DICT_LANG_ABBR + " TEXT, "
            + DICT_LANG_NAME + " TEXT" + ")";

    private static final String CREATE_WORDS_TABLE = "CREATE TABLE " + TABLE_WORDS + "("
            + WORD_ID + " INTEGER PRIMARY KEY, "
            + WORD_DICT_LANG_ID  + "INTEGER NOT NULL, "
            + WORD_DICT_LANG_POS + " INTEGER NOT NULL, "
            + WORD_STRING + " TEXT" + ")";


    public LanguageListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DICTIONARIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROB TABLE IF EXISTS " + TABLE_LANGUAGES);
        db.execSQL("DROB TABLE IF EXISTS " + TABLE_WORDS);

        onCreate(db);
    }

    public long createLanguage(DictionaryLanguage lang) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DICT_ID, lang.getDict_id());
        values.put(DICT_LANG_NO, lang.getLang_no());
        values.put(DICT_LANG_ABBR, lang.getLang_abbr());
        values.put(DICT_LANG_NAME, lang.getLang_name());

        long lang_id = db.insert(TABLE_LANGUAGES, null, values);

        return lang_id;
    }

    public DictionaryLanguage getLanguage(long lang_id) {
        DictionaryLanguage lang = new DictionaryLanguage(0, 0, "ERROR", "ERROR");

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_LANGUAGES + " WHERE "
                + DICT_LANG_ID + " = " + lang_id;

        //Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
            lang.setId(c.getInt(c.getColumnIndex(DICT_LANG_ID)));
            lang.setDict_id(c.getInt(c.getColumnIndex(DICT_ID)));
            lang.setLang_no(c.getInt(c.getColumnIndex(DICT_LANG_NO)));
            lang.setLang_abbr(c.getString(c.getColumnIndex(DICT_LANG_ABBR)));
            lang.setLang_name(c.getString(c.getColumnIndex(DICT_LANG_NAME)));
        } else {
            // throw
        }

        return lang;
    }

    public List<DictionaryLanguage> getDictLanguages(long dict_id) {
        List<DictionaryLanguage> langs = new ArrayList<DictionaryLanguage>();

        String selectQuery = "SELECT * FROM " + TABLE_LANGUAGES + " WHERE "
                + DICT_ID + " = " + dict_id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                DictionaryLanguage lang = new DictionaryLanguage(0, 0, "ERROR", "ERROR");
                lang.setId(c.getInt(c.getColumnIndex(DICT_LANG_ID)));
                lang.setDict_id(c.getInt(c.getColumnIndex(DICT_ID)));
                lang.setLang_no(c.getInt(c.getColumnIndex(DICT_LANG_NO)));
                lang.setLang_abbr(c.getString(c.getColumnIndex(DICT_LANG_ABBR)));
                lang.setLang_name(c.getString(c.getColumnIndex(DICT_LANG_NAME)));

                langs.add(lang);
            } while (c.moveToNext());

        }

        return langs;
    }

    public WordDictionary getWord(long word_id) {
        WordDictionary word = new WordDictionary(0, 0, 0, "ERROR");

        String selectQuery = "SELECT * FROM " + TABLE_WORDS + " WHERE "
                + WORD_ID + " = " + word_id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
            word.setId(c.getInt(c.getColumnIndex(WORD_ID)));
            word.setDict_lang_id(c.getInt(c.getColumnIndex(WORD_DICT_LANG_ID)));
            word.setLang_pos(c.getInt(c.getColumnIndex(WORD_DICT_LANG_POS)));
            word.setWord(c.getString(c.getColumnIndex(WORD_STRING)));
        } else {
            // throw
        }

        return word;
    }

    public List<WordDictionary> getLangWords(long lang_id) {
        List<WordDictionary> words = new ArrayList<WordDictionary>();

        String selectQuery = "SELECT * FROM " + TABLE_WORDS + " WHERE "
                + WORD_DICT_LANG_ID + " = " + lang_id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                WordDictionary word = new WordDictionary(0, 0, 0, "ERROR");
                word.setId(c.getInt(c.getColumnIndex(WORD_ID)));
                word.setDict_lang_id(c.getInt(c.getColumnIndex(WORD_DICT_LANG_ID)));
                word.setLang_pos(c.getInt(c.getColumnIndex(WORD_DICT_LANG_POS)));
                word.setWord(c.getString(c.getColumnIndex(WORD_STRING)));

                words.add(word);
            } while (c.moveToNext());

        }

        return words;
    }

    public WordDictionary getLangPosWord(long lang_id, int lang_pos) {

    }

    public List<WordDictionary> getLangPosWords(long dict_id, int lang_pos) {
        List<WordDictionary> words = new ArrayList<WordDictionary>();

        String selectQuery = "SELECT * FROM " + TABLE_WORDS + " WHERE "
                + WORD_DICT_LANG_ID + " = " + lang_id + " AND "
                + WORD_DICT_LANG_POS + " = " + lang_pos;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                WordDictionary word = new WordDictionary(0, 0, 0, "ERROR");
                word.setId(c.getInt(c.getColumnIndex(WORD_ID)));
                word.setDict_lang_id(c.getInt(c.getColumnIndex(WORD_DICT_LANG_ID)));
                word.setLang_pos(c.getInt(c.getColumnIndex(WORD_DICT_LANG_POS)));
                word.setWord(c.getString(c.getColumnIndex(WORD_STRING)));

                words.add(word);
            } while (c.moveToNext());

        }

        return words;
    }

    public void deleteWord(long word_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WORDS, WORD_ID + " = ?",
                new String[] { String.valueOf(word_id)});
    }

    public void deleteEntry(long lang_id, int lang_pos) {
        List<WordDictionary> words = getLangPosWords(lang_id, lang_pos);

        for (WordDictionary word : words) {
            deleteWord(word.getId());
        }
    }

    public void delete
}
