package iut_orsay.quizzy.quizzy;

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
    private static final String COL_ID_QUIZZ = "ID";
    private static final String COL_NOM_QUIZZ = "Nom";
    private static final int NUM_COL_ID_QUIZZ = 0;
    private static final int NUM_COL_NOM_QUIZZ = 1;

    private static final String TABLE_QUESTION = "table_question";
    private static final String COL_ID_QUESTION = "ID";
    private static final String COL_INTITULE_QUESTION = "INTITULE";
    private static final String COL_IDQUIZZ_QUESTION = "IDQUIZZ";
    private static final int NUM_COL_ID_QUESTION = 0;
    private static final int NUM_COL_INTITULE_QUESTION = 1;
    private static final int NUM_COL_IDQUIZZ_QUESTION = 2;

    private static final String TABLE_REPONSE = "table_reponse";
    private static final String COL_ID_REPONSE = "ID";
    private static final String COL_INTITULE_REPONSE = "INTITULE";
    private static final String COL_BOOL_REPONSE = "OK";
    private static final String COL_IDQUESTION_REPONSE = "IDREPONSE";
    private static final int NUM_COL_ID_REPONSE = 0;
    private static final int NUM_COL_INTITULE_REPONSE = 1;
    private static final int NUM_COL_BOOL_REPONSE = 2;
    private static final int NUM_COL_IDQUESTION_REPONSE = 3;

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

        values.put(COL_NOM_QUIZZ, quizz.getNom());
        return db.insert(TABLE_QUIZZ, null, values);
    }

    public int updateQuizz(Quizz quizz, int id) {
        ContentValues values = new ContentValues();
        values.put(COL_NOM_QUIZZ, quizz.getNom());
        return db.update(TABLE_QUIZZ, values, COL_ID_QUIZZ + " = " + id, null);
    }

    public int removeQuizz(int id) {
        return db.delete(TABLE_QUIZZ, COL_ID_QUIZZ + " = " + id, null);
    }

    public Quizz getQuizzWithNom(String nom) {
        Cursor cursor = db.query(TABLE_QUIZZ, new String[] {COL_ID_QUIZZ, COL_NOM_QUIZZ}, COL_NOM_QUIZZ + " LIKE \"" + nom + "\"", null, null, null, null);
        return cursorToQuizz(cursor);
    }

    public Quizz getQuizzWithId(int id) {
        Cursor cursor = db.query(TABLE_QUIZZ, new String[] {COL_ID_QUIZZ, COL_NOM_QUIZZ}, COL_ID_QUIZZ + " = " + id, null, null, null, null);
        return cursorToQuizz(cursor);
    }


    public List<Quizz> getAllQuizz() {
        Cursor cursor = db.query(TABLE_QUIZZ, new String[] {COL_ID_QUIZZ, COL_NOM_QUIZZ}, null, null, null, null, null);

        if(cursor.getCount() == 0) {
            return null;
        }
        List<Quizz> quizzList = new ArrayList<>();

        cursor.moveToFirst();



        while(!cursor.isAfterLast()) {
            Quizz quizz = new Quizz();
            quizz.setId(cursor.getInt(NUM_COL_ID_QUIZZ));
            quizz.setNom(cursor.getString(NUM_COL_NOM_QUIZZ));
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

        quizz.setId(cursor.getInt(NUM_COL_ID_QUIZZ));
        quizz.setNom(cursor.getString(NUM_COL_NOM_QUIZZ));

        cursor.close();

        return quizz;
    }

    public long insertQuestion(Question question, int idQuizz) {
        ContentValues values = new ContentValues();

        values.put(COL_INTITULE_QUESTION, question.getIntitulé());
        values.put(COL_IDQUIZZ_QUESTION, idQuizz);
        return db.insert(TABLE_QUESTION, null, values);
    }

    public int updateQuestion(Question question, int id) {
        ContentValues values = new ContentValues();
        values.put(COL_INTITULE_QUESTION, question.getIntitulé());
        return db.update(TABLE_REPONSE, values, COL_ID_QUESTION + " = " + id, null);
    }


    public Question getQuestionWithId(int id) {
        Cursor cursor = db.query(TABLE_QUESTION, new String[] {COL_ID_QUESTION, COL_INTITULE_QUESTION, COL_IDQUIZZ_QUESTION}, COL_ID_QUESTION + " = " + id, null, null, null, null);
        return cursorToQuestion(cursor);
    }

    private Question cursorToQuestion(Cursor cursor) {
        if(cursor.getCount() == 0) {
            return null;
        }

        cursor.moveToFirst();
        Question question = new Question();

        question.setId(cursor.getInt(NUM_COL_ID_QUESTION));
        question.setIntitulé(cursor.getString(NUM_COL_INTITULE_QUESTION));
        question.setIdQuizz(cursor.getInt(NUM_COL_IDQUIZZ_QUESTION));

        cursor.close();

        return question;
    }

    public long insertReponse(Réponse reponse, int idQuestion) {
        ContentValues values = new ContentValues();

        values.put(COL_INTITULE_REPONSE, reponse.getIntitulé());
        values.put(COL_BOOL_REPONSE, reponse.getBool());
        values.put(COL_IDQUESTION_REPONSE, idQuestion);
        return db.insert(TABLE_REPONSE, null, values);
    }

    public Réponse getReponseWithId(int id) {
        Cursor cursor = db.query(TABLE_REPONSE, new String[] {COL_ID_REPONSE, COL_INTITULE_REPONSE, COL_BOOL_REPONSE, COL_IDQUESTION_REPONSE}, COL_ID_REPONSE + " = " + id, null, null, null, null);
        return cursorToReponse(cursor);
    }

    public List<Réponse> getReponsesWithQuestionId(int id) {
        Cursor cursor = db.query(TABLE_REPONSE, new String[] {COL_ID_REPONSE, COL_INTITULE_REPONSE, COL_BOOL_REPONSE, COL_IDQUESTION_REPONSE}, COL_IDQUESTION_REPONSE + " = " + id, null, null, null, null);

        if(cursor.getCount() == 0) {
            return null;
        }

        List<Réponse> repList = new ArrayList<>();

        cursor.moveToFirst();

        Réponse rep = new Réponse();

        while(!cursor.isAfterLast()) {
            rep.setId(cursor.getInt(NUM_COL_ID_REPONSE));
            rep.setIntitulé(cursor.getString(NUM_COL_INTITULE_REPONSE));
            rep.setBool((cursor.getInt(NUM_COL_BOOL_REPONSE)) > 0);
            rep.setIdQuestion(cursor.getInt(NUM_COL_IDQUESTION_REPONSE));
            repList.add(rep);
        }

        cursor.close();

        return repList;
    }

    private Réponse cursorToReponse(Cursor cursor) {
        if(cursor.getCount() == 0) {
            return null;
        }

        cursor.moveToFirst();
        Réponse reponse = new Réponse();

        reponse.setId(cursor.getInt(NUM_COL_ID_REPONSE));
        reponse.setIntitulé(cursor.getString(NUM_COL_INTITULE_REPONSE));
        reponse.setBool(cursor.getInt(NUM_COL_BOOL_REPONSE) > 0);
        reponse.setIdQuestion(cursor.getInt(NUM_COL_IDQUESTION_REPONSE));

        cursor.close();

        return reponse;
    }

    public List<Question> getQuizzQuestions(int idQuizz) {
        Cursor cursor = db.query(TABLE_QUESTION, new String[] {COL_ID_QUESTION, COL_INTITULE_QUESTION, COL_IDQUIZZ_QUESTION}, COL_IDQUIZZ_QUESTION + " = " + idQuizz, null, null, null, null);

        if(cursor.getCount() == 0) {
            return null;
        }
        List<Question> questionList = new ArrayList<>();

        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            Question question = new Question();
            question.setId(cursor.getInt(NUM_COL_ID_QUESTION));
            question.setIntitulé(cursor.getString(NUM_COL_INTITULE_QUESTION));
            question.setIdQuizz(cursor.getInt(NUM_COL_IDQUIZZ_QUESTION));
            questionList.add(question);
            cursor.moveToNext();
        }
        cursor.close();

        return questionList;
    }
}
