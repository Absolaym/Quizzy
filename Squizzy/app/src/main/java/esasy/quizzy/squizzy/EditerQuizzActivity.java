package esasy.quizzy.squizzy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EditerQuizzActivity extends AppCompatActivity implements View.OnClickListener {

    Database quizzDB = new Database(this);
    private Spinner choixQuizz;
    private Button selectQuizz;
    private List<Quizz> quizzList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editer_quizz);

        //on crée le bouton (qui sert à valider le choix du quizz sélectionné dans le spinner) et le spinner
        selectQuizz = (Button) findViewById(R.id.eq_select_quizz);
        choixQuizz = (Spinner) findViewById(R.id.eq_choix_quizz);

        //on place un listener sur le bouton
        selectQuizz.setOnClickListener(this);

        //on ouvre la base
        quizzDB.open();

        List<String> quizzNames = new ArrayList<>();

        /*on récupère tous les quizz dans une liste :
            on aurait pu récupérer seulement leur nom, mais on veut pouvoir connaître l'id du quizz qui sera sélectionné dans le spinner pour pouvoir faire une recherche sur l'id et pas le nom :
                vu qu'on connait la position du quizz sélectionné dans le spinner, pour avoir son id il suffit de récupérer l'id du quizz à la même position dans la liste de quizz*/
        quizzList = quizzDB.getAllQuizz();

        int quizzListLen = quizzList.size();

        //on récupère le nom des quizz dans une liste pour pouvoir les afficher sur le spinner
        for(int i = 0; i < quizzListLen; i++) {
            quizzNames.add(quizzList.get(i).getNom());
        }

        //on paramètre le spinner
        ArrayAdapter<String> namesAdaptater = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, quizzNames);
        choixQuizz.setAdapter(namesAdaptater);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()) {
            case R.id.eq_select_quizz:
                //on récupère ce qui a été choisi dans le spinner et sa position
                String choix = choixQuizz.getSelectedItem().toString();
                int posId = choixQuizz.getSelectedItemPosition();

                Toast.makeText(this, choix + " sélectionné", Toast.LENGTH_LONG).show();
                intent = new Intent(EditerQuizzActivity.this, CreerQuizzActivity.class);

                //on passe le nom du quizz et son id à l'activité qui sera lancée
                intent.putExtra("nomQuizz", choix);
                //on récupère l'id via la liste de quizz
                intent.putExtra("idQuizz", quizzList.get(posId).getId());
                startActivity(intent);
                break;
        }
    }

}
