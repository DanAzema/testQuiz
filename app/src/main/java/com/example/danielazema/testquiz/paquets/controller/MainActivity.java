package com.example.danielazema.testquiz.paquets.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.danielazema.testquiz.R;
import com.example.danielazema.testquiz.paquets.model.Utilisateur;

public class MainActivity extends AppCompatActivity {

    private static final String PREF_KEY_PRENOM = "PREF_KEY_PRENOM" ;
    private static final String PREF_KEY_SCORE = "PREF_KEY_SCORE";

    private TextView mTextAcccueil;
    private EditText mSaisiePrenom;
    private Button mBoutonJouer;
    private Utilisateur mUtilisateur;


    public static final int ACTIVITY_JEU_REQUEST_CODE = 69;
    private SharedPreferences mPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextAcccueil = (TextView) findViewById(R.id.activity_main_accueil_txt);
        mSaisiePrenom = (EditText) findViewById(R.id.activity_main_prenom_ntr);
        mBoutonJouer = (Button) findViewById(R.id.activity_main_jouer_btn);
        mBoutonJouer.setEnabled(false);
        mUtilisateur = new Utilisateur();
        mPreferences = getPreferences(MODE_PRIVATE);

        acceuillirUtilisateur();

        mSaisiePrenom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBoutonJouer.setEnabled(s.toString().length()>1);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mBoutonJouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUtilisateur.setPrenom(mSaisiePrenom.getText().toString());

                mPreferences.edit().putString(PREF_KEY_PRENOM, mUtilisateur.getPrenom()).apply();

                Intent jeuActivity = new Intent(MainActivity.this, JeuActivity.class);
                startActivityForResult(jeuActivity,ACTIVITY_JEU_REQUEST_CODE);
            }
        });
    }

    private void acceuillirUtilisateur() {
        String prenom = mPreferences.getString(PREF_KEY_PRENOM, null);
        if (null !=prenom){
            int score = mPreferences.getInt(PREF_KEY_SCORE,0);

            String texteComplet = "Content de vous revoir, "+prenom
                    +"!\nVotre dernier score était " + score
                    +", ferez vous mieux?";
            mTextAcccueil.setText(texteComplet);
            mSaisiePrenom.setText(prenom);
            mSaisiePrenom.setSelection(prenom.length());
            mBoutonJouer.setEnabled(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (ACTIVITY_JEU_REQUEST_CODE == requestCode && RESULT_OK == resultCode){
            // récupérer le score à partir de l'Intent
            int score = data.getIntExtra(JeuActivity.BUNDLE_EXTRA_SCORE,0);

            mPreferences.edit().putInt(PREF_KEY_SCORE, score).apply();
            acceuillirUtilisateur();

        }
    }
}
