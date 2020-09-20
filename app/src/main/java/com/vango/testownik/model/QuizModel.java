package com.vango.testownik.model;

import com.vango.testownik.model.room.Miernictwo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class QuizModel {
    @PrimaryKey
    private Integer id;
    private String question;
    private Answer answerA;
    private Answer answerB;
    private Answer answerC;
    private Answer answerD;
   // @ColumnInfo(defaultValue = "5")
    private Integer count;


    public QuizModel(Integer id, String question, Answer answerA, Answer answerB, Answer answerC, Answer answerD) {
        this.id = id;
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
    }

    public QuizModel(String question, Answer answerA, Answer answerB, Answer answerC, Answer answerD) {
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Answer getAnswerA() {
        return answerA;
    }

    public void setAnswerA(Answer answerA) {
        this.answerA = answerA;
    }

    public Answer getAnswerB() {
        return answerB;
    }

    public void setAnswerB(Answer answerB) {
        this.answerB = answerB;
    }

    public Answer getAnswerC() {
        return answerC;
    }

    public void setAnswerC(Answer answerC) {
        this.answerC = answerC;
    }

    public Answer getAnswerD() {
        return answerD;
    }

    public void setAnswerD(Answer answerD) {
        this.answerD = answerD;
    }


    public Integer getCount() {
        if(count == null){
            return 5;
        }
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void answerGood(){
        count= getCount()-1;
    }
    public void answerWrong(){
        count= getCount()+3;
    }

    public QuizModel cast(String whichQuiz){
        return new Miernictwo(id,question,answerA,answerB,answerC,answerD);
    }
}
