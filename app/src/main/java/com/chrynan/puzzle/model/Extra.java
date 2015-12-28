package com.chrynan.puzzle.model;

import com.chrynan.puzzle.interfaces.Piece;
import com.orm.SugarRecord;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by chRyNaN on 12/28/2015.
 */
public class Extra extends SugarRecord implements Piece, Serializable {
    private Long id;
    private int type;
    private String name;
    private String fileLocation;
    private Date date;

    public Extra(){
        this.date = new Date();
    }

    public Extra(JSONObject obj){
        setPropertiesFromJSON(obj);
    }

    public static Extra fromJSON(JSONObject obj){
        return new Extra(obj);
    }

    public void setPropertiesFromJSON(JSONObject obj){
        try{
            if(obj != null){
                if(obj.has("id")){
                    this.id = obj.getLong("id");
                }
                if(obj.has("type")){
                    this.type = obj.getInt("type");
                }
                if(obj.has("name")){
                    this.name = obj.getString("name");
                }
                if(obj.has("fileLocation")){
                    this.fileLocation = obj.getString("fileLocation");
                }
                if(obj.has("date")){
                    this.date = new Date(obj.getLong("date"));
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
            obj.put("type", type);
            obj.put("name", name);
            obj.put("fileLocation", fileLocation);
            if(date != null) {
                obj.put("date", date.getTime());
            }
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
        }else{
            return this.toString();
        }
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public int getType(){
        return type;
    }

    public void setType(int type){
        this.type = type;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getFileLocation(){
        return fileLocation;
    }

    public void setFileLocation(String fileLocation){
        this.fileLocation = fileLocation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Extra{" +
                "id=" + id +
                ", type=" + type +
                ", name='" + name + '\'' +
                ", fileLocation='" + fileLocation + '\'' +
                ", date=" + date +
                '}';
    }

}
