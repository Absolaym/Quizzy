package esasy.quizzy.squizzy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class LancerQuizzActivity extends AppCompatActivity {

    Database quizzDB = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lancer_quizz);

        quizzDB.open();

        List<Quizz> quizzList;
        List<String> quizzNames = new ArrayList<>();

        Quizz q = quizzDB.getQuizzWithNom("yolo1");

        if(q != null) {
            Toast.makeText(this, "Nom du quizz : " + q.getNom() + ", clé : " + q.getId(), Toast.LENGTH_LONG).show();
        }

        quizzList = quizzDB.getAllQuizz();

        int quizzListLen = quizzList.size();

        for(int i = 0; i < quizzListLen; i++) {
            quizzNames.add(quizzList.get(i).getNom());
        }

        if(quizzDB.getAllQuizz() == null) {
            Toast.makeText(this, "vide !", Toast.LENGTH_LONG).show();
            quizzNames.add("-- none ---");
        }

        Spinner spinner = (Spinner) findViewById(R.id.choix_quizz);
        ArrayAdapter<String> namesAdaptater = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, quizzNames);
        spinner.setAdapter(namesAdaptater);
    }

}
