package esasy.quizzy.squizzy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button lancerQuizz, créerQuizz, éditerQuizz;

        //on créé les trois boutons, on les associe à leur identifiant dans le fichier activity_main.xml
        lancerQuizz = (Button) findViewById(R.id.lancer_quizz);
        créerQuizz = (Button) findViewById(R.id.creer_quizz);
        éditerQuizz = (Button) findViewById(R.id.editer_quizz);

        //on place des listeners dessus
        lancerQuizz.setOnClickListener(this);
        créerQuizz.setOnClickListener(this);
        éditerQuizz.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()) {
            //en fonction de l'id du bouton cliqué, on lance l'activité associée
            case R.id.creer_quizz:
                intent = new Intent(MainActivity.this, CreerQuizzActivity.class);
                startActivity(intent);
                break;

            case R.id.lancer_quizz:
                intent = new Intent(MainActivity.this, LancerQuizzActivity.class);
                startActivity(intent);
                break;

            case R.id.editer_quizz:
                intent = new Intent(MainActivity.this, EditerQuizzActivity.class);
                startActivity(intent);
                break;
        }
    }
}
