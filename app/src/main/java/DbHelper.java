import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LanguageList.db";

    private static final String TABLE_DICTIONARIES = "dictionaries";
    private static final String TABLE_LANGUAGES = "languages";
    private static final String TABLE_ENTRIES = "entries";
    private static final String TABLE_WORDS = "words";

    private static final String DICT_ID = "dict_id";
    private static final String DICT_NAME = "dict_name";

    private static final String LANG_ID = "lang_id";
    private static final String LANG_DICT_ID = "lang_dict_id";
    private static final String LANG_NO = "lang_no";
    private static final String LANG_ABBR = "lang_abbr";
    private static final String LANG_NAME = "lang_name";

    private static final String ENTRY_ID = "entry_id";
    private static final String ENTRY_DICT_ID = "dict_id";
    private static final String ENTRY_POS = "entry_pos";

    private static final String WORD_ID = "word_id";
    private static final String WORD_ENTRY_ID = "entry_id";
    private static final String WORD_POS = "word_pos";
    private static final String WORD_STRING = "word_string";


    private static final String CREATE_DICTIONARIES_TABLE = "CREATE TABLE " + TABLE_DICTIONARIES + "("
            + DICT_ID + " INTEGER PRIMARY KEY, "
            + DICT_NAME + " TEXT" + ")";

    private static final String CREATE_LANGUAGES_TABLE = "CREATE TABLE " + TABLE_LANGUAGES + "("
            + LANG_ID + " INTEGER PRIMARY KEY, "
            + LANG_DICT_ID + " INTEGER NOT NULL, "
            + LANG_NO + " INTEGER NOT NULL, "
            + LANG_ABBR + " TEXT, "
            + LANG_NAME + " TEXT" + ")";

    private static final String CREATE_ENTRIES_TABLE = "CREATE TABLE " + TABLE_ENTRIES + "("
            + ENTRY_ID + " INTEGER PRIMARY KEY, "
            + ENTRY_DICT_ID + " INTEGER NOT NULL, "
            + ENTRY_POS + " INTEGER NOT NULL" + ")";

    private static final String CREATE_WORDS_TABLE = "CREATE TABLE " + TABLE_WORDS + "("
            + WORD_ID + " INTEGER PRIMARY KEY, "
            + WORD_ENTRY_ID + " INTEGER NOT NULL, "
            + WORD_POS + "INTEGER NOT NULL, "
            + WORD_STRING + " TEXT" + ")";


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DICTIONARIES_TABLE);
        db.execSQL(CREATE_LANGUAGES_TABLE);
        db.execSQL(CREATE_ENTRIES_TABLE);
        db.execSQL(CREATE_WORDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROB TABLE IF EXISTS " + TABLE_DICTIONARIES);
        db.execSQL("DROB TABLE IF EXISTS " + TABLE_LANGUAGES);
        db.execSQL("DROB TABLE IF EXISTS " + TABLE_ENTRIES);
        db.execSQL("DROB TABLE IF EXISTS " + TABLE_WORDS);

        onCreate(db);
    }

    public long createDictionary(Dictionary dict) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DICT_ID, dict.getId());
        values.put(DICT_NAME, dict.getName());

        long dict_id = db.insert(TABLE_DICTIONARIES, null, values);

        return dict_id;
    }

    public long createLanguage(Language lang) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(LANG_ID, lang.getId());
        values.put(LANG_DICT_ID, lang.getDictId());
        values.put(LANG_NO, lang.getNo());
        values.put(LANG_ABBR, lang.getAbbr());
        values.put(LANG_NAME, lang.getName());

        long lang_id = db.insert(TABLE_LANGUAGES, null, values);

        return lang_id;
    }

    public long createEntry(Entry entry) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ENTRY_ID, entry.getId());
        values.put(ENTRY_DICT_ID, entry.getDictId());
        values.put(ENTRY_POS, entry.getPos());

        long entry_id = db.insert(TABLE_LANGUAGES, null, values);

        return entry_id;
    }

    public long createWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WORD_ID, word.getId());
        values.put(WORD_ENTRY_ID, word.getEntryId());
        values.put(WORD_POS, word.getPos());
        values.put(WORD_STRING, word.getString());

        long word_id = db.insert(TABLE_LANGUAGES, null, values);

        return word_id;
    }


    public Dictionary getDictionary(long dictId) {
        Dictionary dict = new Dictionary(0, "ERROR", "ERROR");

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_DICTIONARIES + " WHERE "
                + DICT_ID + " = " + dictId;

        //Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
            dict.setId(c.getInt(c.getColumnIndex(DICT_ID)));
            dict.setName(c.getString(c.getColumnIndex(DICT_NAME)));
        } else {
            // throw
        }

        return dict;
    }

    public Language getLanguage(long langId) {
        Language lang = new Language(0, 0, "ERROR", "ERROR");

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_LANGUAGES + " WHERE "
                + LANG_ID + " = " + langId;

        //Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
            lang.setId(c.getInt(c.getColumnIndex(LANG_ID)));
            lang.setDictId(c.getInt(c.getColumnIndex(LANG_DICT_ID)));
            lang.setNo(c.getInt(c.getColumnIndex(LANG_NO)));
            lang.setAbbr(c.getString(c.getColumnIndex(LANG_ABBR)));
            lang.setName(c.getString(c.getColumnIndex(LANG_NAME)));
        } else {
            // throw
        }

        return lang;
    }

    public Entry getEntry(long entryId) {
        Entry entry = new Entry(0, 0, 0);

        String selectQuery = "SELECT * FROM " + TABLE_ENTRIES + " WHERE "
                + ENTRY_ID + " = " + entryId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
            entry.setId(c.getInt(c.getColumnIndex(ENTRY_ID)));
            entry.setDictId(c.getInt(c.getColumnIndex(ENTRY_DICT_ID)));
            entry.setPos(c.getInt(c.getColumnIndex(ENTRY_POS)));
        } else {
            // throw
        }

        return entry;
    }

    public Word getWord(long wordId) {
        Word word = new Word(0, 0, 0, "ERROR");

        String selectQuery = "SELECT * FROM " + TABLE_WORDS + " WHERE "
                + WORD_ID + " = " + wordId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
            word.setId(c.getInt(c.getColumnIndex(WORD_ID)));
            word.setEntryId(c.getInt(c.getColumnIndex(WORD_ENTRY_ID)));
            word.setPos(c.getInt(c.getColumnIndex(WORD_POS)));
            word.setString(c.getString(c.getColumnIndex(WORD_STRING)));
        } else {
            // throw
        }

        return word;
    }

    public List<Language> getDictLanguages(long dictId) {
        List<Language> langs = new ArrayList<Language>();

        String selectQuery = "SELECT * FROM " + TABLE_LANGUAGES + " WHERE "
                + LANG_DICT_ID + " = " + dictId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Language lang = new Language(0, 0, "ERROR", "ERROR");
                lang.setId(c.getInt(c.getColumnIndex(LANG_ID)));
                lang.setDictId(c.getInt(c.getColumnIndex(LANG_DICT_ID)));
                lang.setNo(c.getInt(c.getColumnIndex(LANG_NO)));
                lang.setAbbr(c.getString(c.getColumnIndex(LANG_ABBR)));
                lang.setName(c.getString(c.getColumnIndex(LANG_NAME)));

                langs.add(lang);

            } while (c.moveToNext());

        }

        return langs;
    }

    public Entry getDictPosEntry(long dictId, int pos) {
        Entry entry = new Entry(0, 0, 0);

        String selectQuery = "SELECT * FROM " + TABLE_ENTRIES + " WHERE "
                + ENTRY_DICT_ID + " = " + dictId + " AND "
                + ENTRY_POS + " = " + pos;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            c.moveToFirst();
            entry.setId(c.getInt(c.getColumnIndex(ENTRY_ID)));
            entry.setDictId(c.getInt(c.getColumnIndex(ENTRY_DICT_ID)));
            entry.setPos(c.getInt(c.getColumnIndex(ENTRY_POS)));
        } else {
            // throw
        }

        return entry;
    }

    public List<Entry> getDictEntries(long dictId) {
        List<Entry> entries = new ArrayList<Entry>();

        String selectQuery = "SELECT * FROM " + TABLE_ENTRIES + " WHERE "
                + ENTRY_DICT_ID + " = " + dictId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Entry entry = new Entry(0, 0, 0);
                c.moveToFirst();
                entry.setId(c.getInt(c.getColumnIndex(ENTRY_ID)));
                entry.setDictId(c.getInt(c.getColumnIndex(ENTRY_DICT_ID)));
                entry.setPos(c.getInt(c.getColumnIndex(ENTRY_POS)));

                entries.add(entry);

            } while (c.moveToNext());

        }

        return entries;
    }

    public List<Word> getEntryWords(long entryId) {
        List<Word> words = new ArrayList<Word>();

        String selectQuery = "SELECT * FROM " + TABLE_WORDS + " WHERE "
                + WORD_ENTRY_ID + " = " + entryId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                Word word = new Word(0, 0, 0, "ERROR");
                word.setId(c.getInt(c.getColumnIndex(WORD_ID)));
                word.setEntryId(c.getInt(c.getColumnIndex(WORD_ENTRY_ID)));
                word.setPos(c.getInt(c.getColumnIndex(WORD_POS)));
                word.setString(c.getString(c.getColumnIndex(WORD_STRING)));

                words.add(word);

            } while (c.moveToNext());

        }

        return words;
    }

    public List<Word> getDictWords(long dictId) {
        List<Word> words = new ArrayList<Word>();

        List<Entry> entries = getDictEntries(dictId);

        for (Entry entry : entries) {
            List<Word> entryWords = getEntryWords(entry.getId());

            for (Word word : entryWords) {
                words.add(word);
            }
        }

        return words;
    }

    public void deleteWord(long wordId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WORDS, WORD_ID + " = ?",
                new String[] { String.valueOf(wordId)});
    }

    public void deleteEntry(long entryId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WORDS, WORD_ENTRY_ID + " = ?",
                new String[] { String.valueOf(entryId)});

        db.delete(TABLE_ENTRIES, ENTRY_ID + " = ?",
                new String[] { String.valueOf(entryId)});
    }

    public void deleteLang(long langId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LANGUAGES, LANG_ID + " = ?",
                new String[] { String.valueOf(langId)});
    }

    public void deleteDictPosEntry(long dictId, int pos) {
        Entry entry = getDictPosEntry(dictId, pos);

        deleteEntry(entry.getId());
    }

    public void deleteDict(long dictId) {
        List<Entry> entries = getDictEntries(dictId);

        for (Entry entry : entries) {
            deleteEntry(entry.getId());
        }

        List<Language> langs = getDictLanguages(dictId);

        for (Language lang : langs) {
            deleteLang(lang.getId());
        }

        deleteDict(dictId);
    }

    // no database updating yet
}
