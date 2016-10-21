package esasy.quizzy.squizzy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "quizz.db";
    private static final String TABLE_QUIZZ = "table_quizz";
    private static final String COL_ID = "ID";
    private static final String COL_NOM = "Nom";
    private static final int NUM_COL_ID = 0;
    private static final int NUM_COL_NOM = 1;

    private SQLiteDatabase db;
    private SQLiteBD mSQLiteBD;

    public Database(Context context) {
        mSQLiteBD = new SQLiteBD(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open() {
        db = mSQLiteBD.getWritableDatabase();
    }

    public void upgrade() {
        int newVer = VERSION_BDD;
        mSQLiteBD.onUpgrade(db, 1, newVer++);
    }
    public void close() {
        db.close();
    }

    public SQLiteDatabase getBD() {
        return db;
    }

    public long insertQuizz(Quizz quizz) {
        ContentValues values = new ContentValues();

        values.put(COL_NOM, quizz.getNom());
        return db.insert(TABLE_QUIZZ, null, values);
    }

    public int updateQuizz(Quizz quizz, int id) {
        ContentValues values = new ContentValues();
        values.put(COL_NOM, quizz.getNom());
        return db.update(TABLE_QUIZZ, values, COL_ID + " = " + id, null);
    }

    public int removeQuizz(int id) {
        return db.delete(TABLE_QUIZZ, COL_ID + " = " + id, null);
    }

    public Quizz getQuizzWithNom(String nom) {
        Cursor cursor = db.query(TABLE_QUIZZ, new String[] {COL_ID, COL_NOM}, COL_NOM + " LIKE \"" + nom + "\"", null, null, null, null);
        return cursorToQuizz(cursor);
    }

    public Quizz getQuizzWithId(int id) {
        Cursor cursor = db.query(TABLE_QUIZZ, new String[] {COL_ID, COL_NOM}, COL_ID + " = " + id, null, null, null, null);
        return cursorToQuizz(cursor);
    }


    public List<Quizz> getAllQuizz() {
        Cursor cursor = db.query(TABLE_QUIZZ, new String[] {COL_ID, COL_NOM}, null, null, null, null, null);

        if(cursor.getCount() == 0) {
            return null;
        }
        List<Quizz> quizzList = new ArrayList<>();

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            Quizz quizz = new Quizz();
            quizz.setId(cursor.getInt(NUM_COL_ID));
            quizz.setNom(cursor.getString(NUM_COL_NOM));
            quizzList.add(quizz);
            cursor.moveToNext();
        }
        cursor.close();

        return quizzList;
    }

    private Quizz cursorToQuizz(Cursor cursor) {
        if(cursor.getCount() == 0) {
            return null;
        }

        cursor.moveToFirst();
        Quizz quizz = new Quizz();

        quizz.setId(cursor.getInt(NUM_COL_ID));
        quizz.setNom(cursor.getString(NUM_COL_NOM));

        cursor.close();

        return quizz;
    }
}