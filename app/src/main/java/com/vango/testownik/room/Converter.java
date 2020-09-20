package com.vango.testownik.room;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vango.testownik.model.Answer;
import com.vango.testownik.model.QuizModel;

import java.lang.reflect.Type;
import java.util.List;

import androidx.room.TypeConverter;

public class Converter {
    @TypeConverter
    public static String convertAnswer(Answer answer){
        return new Gson().toJson(answer);
    }

    @TypeConverter
    public static Answer convertJsonToAnswer(String json){
        return new Gson().fromJson(json,Answer.class);
    }
}
