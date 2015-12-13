package com.chrynan.puzzle.model;

/**
 * Created by chRyNaN on 12/12/2015.
 */
public class Error {
    public static final int PRIORITY_SEVERE = 0;
    public static final int PRIORITY_MEDIUM = 1;
    public static final int PRIORITY_LOW = 2;
    private int type;
    private String details;
    private int priority;

    public Error(int type, String details){
        this.type = type;
        this.details = details;
        this.priority = PRIORITY_LOW;
    }

    public Error(int type, int priority, String details){
        this.type = type;
        this.priority = priority;
        this.details = details;
    }

}
