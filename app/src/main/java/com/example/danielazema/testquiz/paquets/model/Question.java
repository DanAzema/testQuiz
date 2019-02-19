package com.example.danielazema.testquiz.paquets.model;

import java.util.List;

public class Question {
    private String mQuestion;
    private List<String> mChoixPossibles;
    private int mIndexReponse;

    public Question(String question, List<String> choixPossibles, int indexReponse) {
        this.setQuestion(question);
        this.setChoixPossibles(choixPossibles);
        this.setIndexReponse(indexReponse);
    }


    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public List<String> getChoixPossibles() {
        return mChoixPossibles;
    }

    public void setChoixPossibles(List<String> choixPossibles) {
        mChoixPossibles = choixPossibles;
    }

    public int getIndexReponse() {
        return mIndexReponse;
    }

    public void setIndexReponse(int indexReponse) {
        if (indexReponse < 0 || indexReponse >= mChoixPossibles.size()) {
            throw new IllegalArgumentException("Réponse hors des limites");
        }
        mIndexReponse = indexReponse;
    }
}
