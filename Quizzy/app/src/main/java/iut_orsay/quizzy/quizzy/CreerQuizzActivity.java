package iut_orsay.quizzy.quizzy;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

public class CreerQuizzActivity extends AppCompatActivity implements View.OnClickListener {
    Database quizzDB = new Database(this);
    private EditText edit_nomQuizz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creer_quizz);

        edit_nomQuizz = (EditText) findViewById(R.id.cqz_nom_quizz);
        Button sauvQuizz = (Button) findViewById(R.id.cqz_bouton_sauv);

        sauvQuizz.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final Context context = this;
        final String inputNomQuizz = edit_nomQuizz.getText().toString();

        if(v.getId() == R.id.cqz_bouton_sauv) {
            Quizz quizz = new Quizz(inputNomQuizz);

            int quizzId = toIntExact(quizzDB.insertQuizz(quizz));

            Toast.makeText(context, "id quizz : " + quizzId , Toast.LENGTH_SHORT).show();
        }
    }



}
