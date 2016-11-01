package iut_orsay.quizzy.quizzy;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class SQLiteBD extends SQLiteOpenHelper {
    private static final String TABLE_QUIZZ = "table_quizz";
    private static final String TABLE_QUESTION = "table_question";
    private static final String TABLE_REPONSE = "table_reponse";

    private static final String COL_ID_QUIZZ = "ID";
    private static final String COL_NOM_QUIZZ = "NOM";

    private static final String COL_ID_QUESTION = "ID";
    private static final String COL_INTITULE_QUESTION = "INTITULE";
    private static final String COL_IDQUIZZ_QUESTION = "IDQUIZZ";

    private static final String COL_ID_REPONSE = "ID";
    private static final String COL_INTITULE_REPONSE = "INTITULE";
    private static final String COL_BOOL_REPONSE = "OK";
    private static final String COL_IDQUESTION_REPONSE = "IDREPONSE";

    private static final String CREATE_QUIZZ = "CREATE TABLE " + TABLE_QUIZZ + "(" +
            COL_ID_QUIZZ + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_NOM_QUIZZ + " TEXT NOT NULL)";

    private static final String CREATE_QUESTION = "CREATE TABLE " + TABLE_QUESTION + "(" +
            COL_ID_QUESTION + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_INTITULE_QUESTION + " TEXT NOT NULL, " +
            COL_IDQUIZZ_QUESTION + " INTEGER, " +
            "FOREIGN KEY ("+COL_IDQUIZZ_QUESTION+") REFERENCES " + TABLE_QUIZZ + "("+COL_ID_QUIZZ+"))";

    private static final String CREATE_REPONSE = "CREATE TABLE " + TABLE_REPONSE + "(" +
            COL_ID_REPONSE +  " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_INTITULE_REPONSE + " TEXT NOT NULL, " +
            COL_BOOL_REPONSE + " BOOLEAN NOT NULL CHECK ("+COL_BOOL_REPONSE+" IN (0,1)), " +
            COL_IDQUESTION_REPONSE + " INTEGER, " +
            "FOREIGN KEY ("+COL_IDQUESTION_REPONSE+") REFERENCES " + TABLE_QUESTION + "("+COL_ID_QUESTION+"))";

    public SQLiteBD(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUIZZ);
        db.execSQL(CREATE_QUESTION);
        db.execSQL(CREATE_REPONSE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZZ);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REPONSE);
        onCreate(db);
    }
}