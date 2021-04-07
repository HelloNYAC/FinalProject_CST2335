package com.finalproject_cst2335.trivia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TriviaQuestion {
    private String question, corrAns, questType, userSelectAns, questLevel;
    protected long id;
    private List<String> incorrArray, answerSetArray;

    /**
     * no-arg constuctor
     */
    public TriviaQuestion(){

    }

    /**
     * geter of id
     * @return
     */
    public long getId(){
        return id;
    }

    /**
     * setter pf id
     * @param id
     */
    public void setID(int id){
        this.id = id;
    }

    /**
     * getter of question
     * @return
     */
    public String getQuestion() {
        return question;
    }

    /**
     * getter of correct answer
     * @return
     */
    public String getCorrAns(){
        return corrAns;
    }

    /**
     * getter of user selected answer
     * @return
     */
    public String getUserSelectAns(){
        return userSelectAns;
    }

    /**
     * setter of user selected answer
     * @param userSelectAns
     */
    public void setUserSelectAns(String userSelectAns){
        this.userSelectAns = userSelectAns;
    }

    /**
     * getter of incorrect answer array
     * @return
     */
    public List<String> getIncorrArray(){
        return incorrArray;
    }

    /**
     * getter of question type
     * @return
     */
    public String getQuestType() {
        return questType;
    }

    /**
     * setter of correct answer
     * @param corrAns
     */
    public void setCorrAns(String corrAns) {
        this.corrAns = corrAns;
    }

    /**
     * setter of question
     * @param question
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * setter of question type
     * @param questType
     */
    public void setQuestType(String questType) {
        this.questType = questType;
    }

    /**
     * getter of question level
     * @return
     */
    public String getQuestLevel() {
        return questLevel;
    }

    /**
     * setter of question level
     * @param questLevel
     */
    public void setQuestLevel(String questLevel) {
        this.questLevel = questLevel;
    }

    /**
     * setter od id
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * setter of incorrect answer array
     * @param incorrArray
     */
    public void setIncorrArray(List<String> incorrArray) {
        this.incorrArray = incorrArray;
    }

    /**
     * getter of answer array
     * @return
     */
    public List<String> getAnswerSetArray() {
        return answerSetArray;
    }

    /**
     * setter of the anaswer array with all answers
     * @param answerSetArray
     */
    public void setAnswerSetArray(List<String> answerSetArray) {
        this.answerSetArray = answerSetArray;
    }
}
