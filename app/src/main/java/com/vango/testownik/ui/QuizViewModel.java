package com.vango.testownik.ui;

import android.widget.Button;

import com.vango.testownik.model.Answer;
import com.vango.testownik.model.QuizModel;
import com.vango.testownik.model.room.Izs;
import com.vango.testownik.model.room.Kodowanie;
import com.vango.testownik.model.room.Miernictwo;
import com.vango.testownik.model.room.Pair;
import com.vango.testownik.model.room.Po;
import com.vango.testownik.model.room.Pps;
import com.vango.testownik.model.room.Pps2;
import com.vango.testownik.model.room.Pt;
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

    LiveData<List<Miernictwo>> questionsMiernictwo; // questions from room db
    LiveData<List<Pair>> questionsPair;
    LiveData<List<Pt>> questionsPt;
    LiveData<List<Pps>> questionsPps;
    LiveData<List<Pps2>> questionsPps2;
    LiveData<List<Izs>> questionsIzs;
    LiveData<List<Po>> questionsPo;
    LiveData<List<Kodowanie>> questionsKodowanie;

    LiveData<List<QuizModel>> retrofit; // questions from web
    public MutableLiveData<QuizModel> nextQuestion = new MutableLiveData<>();
    MutableLiveData<Integer> questionCount=new MutableLiveData<>(0);
    MutableLiveData<Integer> goodAnswers=new MutableLiveData<>(0);
    MutableLiveData<Boolean> isFinished = new MutableLiveData<>(false);
    List<QuizModel> questions = new ArrayList<>();
    Integer repeatsCount; // count all questions including count parameter inside question
    LiveData<Integer> rowCount; // row count in room db
    List<Integer> countList = new ArrayList<>();
    Boolean saveEnabled;
    Integer startingCount; // Count value on start without save
    MutableLiveData<Integer> progress = new MutableLiveData<>(); // progress saved for progressbar
    String quizString;

    @ViewModelInject
    public QuizViewModel(Repository repository, SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;
        QuizModel.wrong=Integer.parseInt(repository.getWrongMultipler());
        saveEnabled=repository.getIsSave();
        startingCount = Integer.parseInt(repository.getStartingCount());
    }
    void getCount(String quizName){
        if(saveEnabled){ // is save turned on in settings
            countList =repository.getCount(quizName);
            questionCount.postValue(repository.getQuestionCount(quizName));
            goodAnswers.postValue(repository.getProgress(quizName));
        }else {
            repository.resetData(quizName);
        }

    }
    void getQuestions(String quizName){
        quizString=quizName;
        rowCount=repository.getRowCount(quizName);
        switch (quizName){
            case QuizNames.miernictwo:
                questionsMiernictwo = repository.getAll();
                break;
            case QuizNames.pair:
                questionsPair = repository.getAllPair();
                break;
            case QuizNames.pps:
                questionsPps = repository.getAllPps();
                break;
            case QuizNames.pps2:
                questionsPps2 = repository.getAllPps2();
                break;
            case QuizNames.pt:
                questionsPt = repository.getAllPt();
                break;
            case QuizNames.izs:
                questionsIzs = repository.getAllIzs();
                break;
            case QuizNames.po:
                questionsPo = repository.getAllPo();
                break;
            case QuizNames.kodowanie:
                questionsKodowanie = repository.getAllKodowanie();
                break;
        }
        retrofit= repository.getData(quizName);
    }

    void cast(){
        List<QuizModel> quizModelList = new ArrayList<>();
        switch (quizString) {
            case QuizNames.miernictwo:
                for (int i = 0; i < questionsMiernictwo.getValue().size(); i++) {
                    quizModelList.add(questionsMiernictwo.getValue().get(i).cast("Miernictwo"));
                    if (countList.size() == questionsMiernictwo.getValue().size()) {
                        quizModelList.get(i).setCount(countList.get(i));
                    } else {
                        quizModelList.get(i).setCount(startingCount);
                    }
                }
                _questionsLiveData.postValue(new Event<>(quizModelList));
                break;
            case QuizNames.pair:
                for (int i = 0; i < questionsPair.getValue().size(); i++) {
                    quizModelList.add(questionsPair.getValue().get(i).cast());
                    if (countList.size() == questionsPair.getValue().size()) {
                        quizModelList.get(i).setCount(countList.get(i));
                    } else {
                        quizModelList.get(i).setCount(startingCount);
                    }
                }
                _questionsLiveData.postValue(new Event<>(quizModelList));
                break;
            case QuizNames.pt:
                for (int i = 0; i < questionsPt.getValue().size(); i++) {
                    quizModelList.add(questionsPt.getValue().get(i).cast());
                    if (countList.size() == questionsPt.getValue().size()) {
                        quizModelList.get(i).setCount(countList.get(i));
                    } else {
                        quizModelList.get(i).setCount(startingCount);
                    }
                }
                _questionsLiveData.postValue(new Event<>(quizModelList));
                break;
            case QuizNames.pps:
                for (int i = 0; i < questionsPps.getValue().size(); i++) {
                    quizModelList.add(questionsPps.getValue().get(i).cast());
                    if (countList.size() == questionsPps.getValue().size()) {
                        quizModelList.get(i).setCount(countList.get(i));
                    } else {
                        quizModelList.get(i).setCount(startingCount);
                    }
                }
                _questionsLiveData.postValue(new Event<>(quizModelList));
                break;
            case QuizNames.pps2:
                for (int i = 0; i < questionsPps2.getValue().size(); i++) {
                    quizModelList.add(questionsPps2.getValue().get(i).cast());
                    if (countList.size() == questionsPps2.getValue().size()) {
                        quizModelList.get(i).setCount(countList.get(i));
                    } else {
                        quizModelList.get(i).setCount(startingCount);
                    }
                }
                _questionsLiveData.postValue(new Event<>(quizModelList));
                break;
            case QuizNames.izs:
                for (int i = 0; i < questionsIzs.getValue().size(); i++) {
                    quizModelList.add(questionsIzs.getValue().get(i).cast());
                    if (countList.size() == questionsIzs.getValue().size()) {
                        quizModelList.get(i).setCount(countList.get(i));
                    } else {
                        quizModelList.get(i).setCount(startingCount);
                    }
                }
                _questionsLiveData.postValue(new Event<>(quizModelList));
                break;
            case QuizNames.po:
                for (int i = 0; i < questionsPo.getValue().size(); i++) {
                    quizModelList.add(questionsPo.getValue().get(i).cast());
                    if (countList.size() == questionsPo.getValue().size()) {
                        quizModelList.get(i).setCount(countList.get(i));
                    } else {
                        quizModelList.get(i).setCount(startingCount);
                    }
                }
                _questionsLiveData.postValue(new Event<>(quizModelList));
                break;
            case QuizNames.kodowanie:
                for (int i = 0; i < questionsKodowanie.getValue().size(); i++) {
                    quizModelList.add(questionsKodowanie.getValue().get(i).cast());
                    if (countList.size() == questionsKodowanie.getValue().size()) {
                        quizModelList.get(i).setCount(countList.get(i));
                    } else {
                        quizModelList.get(i).setCount(startingCount);
                    }
                }
                _questionsLiveData.postValue(new Event<>(quizModelList));
                break;
        }
    }

    void setQuestions(List<QuizModel> list){
        questions= list;
    }

    void saveCount(String quizName){
        if(saveEnabled){
            repository.saveCount(questions,quizName);
            repository.saveProgress(quizName,goodAnswers.getValue());
            repository.saveQuestionCount(quizName,questionCount.getValue());
        }
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
                switch (quizName) {
                    case QuizNames.miernictwo:
                        repository.update((Miernictwo) list.get(i).cast(quizName));
                        break;
                    case QuizNames.pair:
                        repository.updatePair((Pair) retrofit.getValue().get(i).cast(QuizNames.pair));
                        break;
                    case QuizNames.pt:
                        repository.updatePt((Pt) retrofit.getValue().get(i).cast(QuizNames.pt));
                        break;
                    case QuizNames.pps:
                        repository.updatePps((Pps) retrofit.getValue().get(i).cast(QuizNames.pps));
                        break;
                    case QuizNames.pps2:
                        repository.updatePps2((Pps2) retrofit.getValue().get(i).cast(QuizNames.pps2));
                        break;
                    case QuizNames.izs:
                        repository.updateIzs((Izs) retrofit.getValue().get(i).cast(QuizNames.izs));
                        break;
                    case QuizNames.po:
                        repository.updatePo((Po) retrofit.getValue().get(i).cast(QuizNames.po));
                        break;
                    case QuizNames.kodowanie:
                        repository.updateKodowanie((Kodowanie) retrofit.getValue().get(i).cast(QuizNames.kodowanie));
                        break;
                }
            }
    }
    void insert(String quizName){
            for (int i = 0; i <retrofit.getValue().size() ; i++) {
                switch (quizName) {
                    case QuizNames.miernictwo:
                        repository.insert((Miernictwo) retrofit.getValue().get(i).cast(quizName));
                        break;
                    case QuizNames.pair:
                        repository.insertPair((Pair) retrofit.getValue().get(i).cast(quizName));
                        break;
                    case QuizNames.pt:
                        repository.insertPt((Pt) retrofit.getValue().get(i).cast(quizName));
                        break;
                    case QuizNames.pps:
                        repository.insertPps((Pps) retrofit.getValue().get(i).cast(quizName));
                        break;
                    case QuizNames.pps2:
                        repository.insertPps2((Pps2) retrofit.getValue().get(i).cast(quizName));
                        break;
                    case QuizNames.izs:
                        repository.insertIzs((Izs) retrofit.getValue().get(i).cast(quizName));
                        break;
                    case QuizNames.po:
                        repository.insertPo((Po) retrofit.getValue().get(i).cast(quizName));
                        break;
                    case QuizNames.kodowanie:
                        repository.insertKodowanie((Kodowanie) retrofit.getValue().get(i).cast(quizName));
                        break;
                }
            }
    }

    void nextQuestion(){
        if(repeatsCount!=0){
            Random rand = new Random();
            int nextId = rand.nextInt((questions.size()) );
            if(questions.get(nextId).getCount()!=0){
                nextQuestion.setValue(questions.get(nextId));
            }
            else{
                nextId = rand.nextInt((questions.size()));
            }
            currentId=nextId;
        }
        else{
            isFinished.postValue(true);
        }
    }
    public Boolean checkAnswers(Button[] buttons, boolean[] buttonsChecked){
        questionCount.setValue(questionCount.getValue()+1);
        for (int i = 0; i <buttons.length; i++) {
           Answer answer= (Answer) buttons[i].getTag();
           if(answer.getCorrect()!=buttonsChecked[i]) {
               questions.get(currentId).answerWrong();
               repeatsCount+=QuizModel.wrong;
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