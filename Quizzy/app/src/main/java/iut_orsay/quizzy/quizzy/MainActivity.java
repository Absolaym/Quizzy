package iut_orsay.quizzy.quizzy;

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

        Database quizzDB = new Database(this);
        Button lancerQuizz, créerQuizz, éditerQuizz;

        lancerQuizz = (Button) findViewById(R.id.lancer_quizz);
        créerQuizz = (Button) findViewById(R.id.creer_quizz);
        éditerQuizz = (Button) findViewById(R.id.editer_quizz);

        lancerQuizz.setOnClickListener(this);
        créerQuizz.setOnClickListener(this);
        éditerQuizz.setOnClickListener(this);

        quizzDB.open();
        quizzDB.upgrade();
        quizzDB.close();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch(v.getId()) {
            case R.id.creer_quizz:
                intent = new Intent(MainActivity.this, CreerQuizzActivity.class);
                startActivity(intent);
            break;
        }
    }
}
