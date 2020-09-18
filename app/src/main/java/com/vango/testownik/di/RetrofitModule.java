package com.vango.testownik.di;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.vango.testownik.model.QuizModel;
import com.vango.testownik.model.RetrofitModel;
import com.vango.testownik.repository.RetrofitInterface;
import com.vango.testownik.util.QuizConverter;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module
@InstallIn(ApplicationComponent.class)
public class RetrofitModule {

    @Singleton
    @Provides
    public RetrofitInterface provideRetrofit(){
        Type type =new TypeToken<List<QuizModel>>(){}.getType();
        OkHttpClient client  = new OkHttpClient.Builder().build();
        Gson gson =new GsonBuilder().setLenient()
                .registerTypeAdapter(type, new QuizConverter()).create();
       Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("///")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
       return retrofit.create(RetrofitInterface.class);
    }
}
