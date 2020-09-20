package com.vango.testownik.repository;

import android.util.Log;

import com.vango.testownik.model.QuizModel;
import com.vango.testownik.model.room.Miernictwo;
import com.vango.testownik.room.Dao.MiernictwoDao;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    Retrofit retrofit;
    MiernictwoDao miernictwoDao;
    private MutableLiveData<List<QuizModel>> questions = new MutableLiveData<>();
    @Inject
    public Repository(Retrofit retrofit, MiernictwoDao miernictwoDao) {
        this.retrofit = retrofit;
        this.miernictwoDao=miernictwoDao;
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
}
