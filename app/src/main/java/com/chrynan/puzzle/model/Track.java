package com.chrynan.puzzle.model;

import com.chrynan.puzzle.interfaces.Piece;
import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created chRyNaN on 12/12/2015.
 */
public class Track extends SugarRecord implements Piece, Serializable {
    private Long id;
    private String name;
    private String description;
    private Date createdDate;
    private Date lastUpdatedDate;
    private String folderLocation;
    private List<Clip> clips;
    private List<Artist> artists;
    private List<Note> notes;
    private int position;
    private long length;
    private boolean muted;
    private long sampleRate;
    private int channels;
    private int sampleFormat;
    private int channelIndex;
    private int interleaveIndex;
    private String fileFormat;

    public Track(){
        this.createdDate = new Date();
        this.lastUpdatedDate = new Date();
        this.clips = new ArrayList<>();
        this.artists = new ArrayList<>();
        this.notes = new ArrayList<>();
        this.muted = false;
        this.position = 0;
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
            if(lastUpdatedDate != null){
                obj.put("lastUpdatedDate", lastUpdatedDate.getTime());
            }
            obj.put("folderLocation", folderLocation);
            obj.put("position", position);
            obj.put("length", length);
            obj.put("muted", muted);
            obj.put("sampleRate", sampleRate);
            obj.put("sampleFormat", sampleFormat);
            obj.put("channels", channels);
            obj.put("channelIndex", channelIndex);
            obj.put("interleaveIndex", interleaveIndex);
            obj.put("fileFormat", fileFormat);
            if (artists != null && artists.size() > 0) {
                JSONArray aArray = new JSONArray();
                for(Artist a : artists){
                    aArray.put(a.toJSON());
                }
                obj.put("artists", aArray);
            }
            if (notes != null && notes.size() > 0) {
                JSONArray nArray = new JSONArray();
                for(Note n : notes){
                    nArray.put(n.toJSON());
                }
                obj.put("notes", nArray);
            }
            if(clips != null && clips.size() > 0){
                JSONArray cArray = new JSONArray();
                for(Clip c : clips){
                    cArray.put(c.toJSON());
                }
                obj.put("clips", cArray);
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
        return "Track{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", lastUpdatedDate=" + lastUpdatedDate +
                ", folderLocation='" + folderLocation + '\'' +
                ", clips=" + clips +
                ", artists=" + artists +
                ", notes=" + notes +
                ", position=" + position +
                ", length=" + length +
                ", muted=" + muted +
                ", sampleRate=" + sampleRate +
                ", channels=" + channels +
                ", sampleFormat=" + sampleFormat +
                ", channelIndex=" + channelIndex +
                ", interleaveIndex=" + interleaveIndex +
                ", fileFormat='" + fileFormat + '\'' +
                '}';
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

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getFolderLocation() {
        return folderLocation;
    }

    public void setFolderLocation(String folderLocation) {
        this.folderLocation = folderLocation;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public void addArtist(Artist artist){
        if(artists == null){
            artists = new ArrayList<>();
        }
        artists.add(artist);
    }

    public boolean removeArtist(Artist artist){
        if(artists != null){
            return artists.remove(artist);
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public long getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(long sampleRate) {
        this.sampleRate = sampleRate;
    }

    public int getChannels() {
        return channels;
    }

    public void setChannels(int channels) {
        this.channels = channels;
    }

    public int getSampleFormat() {
        return sampleFormat;
    }

    public void setSampleFormat(int sampleFormat) {
        this.sampleFormat = sampleFormat;
    }

    public int getChannelIndex() {
        return channelIndex;
    }

    public void setChannelIndex(int channelIndex) {
        this.channelIndex = channelIndex;
    }

    public int getInterleaveIndex() {
        return interleaveIndex;
    }

    public void setInterleaveIndex(int interleaveIndex) {
        this.interleaveIndex = interleaveIndex;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public List<Clip> getClips() {
        return clips;
    }

    public void setClips(List<Clip> clips) {
        this.clips = clips;
    }

    public void addClip(Clip clip){
        if(clips == null){
            clips = new ArrayList<>();
        }
        clips.add(clip);
    }

    public boolean removeClip(Clip clip){
        if(clips != null){
            return clips.remove(clip);
        }
        return false;
    }

}
