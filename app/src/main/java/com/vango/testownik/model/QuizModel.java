package com.vango.testownik.model;

import com.vango.testownik.model.room.Izs;
import com.vango.testownik.model.room.Kodowanie;
import com.vango.testownik.model.room.Miernictwo;
import com.vango.testownik.model.room.Pair;
import com.vango.testownik.model.room.Po;
import com.vango.testownik.model.room.Pps;
import com.vango.testownik.model.room.Pps2;
import com.vango.testownik.model.room.Pt;
import com.vango.testownik.util.QuizNames;

import java.util.NoSuchElementException;

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
    @ColumnInfo(defaultValue = "5")
    private Integer count = 5;

    public static Integer wrong=3;
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

    public QuizModel(Integer id, String question, Answer answerA, Answer answerB, Answer answerC, Answer answerD, Integer count) {
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

    public String countForLayout(){
        return "Pozostałe powtórzenia: "+ count;
    }
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void answerGood(){
        count= getCount()-1;
    }
    public void answerWrong(){
        count= getCount()+wrong;
    }

    public QuizModel cast(String whichQuiz){
        switch (whichQuiz) {
            case QuizNames.miernictwo:
                return new Miernictwo(id, question, answerA, answerB, answerC, answerD);
            case QuizNames.pair:
                return new Pair(id, question, answerA, answerB, answerC, answerD);
            case QuizNames.pt:
                return new Pt(id, question, answerA, answerB, answerC, answerD);
            case QuizNames.pps:
                return new Pps(id, question, answerA, answerB, answerC, answerD);
            case QuizNames.pps2:
                return new Pps2(id, question, answerA, answerB, answerC, answerD);
            case QuizNames.izs:
                return new Izs(id, question, answerA, answerB, answerC, answerD);
            case QuizNames.po:
                return new Po(id, question, answerA, answerB, answerC, answerD);
            case QuizNames.kodowanie:
                return new Kodowanie(id,question,answerA,answerB,answerC,answerD);
        }
        throw new NoSuchElementException("Can't find such quizname");
    }

}
