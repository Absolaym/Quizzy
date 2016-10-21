package esasy.quizzy.squizzy;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class SQLiteBD extends SQLiteOpenHelper {
    private static final String TABLE_QUIZZ = "table_quizz";
    private static final String COL_ID = "ID";
    private static final String COL_NOM = "NOM";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_QUIZZ + "(" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_NOM + " TEXT NOT NULL);";

    public SQLiteBD(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE " + TABLE_QUIZZ + ";");
        onCreate(db);
    }
}
