package com.finalproject_cst2335.car;

import java.util.ArrayList;

public class CarsModel {
    private float Count;
    private String Message;
    private String SearchCriteria;
   public ArrayList <cars> Results = new ArrayList<cars>();


    // Getter Methods

    public float getCount() {
        return Count;
    }

    public String getMessage() {
        return Message;
    }

    public String getSearchCriteria() {
        return SearchCriteria;
    }

    // Setter Methods

    public void setCount(float Count) {
        this.Count = Count;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public void setSearchCriteria(String SearchCriteria) {
        this.SearchCriteria = SearchCriteria;
    }
}