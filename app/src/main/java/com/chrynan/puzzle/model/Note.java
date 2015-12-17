package com.chrynan.puzzle.model;

import com.chrynan.puzzle.interfaces.Piece;
import com.orm.SugarRecord;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by chRyNaN on 12/12/2015.
 */
public class Note extends SugarRecord implements Piece, Serializable {
    private Long id;
    private Artist author;
    private String body;
    private long startPosition;
    private long endPosition;
    private int priority;

    public Note(){

    }

    public Note(JSONObject obj){
        this();
        setPropertiesFromJSON(obj);
    }

    public void setPropertiesFromJSON(JSONObject obj){
        try{
            if(obj != null){
                if(obj.has("id")){
                    this.id = obj.getLong("id");
                }
                if(obj.has("author")){
                    this.author = new Artist(obj.getJSONObject("author"));
                }
                if(obj.has("body")){
                    this.body = obj.getString("body");
                }
                if(obj.has("startPosition")){
                    this.startPosition = obj.getLong("startPosition");
                }
                if(obj.has("endPosition")){
                    this.endPosition = obj.getLong("endPosition");
                }
                if(obj.has("priority")){
                    this.priority = obj.getInt("priority");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public JSONObject toJSON(){
        try{
            JSONObject obj = new JSONObject();
            obj.put("id", id);
            if(author != null) {
                obj.put("author", author.toJSON());
            }
            obj.put("body", body);
            obj.put("startPosition", startPosition);
            obj.put("endPosition", endPosition);
            obj.put("priority", priority);
            return obj;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String toJSONString(){
        JSONObject obj = this.toJSON();
        if(obj != null){
            return obj.toString();
        }
        return this.toString();
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", author=" + author +
                ", body='" + body + '\'' +
                ", startPosition=" + startPosition +
                ", endPosition=" + endPosition +
                ", priority=" + priority +
                '}';
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Artist getAuthor() {
        return author;
    }

    public void setAuthor(Artist author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(long startPosition) {
        this.startPosition = startPosition;
    }

    public long getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(long endPosition) {
        this.endPosition = endPosition;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

}
