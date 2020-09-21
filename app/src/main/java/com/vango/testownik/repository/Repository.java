package com.vango.testownik.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vango.testownik.model.QuizModel;
import com.vango.testownik.model.room.Miernictwo;
import com.vango.testownik.room.Dao.MiernictwoDao;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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
    private MutableLiveData<List<QuizModel>> questions = new MutableLiveData<>();
    @Inject
    public Repository(Retrofit retrofit, MiernictwoDao miernictwoDao, @ApplicationContext Context context) {
        this.retrofit = retrofit;
        this.miernictwoDao=miernictwoDao;
        this.context=context;
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
            Log.i("t",t.getMessage());
            }
        });
        return questions;
    }
    public void insert(Miernictwo miernictwo){
        Completable.fromAction(() -> miernictwoDao.insert(miernictwo)).subscribeOn(Schedulers.io()).subscribe();
        //miernictwoDao.insert(miernictwo);
    }
    public LiveData<List<Miernictwo>> getAll(){
       return miernictwoDao.getAllQuestions();
    }
    public void update(Miernictwo miernictwo){
        Completable.fromAction(() -> miernictwoDao.update(miernictwo)).subscribeOn(Schedulers.io()).subscribe();
    }

    public LiveData<Integer> getRowCount(){
        return miernictwoDao.getRowCount();
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
        Log.i("shared",sharedReturned);
        Type type =new TypeToken<List<Integer>>(){}.getType();
        if(!sharedReturned.equals("")){
            List<Integer>some_list = new Gson().fromJson(sharedReturned,type);
            Log.i("shared",some_list.size()+" reposi");
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
