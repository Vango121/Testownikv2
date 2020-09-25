package com.vango.testownik.util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.vango.testownik.model.Answer;
import com.vango.testownik.model.QuizModel;
import com.vango.testownik.model.RetrofitModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class QuizConverter implements JsonDeserializer<List<QuizModel>> {


    @Override
    public List<QuizModel> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Type type =new TypeToken<List<RetrofitModel>>(){}.getType();
        List<RetrofitModel> questionlist = new Gson().fromJson(json,type);
        List<QuizModel> list = new ArrayList<>();
        for (int i = 0; i <questionlist.size(); i++) {
            list.add(new QuizModel(questionlist.get(i).getId(),questionlist.get(i).getQuestion(),
                    new Gson().fromJson(questionlist.get(i).getAnswerA(), Answer.class),
                    new Gson().fromJson(questionlist.get(i).getAnswerB(), Answer.class),
                    new Gson().fromJson(questionlist.get(i).getAnswerC(), Answer.class),
                    new Gson().fromJson(questionlist.get(i).getAnswerD(), Answer.class)));
        }

        return list;
    }
}
