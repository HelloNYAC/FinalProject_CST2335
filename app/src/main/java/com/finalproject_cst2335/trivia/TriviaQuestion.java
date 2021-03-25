package com.finalproject_cst2335.trivia;

import java.util.ArrayList;
import java.util.Collections;

public class TriviaQuestion {
    private String question, corrAns, type;
    protected int id;
    private ArrayList<String> incorrArray;

    public TriviaQuestion(int id, String question, String type, String corrAns, ArrayList incorrArray){
        this.id = id;
        this.question = question;
        this.type = type;
        this.corrAns = corrAns;
        this.incorrArray = incorrArray;
    }

    public int getId(){
        return id;
    }

    public void setID(int id){
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrAns(){
        return corrAns;
    }

    public ArrayList<String> getIncorrArray(){
        return incorrArray;
    }

    public String getType() {
        return type;
    }

    public ArrayList getAnswer(){
        incorrArray.add(corrAns);
        Collections.shuffle(incorrArray);
        return incorrArray;
    }

}
