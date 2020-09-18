package com.vango.testownik.model;

import java.util.Objects;

public class Answer {
    private String answer;
    private Boolean isCorrect;

    public Answer(String answer, Boolean isCorrect) {
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public boolean questionsEquals(Answer a){
        return answer.equals(a.getAnswer());
    }
    public boolean equalsAnswers(Answer a){
        return isCorrect==a.isCorrect;
    }

}
