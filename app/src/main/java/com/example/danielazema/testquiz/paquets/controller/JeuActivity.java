package com.example.danielazema.testquiz.paquets.controller;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.danielazema.testquiz.R;
import com.example.danielazema.testquiz.paquets.model.Question;
import com.example.danielazema.testquiz.paquets.model.BanqueDeQuestions;

import java.util.Arrays;

public class JeuActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextQuestion;
    private Button mBoutonRep1;
    private Button mBoutonRep2;
    private Button mBoutonRep3;
    private Button mBoutonRep4;
    private BanqueDeQuestions mBanqueDeQuestions;
    private Question mQuestionCourante;

    private int mScore;
    private int mNbrQuestions;

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE = "scoreCourant";
    public static final String BUNDLE_STATE_QUESTION = "questionCourante";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);

        mBanqueDeQuestions = this.creerQuestions();

        if(savedInstanceState != null){
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mNbrQuestions = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);
        }
        else {
            mScore = 0;
            mNbrQuestions = 4;
        }

        // relier les widgets
        mTextQuestion = (TextView) findViewById(R.id.activity_jeu_question_txt);
        mBoutonRep1 = (Button) findViewById(R.id.activity_jeu_rep1_btn);
        mBoutonRep2 = (Button) findViewById(R.id.activity_jeu_rep2_btn);
        mBoutonRep3 = (Button) findViewById(R.id.activity_jeu_rep3_btn);
        mBoutonRep4 = (Button) findViewById(R.id.activity_jeu_rep4_btn);

        // utiliser la propriété tag pour désigner les boutons
        mBoutonRep1.setTag(0);
        mBoutonRep2.setTag(1);
        mBoutonRep3.setTag(2);
        mBoutonRep4.setTag(3);

        mBoutonRep1.setOnClickListener(this);
        mBoutonRep2.setOnClickListener(this);
        mBoutonRep3.setOnClickListener(this);
        mBoutonRep4.setOnClickListener(this);

        mQuestionCourante = mBanqueDeQuestions.getQuestion();
        this.afficheQuestion(mQuestionCourante);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mNbrQuestions);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v){
        int indexReponse = (int) v.getTag();
        if(indexReponse == mQuestionCourante.getIndexReponse()){
            // bonne réponse
            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
            mScore++;
        }
        else {
            // mauvaise réponse
            Toast.makeText(this,"Mauvaise Réponse",Toast.LENGTH_SHORT).show();
        }

        if (--mNbrQuestions == 0){
            //on atteint le total de questions à poser : fin du jeu
            finJeu();
        }
        else {
            mQuestionCourante = mBanqueDeQuestions.getQuestion();
            afficheQuestion(mQuestionCourante);
        }
    }

    private void finJeu(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Bien joué!")
                .setMessage("Votre score est "+ mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // finir l'activité
                        android.content.Intent intent = new android.content.Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .setCancelable(false)
                .create()
                .show();

    }

    private void afficheQuestion(final Question question){
        // établit le texte pour la question et les 4 boutons réponse
        mTextQuestion.setText(question.getQuestion());
        mBoutonRep1.setText(question.getChoixPossibles().get(0));
        mBoutonRep2.setText(question.getChoixPossibles().get(1));
        mBoutonRep3.setText(question.getChoixPossibles().get(2));
        mBoutonRep4.setText(question.getChoixPossibles().get(3));
    }


    private BanqueDeQuestions creerQuestions(){
        Question question1 = new Question("Quel est le nom du président Français actuel?",
                Arrays.asList("François Hollande","Emmanuel Macron","Jacques Chirac","Valéri Giscard d'Estaing"),1);

        Question question2 = new Question("Combien de Pays dans l'Union Européenne",
                Arrays.asList("15","24","28","32"),2);

        Question question3 = new Question("Année du prmier homme sur la lune?",
                Arrays.asList("1958","1962","1967","1969"),3);

        Question question4 = new Question("Où est enterré Frederic Chopin?",
                Arrays.asList("Strasbourg","Varsovie","Paris","Moscou"),2);

        Question question5 = new Question("D'où vient le nom du mouvement Gilets Jaunes",
                Arrays.asList("Briseurs de grève","De la couleur de la fièvre","Ils sont d'origine asiatique","Cela trainait par là"),3);

        Question question6 = new Question("De quel ouvrage de la Bible est tiré l’expression “rien de nouveau sous le Soleil ?",
                Arrays.asList("La Genèse","Le Cantique des cantiques","Le Livre de Job","L'Ecclésiate"),3);

        Question question7 = new Question("À propos de quoi Napoléon III déclara-t-il : “Il ne faut jamais dire jamais” ?",
                Arrays.asList("De sa propre abdication","De l'annexion par la jeune Italie de la Rome papale",
                        "De l'accord de constitution libérale aux députés","D'une éventuelle guerre avec les Russes"),1);

        return  new BanqueDeQuestions(Arrays.asList(
                    question1,
                    question2,
                    question3,
                    question4,
                    question5,
                    question6,
                    question7));

    }
}
