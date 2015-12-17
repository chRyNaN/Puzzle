package com.chrynan.puzzle.model;

import com.chrynan.puzzle.interfaces.Piece;
import com.orm.SugarRecord;

import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by chRyNaN on 12/12/2015.
 */
public class Clip extends SugarRecord implements Piece, Serializable {
    private Long id;
    private String name;
    private String description;
    private String fileLocation;
    private long startPosition;
    private long length;
    private Date createdDate;

    public Clip(){
        this.createdDate = new Date();
    }

    public Clip(File file){
        this();
        this.fileLocation = file.getAbsolutePath();
    }

    public Clip(JSONObject obj){
        this();
        setPropertiesFromJSON(obj);
    }

    public void setPropertiesFromJSON(JSONObject obj){
        try{
            if(obj != null){
                if(obj.has("id")){
                    this.id = obj.getLong("id");
                }
                if(obj.has("name")){
                    this.name = obj.getString("name");
                }
                if(obj.has("description")){
                    this.description = obj.getString("description");
                }
                if(obj.has("fileLocation")){
                    this.fileLocation = obj.getString("fileLocation");
                }
                if(obj.has("startPosition")){
                    this.startPosition = obj.getLong("startPosition");
                }
                if(obj.has("length")){
                    this.length = obj.getLong("length");
                }
                if(obj.has("createdDate")){
                    this.createdDate = new Date(obj.getLong("createdDate"));
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
            obj.put("name", name);
            obj.put("description", description);
            obj.put("fileLocation", fileLocation);
            obj.put("startPosition", startPosition);
            obj.put("length", length);
            if(createdDate != null){
                obj.put("createdDate", createdDate.getTime());
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
        }
        return this.toString();
    }

    @Override
    public String toString() {
        return "Clip{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", fileLocation='" + fileLocation + '\'' +
                ", startPosition=" + startPosition +
                ", length=" + length +
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public long getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(long startPosition) {
        this.startPosition = startPosition;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}
