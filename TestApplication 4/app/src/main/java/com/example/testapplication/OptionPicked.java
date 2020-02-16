package com.example.testapplication;

import DataModels.Quiz;

public class OptionPicked {
    private Quiz quiz;
    private static OptionPicked optionPicked;
    private OptionPicked (){

    }

    public static OptionPicked getInstance(){
        if(optionPicked == null){
            optionPicked = new OptionPicked();
        }
        return optionPicked;
    }

    public void setQuiz(Quiz title){
        this.getInstance().quiz = title;
    }

    public Quiz getQuiz(){
        return this.getInstance().quiz;
    }


}
