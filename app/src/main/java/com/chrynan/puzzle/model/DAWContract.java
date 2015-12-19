package com.chrynan.puzzle.model;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by chRyNaN on 12/17/2015.
 */
public final class DAWContract {
    /**
     * The authority of the puzzle provider.
     */
    public static final String AUTHORITY = "com.chrynan.puzzle.puzzleprovider";
    /**
     * The content URI for the top-level authority.
     */
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    /*
     * The type of audio files that can be stored
     */
    public static final String[] AUDIO_MIME_TYPES = {"audio/mp3", "audio/flac", "audio/aac", "audio/pcm", "audio/wav",
            "audio/ogg", "audio/mkv", "audio/mid"};

    /*
     * A class representing all the contract information for the Project class
     */
    public static final class Project implements CommonColumns{
        public static final Uri CONTENT_URI = Uri.parse("content://" + DAWContract.AUTHORITY + "/project");
        /**
         * The mime type of a directory of items.
         */
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.chrynan.puzzle.projects";
        /**
         * The mime type of a single item.
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.com.chrynan.puzzle.project";
        public static final String FOLDER_LOCATION = "folder_location";
        public static final String LENGTH = "length";
        public static final String CONTRIBUTERS = "contributers";
        public static final String NOTES = "notes";
        public static final String TRACKS = "tracks";
        public static final String LATITUDE = "lat";
        public static final String LONGITUDE = "lon";
        public static final String MASTER_TRACK = "master_track";
        /**
         * A projection of all columns
         * in the items table.
         */
        public static final String[] PROJECTION_ALL = {_ID, NAME, DESCRIPTION, CREATED_DATE, FOLDER_LOCATION,
                        LENGTH, CONTRIBUTERS, NOTES, TRACKS, LATITUDE, LONGITUDE, MASTER_TRACK};
        /**
         * The default sort order for
         * queries containing NAME fields.
         */
        public static final String SORT_ORDER_DEFAULT = NAME + " ASC";
    }

    /*
     * A class representing all the contract information for the Track class
     */
    public static final class Track implements CommonColumns{
        public static final Uri CONTENT_URI = Uri.parse("content://" + DAWContract.AUTHORITY + "/track");
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.chrynan.puzzle.tracks";
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.com.chrynan.puzzle.track";
        public static final String LAST_UPDATED_DATE = "last_updated_date";
        public static final String FOLDER_LOCATION = "folder_location";
        public static final String CLIPS = "clips";
        public static final String ARTISTS = "artists";
        public static final String NOTES = "notes";
        public static final String POSITION = "position";
        public static final String LENGTH = "length";
        public static final String MUTED = "muted";
        public static final String SAMPLE_RATE = "sample_rate";
        public static final String CHANNELS = "channels";
        public static final String SAMPLE_FORMAT = "sample_format";
        public static final String CHANNEL_INDEX = "channel_index";
        public static final String INTERLEAVE_INDEX = "interleave_index";
        public static final String FILE_FORMAT = "file_format";
        public static final String[] PROJECTION_ALL = {_ID, NAME, DESCRIPTION, CREATED_DATE, LAST_UPDATED_DATE, FOLDER_LOCATION,
                CLIPS, ARTISTS, NOTES, POSITION, LENGTH, MUTED, SAMPLE_RATE, CHANNELS, SAMPLE_FORMAT, CHANNEL_INDEX,
                INTERLEAVE_INDEX, FILE_FORMAT};
        public static final String SORT_ORDER_DEFAULT = NAME + " ASC";
    }

    /*
     * A class representing all the contract information for the Clip class
     */
    public static final class Clip implements CommonColumns{
        public static final Uri CONTENT_URI = Uri.parse("content://" + DAWContract.AUTHORITY + "/clip");
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.chrynan.puzzle.clips";
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.com.chrynan.puzzle.clip";
        public static final String FILE_LOCATION = "file_location";
        public static final String START_POSITION = "start_position";
        public static final String LENGTH = "length";
        public static final String[] PROJECTION_ALL = {_ID, NAME, DESCRIPTION, CREATED_DATE, FILE_LOCATION, START_POSITION, LENGTH};
        public static final String SORT_ORDER_DEFAULT = NAME + " ASC";
    }

    /*
     * A class representing all the contract information for the Artist class
     */
    public static final class Artist implements BaseColumns{
        public static final Uri CONTENT_URI = Uri.parse("content://" + DAWContract.AUTHORITY + "/artist");
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.chrynan.puzzle.artists";
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.com.chrynan.puzzle.artist";
        public static final String USER_ID = "user_id";
        public static final String NAME = "name";
        public static final String[] PROJECTION_ALL = {_ID, NAME, USER_ID};
        public static final String SORT_ORDER_DEFAULT = NAME + " ASC";
    }

    /*
     * A class representing all the contract information for the Note class
     */
    public static final class Note implements BaseColumns{
        public static final Uri CONTENT_URI = Uri.parse("content://" + DAWContract.AUTHORITY + "/note");
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd.com.chrynan.puzzle.notes";
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd.com.chrynan.puzzle.note";
        public static final String AUTHOR = "author";
        public static final String BODY = "body";
        public static final String START_POSITION = "start_position";
        public static final String END_POSITION = "end_position";
        public static final String PRIORITY = "priority";
        public static final String[] PROJECTION_ALL = {_ID, AUTHOR, BODY, START_POSITION, END_POSITION, PRIORITY};
        public static final String SORT_ORDER_DEFAULT = _ID + " ASC";
    }

    /*
     * Interface that provides commonly used columns for the contract classes
     */
    public interface CommonColumns extends BaseColumns{
        String NAME = "name";
        String DESCRIPTION = "description";
        String CREATED_DATE = "created_date";
    }

}
