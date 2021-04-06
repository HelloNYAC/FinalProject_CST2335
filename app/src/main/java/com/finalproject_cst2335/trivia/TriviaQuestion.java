package com.finalproject_cst2335.trivia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TriviaQuestion {
    private String question, corrAns, questType, userSelectAns, questLevel;
    protected long id;
    private List<String> incorrArray, answerSetArray;

    public TriviaQuestion(){

    }

//    public TriviaQuestion(int id, String question, String type, ArrayList answerSetArray){
//        this.id = id;
//        this.question = question;
//        this.questType = type;
//        this.answerSetArray = answerSetArray;
//    }

    public long getId(){
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

    public String getUserSelectAns(){
        return userSelectAns;
    }

    public void setUserSelectAns(String userSelectAns){
        this.userSelectAns = userSelectAns;
    }

    public List<String> getIncorrArray(){
        return incorrArray;
    }

    public String getQuestType() {
        return questType;
    }

    public void setCorrAns(String corrAns) {
        this.corrAns = corrAns;
    }


    public void setQuestion(String question) {
        this.question = question;
    }

    public void setQuestType(String questType) {
        this.questType = questType;
    }

    public String getQuestLevel() {
        return questLevel;
    }

    public void setQuestLevel(String questLevel) {
        this.questLevel = questLevel;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIncorrArray(List<String> incorrArray) {
        this.incorrArray = incorrArray;
    }

    public List<String> getAnswerSetArray() {
        return answerSetArray;
    }

    public void setAnswerSetArray(List<String> answerSetArray) {
        this.answerSetArray = answerSetArray;
    }
}
