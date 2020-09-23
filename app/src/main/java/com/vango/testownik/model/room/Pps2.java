package com.vango.testownik.model.room;

import com.vango.testownik.model.Answer;
import com.vango.testownik.model.QuizModel;

import androidx.room.Entity;

@Entity(tableName = "pps2_table")
public class Pps2 extends QuizModel {

    public Pps2(Integer id, String question, Answer answerA, Answer answerB, Answer answerC, Answer answerD) {
        super(id, question, answerA, answerB, answerC, answerD);
    }

    public QuizModel cast(){
        return new QuizModel(getId(),getQuestion(),getAnswerA(),getAnswerB(),getAnswerC(),getAnswerD());
    }
}
