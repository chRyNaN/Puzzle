package com.chrynan.puzzle.model;

import com.chrynan.puzzle.interfaces.Piece;
import com.orm.SugarRecord;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by chRyNaN on 12/12/2015.
 */
public class Artist extends SugarRecord implements Piece, Serializable {
    private Long id;
    private String userId;
    private String name;

    public Artist(){

    }

    public Artist(JSONObject obj){
        this();
        setPropertiesFromJSON(obj);
    }

    public void setPropertiesFromJSON(JSONObject obj){
        try{
            if(obj != null){
                if(obj.has("id")){
                    this.id = obj.getLong("id");
                }
                if(obj.has("userId")){
                    this.userId = obj.getString("userId");
                }
                if(obj.has("name")){
                    this.name = obj.getString("name");
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
            obj.put("userId", userId);
            obj.put("name", name);
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
        return "Artist{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
