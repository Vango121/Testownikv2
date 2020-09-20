package com.vango.testownik.ui;

import android.util.Log;
import android.widget.Button;

import com.vango.testownik.model.Answer;
import com.vango.testownik.model.QuizModel;
import com.vango.testownik.model.room.Miernictwo;
import com.vango.testownik.repository.Repository;
import com.vango.testownik.util.Event;

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
     private MutableLiveData<Event<List<QuizModel>>> _questionsLiveData = new MutableLiveData<>();
    LiveData<Event<List<QuizModel>>> questionsLiveData() {
        return _questionsLiveData;}
    LiveData<List<Miernictwo>> questionsMiernictwo;
    LiveData<List<QuizModel>> retrofit;
    MutableLiveData<QuizModel> nextQuestion = new MutableLiveData<>();
    public MutableLiveData<Integer> questionCount=new MutableLiveData<>(0);
    public MutableLiveData<Integer> goodAnswers=new MutableLiveData<>(0);
    List<QuizModel> questions = new ArrayList<>();
    Integer repeatsCount; // count all questions including count parameter inside question
    LiveData<Integer> rowCount;
    @ViewModelInject
    public QuizViewModel(Repository repository, SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;
        rowCount=repository.getRowCount();
    }

    void getQuestions(String quizName){
        if(quizName.equals("Miernictwo")){
            questionsMiernictwo = repository.getAll();
        }
        retrofit= repository.getData(quizName);
    }

    void cast(){
        List<QuizModel> quizModelList = new ArrayList<>();
        for (int i = 0; i <questionsMiernictwo.getValue().size(); i++) {
            quizModelList.add(questionsMiernictwo.getValue().get(i).cast());
            Log.i("questionsMiernictwo",questionsMiernictwo.getValue().get(i).getQuestion());
        }
        _questionsLiveData.postValue(new Event<>(quizModelList));
    }
    void setQuestions(List<QuizModel> list){
        questions= list;
    }
        void update(String quizName){
            if(quizName.equals("Miernictwo")){
                for (int i = 0; i <questions.size() ; i++) {
                    repository.update((Miernictwo)questions.get(i).cast(quizName));
                }
            }
        }
    void insert(String quizName){
        if(quizName.equals("Miernictwo")){
            for (int i = 0; i <questions.size() ; i++) {
                repository.insert((Miernictwo) questions.get(i).cast(quizName));
            }
        }
    }
    void nextQuestion(){
        if(repeatsCount!=0){
            Random rand = new Random();
            int nextId = rand.nextInt((questions.size()) );
            Log.i("nextint",nextId+"");
            if(questions.get(nextId).getCount()!=0){
                nextQuestion.setValue(questions.get(nextId));
            }
            else{
                nextId = rand.nextInt((questions.size()));
            }
            currentId=nextId;
        }
        else{
            //TODO("End of testownik")
        }
    }
    public Boolean checkAnswers(Button[] buttons, boolean[] buttonsChecked){
        questionCount.setValue(questionCount.getValue()+1);
        for (int i = 0; i <buttons.length; i++) {
           Answer answer= (Answer) buttons[i].getTag();
           if(answer.getCorrect()!=buttonsChecked[i]) {
               questions.get(currentId).answerWrong();
               repeatsCount+=3;
               return false;
           }
        }
        questions.get(currentId).answerGood();
        repeatsCount-=1;
        goodAnswers.setValue(goodAnswers.getValue()+1);
        return true;
    }

    public void getNumberOfQuestions(){
        int toReturn =0;
        for (int i = 0; i <questions.size(); i++) {
           toReturn+= questions.get(i).getCount();
        }
        repeatsCount=toReturn;

    }


}