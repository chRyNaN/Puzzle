package com.chrynan.puzzle;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.chrynan.puzzle.model.Artist;
import com.chrynan.puzzle.model.Clip;
import com.chrynan.puzzle.model.DAWContract;
import com.chrynan.puzzle.model.Note;
import com.chrynan.puzzle.model.Project;
import com.chrynan.puzzle.model.Track;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

/**
 * Created by chRyNaN on 12/17/2015.
 */
public class PuzzleProvider extends ContentProvider {
    /*
     * For use when matching Uri to the appropriate place. For instance, getType method returns the type, this will simplify the code
     * to get the appropriate type by handling the boilerplate logic of determining what Uri was sent in. This method of doing it
     * is still pretty verbose.
     */
    private static final int PROJECT_LIST = 0;
    private static final int PROJECT_ID = 1;
    private static final int TRACK_LIST = 2;
    private static final int TRACK_ID = 3;
    private static final int CLIP_LIST = 4;
    private static final int CLIP_ID = 5;
    private static final int ARTIST_LIST = 6;
    private static final int ARTIST_ID = 7;
    private static final int NOTE_LIST = 8;
    private static final int NOTE_ID = 9;
    //Handles the Uri matching logic. Instantiated in the following static instantiation block.
    private static final UriMatcher URI_MATCHER;
    //Sets up the UriMatcher. addURI parameters: authority, path, id
    static{
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(DAWContract.AUTHORITY, "project", 0);
        URI_MATCHER.addURI(DAWContract.AUTHORITY, "project/#", 1);
        URI_MATCHER.addURI(DAWContract.AUTHORITY, "track", 2);
        URI_MATCHER.addURI(DAWContract.AUTHORITY, "track/#", 3);
        URI_MATCHER.addURI(DAWContract.AUTHORITY, "clip", 4);
        URI_MATCHER.addURI(DAWContract.AUTHORITY, "clip/#", 5);
        URI_MATCHER.addURI(DAWContract.AUTHORITY, "artist", 6);
        URI_MATCHER.addURI(DAWContract.AUTHORITY, "artist/#", 7);
        URI_MATCHER.addURI(DAWContract.AUTHORITY, "note", 8);
        URI_MATCHER.addURI(DAWContract.AUTHORITY, "note/#", 9);
    }


    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if(uri != null){
            Select<?> s;
            switch(URI_MATCHER.match(uri)) {
                case PROJECT_LIST:
                case PROJECT_ID:
                    s = Select.from(Project.class);

                    break;
                case TRACK_LIST:
                case TRACK_ID:
                    s = Select.from(Track.class);
                    break;
                case CLIP_LIST:
                case CLIP_ID:
                    s = Select.from(Clip.class);
                    break;
                case ARTIST_LIST:
                case ARTIST_ID:
                    s = Select.from(Artist.class);
                    break;
                case NOTE_LIST:
                case NOTE_ID:
                    s = Select.from(Note.class);
                    break;
                default:
                    throw new IllegalArgumentException("Uri provided in the query method didn't match any known format.");
            }
            Long id = getId(uri);
            if(selection != null && selectionArgs != null){
                s.where(selection, selectionArgs);
                if(id != -1){
                    s.and(Condition.prop(BaseColumns._ID).eq(id));
                }
            }else if(id != -1){
                s.where(Condition.prop(BaseColumns._ID).eq(id));
            }
            if(sortOrder != null) {
                s.orderBy(sortOrder);
            }
            return getCursor(s, projection);
        }else{
            throw new IllegalArgumentException("Uri provided in the query method must not be null.");
        }
    }

    @Override
    public String getType(Uri uri) {
        switch(URI_MATCHER.match(uri)){
            case PROJECT_LIST:
                return DAWContract.Project.CONTENT_TYPE;
            case PROJECT_ID:
                return DAWContract.Project.CONTENT_ITEM_TYPE;
            case TRACK_LIST:
                return DAWContract.Track.CONTENT_TYPE;
            case TRACK_ID:
                return DAWContract.Track.CONTENT_ITEM_TYPE;
            case CLIP_LIST:
                return DAWContract.Clip.CONTENT_TYPE;
            case CLIP_ID:
                return DAWContract.Clip.CONTENT_ITEM_TYPE;
            case ARTIST_LIST:
                return DAWContract.Artist.CONTENT_TYPE;
            case ARTIST_ID:
                return DAWContract.Artist.CONTENT_ITEM_TYPE;
            case NOTE_LIST:
                return DAWContract.Note.CONTENT_TYPE;
            case NOTE_ID:
                return DAWContract.Note.CONTENT_ITEM_TYPE;
            default:
                return null;
        }
    }

    @Override
    public String[] getStreamTypes(Uri uri, String mimeTypeFilter){
        //TODO implement getStreamTypes method
        return null;
    }

    //Currently not supported use Puzzle class instead
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    //Currently not supported use Puzzle class instead
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    //Currently not supported use Puzzle class instead
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    public Class<?> getClass(Uri uri){
        switch(URI_MATCHER.match(uri)){
            case PROJECT_LIST:
            case PROJECT_ID:
                return Project.class;
            case TRACK_LIST:
            case TRACK_ID:
                return Track.class;
            case CLIP_LIST:
            case CLIP_ID:
                return Clip.class;
            case ARTIST_LIST:
            case ARTIST_ID:
                return Artist.class;
            case NOTE_LIST:
            case NOTE_ID:
                return Note.class;
            default:
                throw new IllegalArgumentException("Uri provided in the query method didn't match any known format.");
        }
    }

    public long getId(Uri uri){
        switch(URI_MATCHER.match(uri)){
            case PROJECT_ID:
            case TRACK_ID:
            case CLIP_ID:
            case ARTIST_ID:
            case NOTE_ID:
                return ContentUris.parseId(uri);
            default:
                return -1;
        }
    }

    public Cursor getCursor(Select s, String[] projection){
        //Would be convenient if I could just get the cursor from the Select object. Despite, it being available
        //in their source on GitHub, I can't access the getCursor method to do so. It's available in their source but
        //not in the library, perhaps they never updated it. So, I must get the list of objects from the Select object
        //and traverse that to return only the appropriate fields specified in the projection. This may require using
        //reflection, for instance the Field object. But that method is slow and error prone. What I decided to do was
        //clone the Sugar ORM repository, add the appropriate method, build the project, and include the project as a library
        //for this project. Let's hope that works. Can't add projection here though.
        if(s != null){
            return s.getCursor();
        }
        return null;
    }

}
