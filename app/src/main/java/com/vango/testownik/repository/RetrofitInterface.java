package com.vango.testownik.repository;

import com.vango.testownik.model.QuizModel;
import com.vango.testownik.model.RetrofitModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("/question_array.php")
    Call<List<QuizModel>> getQuestions(@Query("table") String table);
}
