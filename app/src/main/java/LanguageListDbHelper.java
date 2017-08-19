import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class LanguageListDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "LanguageList.db";

    private static final String TABLE_LANGUAGES = "languages";

    private static final String ELT_ID = "id";
    private static final String ELT_LANG_NO = "lang_no";
    private static final String ELT_LANG_POS = "lang_pos";
    private static final String ELT_LANG_NAME = "lang_name";

    public LanguageListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LANGUAGES_TABLE = "CREATE TABLE " + TABLE_LANGUAGES + "("
                + ELT_ID + " INTEGER PRIMARY KEY, " + ELT_LANG_NO + " INTEGER NOT NULL, "
                + ELT_LANG_POS + " INTEGER NOT NULL, " + ELT_LANG_NAME + " TEXT" + ")";
        db.execSQL(CREATE_LANGUAGES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROB TABLE IF EXISTS " + TABLE_LANGUAGES);

        onCreate(db);
    }

}
