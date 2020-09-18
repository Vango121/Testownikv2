package com.vango.testownik.repository;

import javax.inject.Inject;

public class Retrofit {
    private RetrofitInterface retrofitInterface;

    @Inject
    public Retrofit(RetrofitInterface retrofitInterface) {
        this.retrofitInterface = retrofitInterface;
    }

    public RetrofitInterface getRetrofitInterface() {
        return retrofitInterface;
    }
}
