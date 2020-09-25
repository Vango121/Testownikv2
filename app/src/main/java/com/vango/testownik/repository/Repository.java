package com.vango.testownik.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vango.testownik.model.QuizModel;
import com.vango.testownik.model.room.Izs;
import com.vango.testownik.model.room.Kodowanie;
import com.vango.testownik.model.room.Miernictwo;
import com.vango.testownik.model.room.Pair;
import com.vango.testownik.model.room.Po;
import com.vango.testownik.model.room.Pps;
import com.vango.testownik.model.room.Pps2;
import com.vango.testownik.model.room.Pt;
import com.vango.testownik.room.Dao.IzsDao;
import com.vango.testownik.room.Dao.KodowanieDao;
import com.vango.testownik.room.Dao.MiernictwoDao;
import com.vango.testownik.room.Dao.PairDao;
import com.vango.testownik.room.Dao.PoDao;
import com.vango.testownik.room.Dao.Pps2Dao;
import com.vango.testownik.room.Dao.PpsDao;
import com.vango.testownik.room.Dao.PtDao;
import com.vango.testownik.util.QuizNames;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    Retrofit retrofit;
    MiernictwoDao miernictwoDao;
    Context context;
    PairDao pairDao;
    PtDao ptDao;
    PpsDao ppsDao;
    Pps2Dao pps2Dao;
    IzsDao izsDao;
    PoDao poDao;
    KodowanieDao kodowanieDao;
    private MutableLiveData<List<QuizModel>> questions = new MutableLiveData<>();
    @Inject
    public Repository(Retrofit retrofit,
                      MiernictwoDao miernictwoDao,
                      PairDao pairDao,PtDao ptDao,
                      PpsDao ppsDao,Pps2Dao pps2Dao,
                      IzsDao izsDao,
                      PoDao poDao,
                      KodowanieDao kodowanieDao,
                      @ApplicationContext Context context) {
        this.retrofit = retrofit;
        this.miernictwoDao=miernictwoDao;
        this.context=context;
        this.pairDao=pairDao;
        this.ptDao=ptDao;
        this.ppsDao=ppsDao;
        this.pps2Dao=pps2Dao;
        this.izsDao=izsDao;
        this.poDao=poDao;
        this.kodowanieDao=kodowanieDao;
    }
    public LiveData<List<QuizModel>> getData(String table){
        Call<List<QuizModel>> call= retrofit.getRetrofitInterface().getQuestions(table);
        call.enqueue(new Callback<List<QuizModel>>() {
            @Override
            public void onResponse(Call<List<QuizModel>> call, Response<List<QuizModel>> response) {
                questions.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<QuizModel>> call, Throwable t) {
            }
        });
        return questions;
    }
    public void insert(Miernictwo miernictwo){
            Completable.fromAction(() -> miernictwoDao.insert(miernictwo)).subscribeOn(Schedulers.io()).subscribe();
    }
    public void insertPair(Pair pair){
        Completable.fromAction(() -> pairDao.insert(pair)).subscribeOn(Schedulers.io()).subscribe();
    }
    public void insertPt(Pt pt){
        Completable.fromAction(() -> ptDao.insert(pt)).subscribeOn(Schedulers.io()).subscribe();
    }
    public void insertPps(Pps pps){
        Completable.fromAction(() -> ppsDao.insert(pps)).subscribeOn(Schedulers.io()).subscribe();
    }
    public void insertPps2(Pps2 pps2){
        Completable.fromAction(() -> pps2Dao.insert(pps2)).subscribeOn(Schedulers.io()).subscribe();
    }
    public void insertIzs(Izs izs){
        Completable.fromAction(() -> izsDao.insert(izs)).subscribeOn(Schedulers.io()).subscribe();
    }
    public void insertPo(Po po){
        Completable.fromAction(() -> poDao.insert(po)).subscribeOn(Schedulers.io()).subscribe();
    }
    public void insertKodowanie(Kodowanie kodowanie){
        Completable.fromAction(() -> kodowanieDao.insert(kodowanie)).subscribeOn(Schedulers.io()).subscribe();
    }


    public LiveData<List<Miernictwo>> getAll(){
       return miernictwoDao.getAllQuestions();
    }
    public LiveData<List<Pair>> getAllPair(){
        return pairDao.getAllQuestions();
    }
    public LiveData<List<Pps>> getAllPps(){
        return ppsDao.getAllQuestions();
    }
    public LiveData<List<Pps2>> getAllPps2(){
        return pps2Dao.getAllQuestions();
    }
    public LiveData<List<Pt>> getAllPt(){
        return ptDao.getAllQuestions();
    }
    public LiveData<List<Izs>> getAllIzs(){
        return izsDao.getAllQuestions();
    }
    public LiveData<List<Po>> getAllPo(){
        return poDao.getAllQuestions();
    }
    public LiveData<List<Kodowanie>> getAllKodowanie(){
        return kodowanieDao.getAllQuestions();
    }

    public void update(Miernictwo miernictwo){
        Completable.fromAction(() -> miernictwoDao.update(miernictwo)).subscribeOn(Schedulers.io()).subscribe();
    }
    public void updatePair(Pair pair){
        Completable.fromAction(() -> pairDao.update(pair)).subscribeOn(Schedulers.io()).subscribe();
    }
    public void updatePt(Pt pt){
        Completable.fromAction(() -> ptDao.update(pt)).subscribeOn(Schedulers.io()).subscribe();
    }
    public void updatePps(Pps pps){
        Completable.fromAction(() -> ppsDao.update(pps)).subscribeOn(Schedulers.io()).subscribe();
    }
    public void updatePps2(Pps2 pps2){
        Completable.fromAction(() -> pps2Dao.update(pps2)).subscribeOn(Schedulers.io()).subscribe();
    }
    public void updatePo(Po po){
        Completable.fromAction(() -> poDao.update(po)).subscribeOn(Schedulers.io()).subscribe();
    }
    public void updateIzs(Izs izs){
        Completable.fromAction(() -> izsDao.update(izs)).subscribeOn(Schedulers.io()).subscribe();
    }
    public void updateKodowanie(Kodowanie kodowanie){
        Completable.fromAction(() -> kodowanieDao.update(kodowanie)).subscribeOn(Schedulers.io()).subscribe();
    }

    public LiveData<Integer> getRowCount(String quizName){
        switch (quizName){
            case QuizNames.miernictwo: return miernictwoDao.getRowCount();
            case QuizNames.pair: return pairDao.getRowCount();
            case QuizNames.pt: return ptDao.getRowCount();
            case QuizNames.pps: return ppsDao.getRowCount();
            case QuizNames.pps2: return pps2Dao.getRowCount();
            case QuizNames.izs: return izsDao.getRowCount();
            case QuizNames.po: return poDao.getRowCount();
            case QuizNames.kodowanie: return kodowanieDao.getRowCount();
        }
        throw new NoSuchElementException("Can't find such quiz");
    }

    public void saveCount(List<QuizModel> list,String quizName){
        List<Integer> listCount= new ArrayList<>();
        for (int i = 0; i <list.size(); i++) {
            listCount.add(list.get(i).getCount());
        }
        SharedPreferences sharedPreferences =PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        sharedPreferences.edit().putString(quizName+"count",new Gson().toJson(listCount)).apply();
    }
    public List<Integer> getCount(String quizName){
        SharedPreferences sharedPreferences =PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String sharedReturned =sharedPreferences.getString(quizName+"count","");
        Type type =new TypeToken<List<Integer>>(){}.getType();
        if(!sharedReturned.equals("")){
            List<Integer>some_list = new Gson().fromJson(sharedReturned,type);
            return some_list;
        }
        return new ArrayList<>();
    }
    public boolean getIsSave(){
        SharedPreferences sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sharedPreferences.getBoolean("Autosave",true);
    }
    public String getWrongMultipler(){
        SharedPreferences sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sharedPreferences.getString("Wrong","3");
    }
    public String getStartingCount(){
        SharedPreferences sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        return sharedPreferences.getString("Multiply","5");
    }
}
