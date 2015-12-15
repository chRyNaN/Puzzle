package com.chrynan.puzzle;

import android.os.Environment;

import com.chrynan.puzzle.model.Project;

import java.io.File;

/**
 * Created by chRyNaN on 12/15/2015.
 */
public class Storage {
    public static final String ROOT_FOLDER_NAME = "DAW_Projects";
    public static final String AUDIO_FOLDER_NAME = "Audio";
    public static final String PICTURE_FOLDER_NAME = "Pictures";
    public static final String EXTRA_FOLDER_NAME = "Extras";

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static File getProjectFilesDirectory(){
        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + ROOT_FOLDER_NAME);
        if(!folder.exists()){
            folder.mkdir();
        }
        return folder;
    }

    public static File createProjectFolder(Project p){
        File rootFolder = getProjectFilesDirectory();
        if(rootFolder != null && rootFolder.exists()){
            if(p != null && p.getName() != null) {
                File projectFolder = new File(rootFolder, p.getName());
                if(projectFolder.exists()){
                    projectFolder = new File(rootFolder, p.getName() + "_" + p.getId());
                    projectFolder.mkdir();
                }else{
                    projectFolder.mkdir();
                }
                //now create the audio folder
                File audioFolder = new File(projectFolder, AUDIO_FOLDER_NAME);
                audioFolder.mkdir();
                //now create picture folder
                File picFolder = new File(projectFolder, PICTURE_FOLDER_NAME);
                picFolder.mkdir();
                //now create the extras folder
                File extraFolder = new File(projectFolder, EXTRA_FOLDER_NAME);
                extraFolder.mkdir();
                return projectFolder;
            }
        }
        return null;
    }

    public static File getProjectFolder(Project p){
        File rootFolder = getProjectFilesDirectory();
        if(rootFolder != null && rootFolder.exists()){
            //first check if the file with the id concatenated to the name exists
            File projectFolder = new File(rootFolder, p.getName() + "_" + p.getId());
            if(!projectFolder.exists()){
                projectFolder = new File(rootFolder, p.getName());
            }
            if(projectFolder == null || !projectFolder.exists()){
                projectFolder = createProjectFolder(p);
            }
            return projectFolder;
        }
        return null;
    }

    public static File getAudioFolder(Project p){
        File projectFolder = getProjectFolder(p);
        if(projectFolder != null){
            File audioFolder = new File(projectFolder, AUDIO_FOLDER_NAME);
            if(!audioFolder.exists()){
                audioFolder.mkdir();
            }
            return audioFolder;
        }
        return null;
    }

    public static File getPictureFolder(Project p){
        File projectFolder = getProjectFolder(p);
        if(projectFolder != null){
            File picFolder = new File(projectFolder, PICTURE_FOLDER_NAME);
            if(!picFolder.exists()){
                picFolder.mkdir();
            }
            return picFolder;
        }
        return null;
    }

    public static File getExtraFolder(Project p){
        File projectFolder = getProjectFolder(p);
        if(projectFolder != null){
            File extraFolder = new File(projectFolder, EXTRA_FOLDER_NAME);
            if(!extraFolder.exists()){
                extraFolder.mkdir();
            }
            return extraFolder;
        }
        return null;
    }

}
