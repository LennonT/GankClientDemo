package com.example.lennont.gankclientdemo.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LennonT on 2016/7/8.
 */
public class GoodsResult {

    private ArrayList<Goods> results;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    private String error;

    public GoodsResult(ArrayList<Goods> results) {
        this.results = results;
    }

    public ArrayList<Goods> getResults() {
        return results;
    }

    public void setResults(ArrayList<Goods> results) {
        this.results = results;
    }

}
