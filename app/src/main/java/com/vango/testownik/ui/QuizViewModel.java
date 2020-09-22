package com.vango.testownik.ui;

import android.util.Log;
import android.widget.Button;

import com.vango.testownik.model.Answer;
import com.vango.testownik.model.QuizModel;
import com.vango.testownik.model.room.Miernictwo;
import com.vango.testownik.model.room.Pair;
import com.vango.testownik.repository.Repository;
import com.vango.testownik.util.Event;
import com.vango.testownik.util.QuizNames;

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
    LiveData<List<Pair>> questionsPair;
    LiveData<List<QuizModel>> retrofit;
    MutableLiveData<QuizModel> nextQuestion = new MutableLiveData<>();
    public MutableLiveData<Integer> questionCount=new MutableLiveData<>(0);
    public MutableLiveData<Integer> goodAnswers=new MutableLiveData<>(0);
    List<QuizModel> questions = new ArrayList<>();
    Integer repeatsCount; // count all questions including count parameter inside question
    LiveData<Integer> rowCount;
    List<Integer> countList = new ArrayList<>();
    Boolean saveEnabled;
    Integer startingCount; // Count value on start without save
    String quizString;

    @ViewModelInject
    public QuizViewModel(Repository repository, SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;
        rowCount=repository.getRowCount();
        QuizModel.wrong=Integer.parseInt(repository.getWrongMultipler());
        saveEnabled=repository.getIsSave();
        startingCount = Integer.parseInt(repository.getStartingCount());
    }
    void getCount(String quizName){

        if(saveEnabled){ // is save turned on in settings
            countList =repository.getCount(quizName);
        }

    }
    void getQuestions(String quizName){
        quizString=quizName;
        if(quizName.equals(QuizNames.miernictwo)){
            questionsMiernictwo = repository.getAll();
        }
        else if (quizName.equals(QuizNames.pair)){
            questionsPair = repository.getAllPair();
        }
        retrofit= repository.getData(quizName);
    }

    void cast(){
        List<QuizModel> quizModelList = new ArrayList<>();
        if(quizString.equals(QuizNames.miernictwo)){
            for (int i = 0; i <questionsMiernictwo.getValue().size(); i++) {
                quizModelList.add(questionsMiernictwo.getValue().get(i).cast("Miernictwo"));
                if(countList.size()==questionsMiernictwo.getValue().size()){
                     quizModelList.get(i).setCount(countList.get(i));
                 }else{
                    quizModelList.get(i).setCount(startingCount);
                }
            }
        _questionsLiveData.postValue(new Event<>(quizModelList));
        }else if( quizString.equals(QuizNames.pair)){
            for (int i = 0; i <questionsPair.getValue().size(); i++) {
                quizModelList.add(questionsPair.getValue().get(i).cast());
                if(countList.size()==questionsPair.getValue().size()){
                    quizModelList.get(i).setCount(countList.get(i));
                }else{
                    quizModelList.get(i).setCount(startingCount);
                }
            }
            _questionsLiveData.postValue(new Event<>(quizModelList));
        }
    }
    void setQuestions(List<QuizModel> list){
        questions= list;
    }

    void saveCount(String quizName){
        repository.saveCount(questions,quizName);
    }


    void update(String quizName){
        if(quizName.equals(QuizNames.miernictwo)){
            for (int i = 0; i <questions.size() ; i++) {
                repository.update((Miernictwo)questions.get(i).cast(quizName));
            }
        }
    }
    void updateRetrofit(List<QuizModel> list, String quizName){

            for (int i = 0; i <list.size(); i++) {
                if(quizName.equals(QuizNames.miernictwo)){
                repository.update((Miernictwo)list.get(i).cast(quizName));
                }
                else if(quizName.equals(QuizNames.pair)){
                    repository.updatePair((Pair)retrofit.getValue().get(i));
                }
            }
    }
    void insert(String quizName){

            for (int i = 0; i <retrofit.getValue().size() ; i++) {
                if(quizName.equals(QuizNames.miernictwo)){
                repository.insert((Miernictwo) retrofit.getValue().get(i).cast(quizName));
                }
                else if(quizName.equals(QuizNames.pair)){
                   repository.insertPair((Pair)retrofit.getValue().get(i).cast(quizName));
                }
            }

    }

    void nextQuestion(){
        Log.i("repeats",repeatsCount+"");
        if(repeatsCount!=0){

            Log.i("nextquestion","true");
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
            Log.i("koniec","koniec");
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