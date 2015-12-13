package com.chrynan.puzzle.model;

import com.chrynan.puzzle.interfaces.Piece;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chRyNaN on 12/12/2015.
 */
public class Result<T extends Piece> {
    private List<T> results;
    private int type;
    private boolean singleResult;

    public Result(){
        this.singleResult = true;
    }

    public Result(T result){
        this.results = new ArrayList<>();
        this.results.add(result);
        this.singleResult = true;
    }

    public Result(T result, int type){
        this(result);
        this.type = type;
    }

    public Result(List<T> results){
        this.results = results;
        this.singleResult = false;
    }

    public Result(List<T> results, int type){
        this(results);
        this.type = type;
    }

    public T getSingleResult(){
        if(results != null && results.size() > 0){
            return results.get(0);
        }
        return null;
    }

    public List<T> getResultList(){
        return results;
    }

    public int getType(){
        return type;
    }

    public boolean isSingleResult(){
        return singleResult;
    }

}
