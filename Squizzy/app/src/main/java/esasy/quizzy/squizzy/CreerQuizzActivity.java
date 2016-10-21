package esasy.quizzy.squizzy;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreerQuizzActivity extends AppCompatActivity implements View.OnClickListener {
    Database quizzDB = new Database(this);
    private EditText input_nomQuizz;
    String quizzNameFromEdit;
    int quizzIdFromEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creer_quizz);

        //on créé le champs de saisie du nom du quizz, les boutons sauvegarder et supprimer
        input_nomQuizz = (EditText) findViewById(R.id.nom_quizz);
        Button sauvQuizz = (Button) findViewById(R.id.bouton_sauv);
        Button supprQuizz = (Button) findViewById(R.id.bouton_suppr);

        //on récupère ce qui a potentiellement été envoyé d'EditerQuizzActivity (nom + id du quizz)
        if(savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();

            if(extras != null) {
                quizzNameFromEdit = extras.getString("nomQuizz");
                quizzIdFromEdit = extras.getInt("idQuizz");
            }
        }

        else {
            quizzNameFromEdit = (String) savedInstanceState.getSerializable("nomQuizz");
            quizzIdFromEdit = (int) savedInstanceState.getSerializable("idQuizz");
        }

        //si on est passé par EditerQuizzActivity pour arriver sur cette activité, on pré-remplit le nom du quizz dans le champs de saisie du nom
        if(quizzNameFromEdit != null) {
            Toast.makeText(this, quizzNameFromEdit + " reçu !", Toast.LENGTH_LONG).show();
            input_nomQuizz.setText(quizzNameFromEdit);
        }

        //on place les listeners sur les boutons
        sauvQuizz.setOnClickListener(this);
        supprQuizz.setOnClickListener(this);

        quizzDB.open();
    }

    @Override
    public void onClick(View v) {
        final Context context = this;
        //pour les fenêtres de confirmation de suppression/sauvegarde
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        AlertDialog alertDialog;

        //on récupère ce qu'il y a dans le champ de saisie du nom
        final String inInput = input_nomQuizz.getText().toString();
        switch(v.getId()) {
            case R.id.bouton_sauv:

                //pour la fenêtre de sauvegarde
                alertDialogBuilder.setTitle("Sauvegarde");
                alertDialogBuilder.setMessage("Voulez-vous sauvegardez le quizz ?");
                alertDialogBuilder.setCancelable(false);

                //on définit ce qu'il se passe si on appuie sur oui
                alertDialogBuilder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //si on n'a pas reçu de nom de quizz, c'est à dire si on a cliqué sur créer quizz dans le menu principal
                        if(quizzNameFromEdit == null) {
                            //si on n'a pas indiqué de nom de quizz, on affiche un toast d'erreur
                            if(inInput.equals("")) {
                                Toast.makeText(context, "Nom du quizz obligatoire !", Toast.LENGTH_LONG).show();
                            }

                            //sinon on créé un nouveau quizz qui a pour nom ce qui est contenu dans le champs de saisie du nom du quizz
                            else {
                                Quizz quizz = new Quizz(inInput);

                                //on importe le quizz dans la base
                                quizzDB.insertQuizz(quizz);
                                Toast.makeText(context, "Quizz " + quizz.getNom() + " créé, clé : " + quizzDB.getQuizzWithNom(inInput).getId(), Toast.LENGTH_LONG).show();
                            }
                        }

                        //sinon, c'est qu'on est passé par EditerQuizzActivity
                        else {
                            //on récupère le quizz via l'id qui a été passé par EditerQuizzActivity
                            Quizz quizz = quizzDB.getQuizzWithId(quizzIdFromEdit);
                            String oldName = quizz.getNom();

                            //on met à jour le nom avec ce qui est contenu dans le champ de saisie du nom
                            quizz.setNom(inInput);
                            quizzDB.updateQuizz(quizz, quizz.getId());
                            Toast.makeText(context, "Quizz " + oldName + " modifié en " + quizz.getNom(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

                //on définit ce qu'il se passe si on appuie sur non (c'est-à-dire rien dans notre cas)
                alertDialogBuilder.setNegativeButton("Non",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                //on construit puis affiche la fenêtre de dialogue
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();

            break;

            case R.id.bouton_suppr:

                //pour la fenêtre de suppression
                alertDialogBuilder.setTitle("Suppression");
                alertDialogBuilder.setMessage("Voulez-vous supprimer le quizz ?");
                alertDialogBuilder.setCancelable(false);

                //on définit pour oui
                alertDialogBuilder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //si le nom du quizz est pas pré-rempli, c'est qu'on est arrivé ici directement via le menu principal, on n'a donc pas encore créé de quizz donc on n'a rien a supprimé
                        if(inInput.equals("")) {
                            Toast.makeText(context, "Pas de quizz à supprimer", Toast.LENGTH_LONG).show();
                            dialog.cancel();
                        }

                        else {
                            //sinon soit le champ de saisie est rempli mais on ne vient pas d'EditerQuizzActivity, on se contente donc juste de remettre à 0 les champs
                            if(quizzNameFromEdit == null) {
                                input_nomQuizz.setText("");
                                dialog.cancel();
                            }

                            //soit on vient d'EditerQuizzActivity, auquel cas on supprime le quizz via l'id récupérer de l'activité précédente
                            else{
                                Quizz quizz = quizzDB.getQuizzWithId(quizzIdFromEdit);
                                quizzDB.removeQuizz(quizzIdFromEdit);
                                Toast.makeText(context, "Quizz " + quizz.getNom() + "supprimé !", Toast.LENGTH_LONG).show();
                                dialog.cancel();
                            }
                        }
                        //dans tous les cas on remet le champ de saisie du nom à 0
                        input_nomQuizz.setText("");
                    }
                });

                //on définit pour non (i.e on fait rien)
                alertDialogBuilder.setNegativeButton("Non",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                //on créé et affiche la fenêtre de dialogue
                alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            break;
        }
    }
}
