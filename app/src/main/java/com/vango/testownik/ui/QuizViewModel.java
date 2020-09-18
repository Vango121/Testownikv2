package com.vango.testownik.ui;

import android.util.Log;
import android.widget.Button;

import com.vango.testownik.model.Answer;
import com.vango.testownik.model.QuizModel;
import com.vango.testownik.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public class QuizViewModel extends ViewModel {
    private Repository repository;

    private SavedStateHandle savedStateHandle;
    static Integer currentId;
     LiveData<List<QuizModel>> questions;
    MutableLiveData<QuizModel> nextQuestion = new MutableLiveData<>();
    MutableLiveData<Integer> questionCount=new MutableLiveData<>(0);
    MutableLiveData<Integer> goodAnswers=new MutableLiveData<>(0);
    @ViewModelInject
    public QuizViewModel(Repository repository, SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;
    }

    void getQuestions(String quizName){
        questions = repository.getData(quizName);
    }


    void nextQuestion(){
        Random rand = new Random();
        int nextId = rand.nextInt((questions.getValue().size()) + 1);
        Log.i("nextint",nextId+"");
        nextQuestion.setValue(questions.getValue().get(nextId));
        currentId=nextId;
    }
    public Boolean checkAnswers(Button[] buttons, boolean[] buttonsChecked){
        questionCount.setValue(questionCount.getValue()+1);
        for (int i = 0; i <buttons.length; i++) {
           Answer answer= (Answer) buttons[i].getTag();
           if(answer.getCorrect()!=buttonsChecked[i]) return false;
        }
        goodAnswers.setValue(goodAnswers.getValue()+1);
        return true;
    }
}