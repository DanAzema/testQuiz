package com.example.danielazema.testquiz.paquets.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class BanqueDeQuestions {
    private java.util.List<Question> mQuestionList;
    private int mIndexQuestionSuivante;

    public BanqueDeQuestions(List<Question> questionList){
        // mélanger l'ordre des questions, puis les stocker
        mQuestionList = questionList;
        Collections.shuffle(mQuestionList);
        mIndexQuestionSuivante = 0;

    }

    public Question getQuestion(){
        // boucle sur les questions et retourne la suivante à chaque appel
        if (mIndexQuestionSuivante == mQuestionList.size()){
            mIndexQuestionSuivante = 0;
        }
        return mQuestionList.get(mIndexQuestionSuivante++);
    }

    public List<Question> getQuestionList() {
        return mQuestionList;
    }
}
