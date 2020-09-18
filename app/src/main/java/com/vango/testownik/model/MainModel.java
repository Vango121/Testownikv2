package com.vango.testownik.model;

public class MainModel {
    Integer logo;
    String name;



    public MainModel(Integer logo, String name){
        this.logo=logo;
        this.name=name;
    }
    public Integer getLogo() {
        return logo;
    }

    public String getName() {
        return name;
    }
}
