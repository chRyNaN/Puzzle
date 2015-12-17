package com.chrynan.puzzle.model;

import android.graphics.Bitmap;

import com.chrynan.puzzle.Storage;
import com.chrynan.puzzle.interfaces.Piece;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chRyNaN on 12/12/2015.
 */
public class Project extends SugarRecord implements Piece, Serializable {
    private Long id;
    private String name;
    private String description;
    private Date createdDate;
    private String folderLocation;
    private long length;
    private List<Artist> contributers;
    private List<Note> notes;
    @Ignore
    private List<Bitmap> pictures;
    private List<Track> tracks;
    private long lat;
    private long lon;
    private Track masterTrack;

    public Project(){
        this.contributers = new ArrayList<>();
        this.notes = new ArrayList<>();
        this.pictures = new ArrayList<>();
        this.tracks = new ArrayList<>();
        this.createdDate = new Date();
    }

    public Long getId() {
        return id;
    }

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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getFolderLocation() {
        return folderLocation;
    }

    public void setFolderLocation(String folderLocation) {
        this.folderLocation = folderLocation;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public List<Artist> getContributers() {
        return contributers;
    }

    public void setContributers(List<Artist> contributers) {
        this.contributers = contributers;
    }

    public void addContributer(Artist artist){
        if(contributers == null){
            contributers = new ArrayList<>();
        }
        contributers.add(artist);
    }

    public boolean removeContributer(Artist artist){
        if(contributers != null){
            return contributers.remove(artist);
        }
        return false;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public void addNote(Note note){
        if(notes == null){
            notes = new ArrayList<>();
        }
        notes.add(note);
    }

    public boolean removeNote(Note note){
        if(notes != null){
            return notes.remove(note);
        }
        return false;
    }

    public List<Bitmap> getPictures() {
        if(pictures == null || pictures.size() < 1){
            pictures = new ArrayList<>();
            pictures.addAll(Storage.getBitmaps(this));
        }
        return pictures;
    }

    public void setPictures(List<Bitmap> pictures) {
        this.pictures = pictures;
    }

    public void addPicture(Bitmap bitmap){
        if(pictures == null){
            pictures = new ArrayList<>();
        }
        pictures.add(bitmap);
    }

    public boolean removePicture(Bitmap bitmap){
        if(pictures != null){
            return pictures.remove(bitmap);
        }
        return false;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public void addTrack(Track track){
        if(tracks == null){
            tracks = new ArrayList<>();
        }
        tracks.add(track);
    }

    public boolean removeTrack(Track track){
        if(tracks != null){
            return tracks.remove(track);
        }
        return false;
    }

    public long getLat() {
        return lat;
    }

    public void setLat(long lat) {
        this.lat = lat;
    }

    public long getLon() {
        return lon;
    }

    public void setLon(long lon) {
        this.lon = lon;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", folderLocation='" + folderLocation + '\'' +
                ", length=" + length +
                ", contributers=" + contributers +
                ", notes=" + notes +
                ", pictures=" + pictures +
                ", tracks=" + tracks +
                ", lat=" + lat +
                ", lon=" + lon +
                '}';
    }

    public JSONObject toJSON(){
        try{
            JSONObject obj = new JSONObject();
            obj.put("id", id);
            obj.put("name", name);
            obj.put("description", description);
            if(createdDate != null) {
                obj.put("createdDate", createdDate.getTime());
            }
            obj.put("folderLocation", folderLocation);
            obj.put("length", length);
            if(contributers != null){
                JSONArray cArray = new JSONArray();
                for(Artist a : contributers){
                    cArray.put(a.toJSON());
                }
                obj.put("contributers", cArray);
            }
            if(notes != null){
                JSONArray nArray = new JSONArray();
                for(Note n : notes){
                    nArray.put(n.toJSON());
                }
                obj.put("notes", nArray);
            }
            if(tracks != null){
                JSONArray tArray = new JSONArray();
                for(Track t : tracks){
                    tArray.put(t.toJSON());
                }
                obj.put("tracks", tArray);
            }
            obj.put("lat", lat);
            obj.put("lon", lon);
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

    public Track getMasterTrack() {
        return masterTrack;
    }

    public void setMasterTrack(Track masterTrack) {
        this.masterTrack = masterTrack;
    }

}
