package com.chrynan.puzzle.model;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by chRyNaN on 12/17/2015.
 */
public final class DAWContract {
    /**
     * The authority of the puzzle provider.
     */
    public static final String AUTHORITY = "com.chrynan.puzzle";
    /**
     * The content URI for the top-level authority.
     */
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    /*
     * A class representing all the contract information for the Project class
     */
    public static final class Project implements CommonColumns{
        public static final Uri CONTENT_URI = Uri.parse("content://" + DAWContract.AUTHORITY + "/project");
        public static final String FOLDER_LOCATION = "folder_location";
        public static final String LENGTH = "length";
        public static final String CONTRIBUTERS = "contributers";
        public static final String NOTES = "notes";
        public static final String TRACKS = "tracks";
        public static final String LATITUDE = "lat";
        public static final String LONGITUDE = "lon";
        public static final String MASTER_TRACK = "master_track";
    }

    /*
     * A class representing all the contract information for the Track class
     */
    public static final class Track implements CommonColumns{
        public static final Uri CONTENT_URI = Uri.parse("content://" + DAWContract.AUTHORITY + "/track");
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
    }

    /*
     * A class representing all the contract information for the Clip class
     */
    public static final class Clip implements CommonColumns{
        public static final Uri CONTENT_URI = Uri.parse("content://" + DAWContract.AUTHORITY + "/clip");
        public static final String FILE_LOCATION = "file_location";
        public static final String START_POSITION = "start_position";
        public static final String LENGTH = "length";
    }

    /*
     * A class representing all the contract information for the Artist class
     */
    public static final class Artist implements BaseColumns{
        public static final String USER_ID = "user_id";
        public static final String NAME = "name";
    }

    /*
     * A class representing all the contract information for the Note class
     */
    public static final class Note implements BaseColumns{
        public static final String AUTHOER = "authoer";
        public static final String BODY = "body";
        public static final String START_POSITION = "start_position";
        public static final String END_POSITION = "end_position";
        public static final String PRIORITY = "priority";
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
