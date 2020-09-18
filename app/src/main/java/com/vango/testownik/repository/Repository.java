package com.vango.testownik.repository;

import android.util.Log;

import com.google.gson.Gson;
import com.vango.testownik.model.Answer;
import com.vango.testownik.model.QuizModel;
import com.vango.testownik.model.RetrofitModel;
import com.vango.testownik.ui.QuizViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    Retrofit retrofit;

    private MutableLiveData<List<QuizModel>> questions = new MutableLiveData<>();
    @Inject
    public Repository(Retrofit retrofit) {
        this.retrofit = retrofit;
    }
    public LiveData<List<QuizModel>> getData(String table){
        Call<List<QuizModel>> call= retrofit.getRetrofitInterface().getQuestions(table);
        call.enqueue(new Callback<List<QuizModel>>() {
            @Override
            public void onResponse(Call<List<QuizModel>> call, Response<List<QuizModel>> response) {
                Log.i("answer",response.body().get(0).getAnswerA().getAnswer());
                questions.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<QuizModel>> call, Throwable t) {
            Log.i("t",t.getMessage());
            }
        });
        return questions;
    }
}
