package com.chrynan.puzzle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.chrynan.puzzle.model.Clip;
import com.chrynan.puzzle.model.Project;
import com.chrynan.puzzle.model.Track;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public static boolean isInProjectDirectory(Project p, File file){
        if(p == null || file == null){
            return false;
        }
        String path = getProjectFilesDirectory().getAbsolutePath() + File.separator + p.getName();
        if(file.getAbsolutePath().startsWith(path) || file.getAbsolutePath().startsWith(path + "_" + p.getId())){
            return true;
        }
        return false;
    }

    public static boolean containsAudioFile(Project p, File file){
        if(isInProjectDirectory(p, file)){
            File f = new File(getAudioFolder(p), file.getName()); //TODO check this logic
            if(f != null && f.exists()){
                return true;
            }
        }
        return false;
    }

    public static List<File> saveAllTracks(Project p){
        List<File> files = new ArrayList<>();
        //Slow; O(n ^ 2)
        for(Track t : p.getTracks()){
            for(Clip c : t.getClips()){
                if(!containsAudioFile(p, new File(c.getFileLocation()))){
                    //Doesn't already contain the audio file in the project directory, so move it to the project directory
                    moveFile(new File(c.getFileLocation()), new File(getAudioFolder(p), c.getName() + "_" + c.getId()));
                }
            }
        }
        if(p.getMasterTrack() != null && p.getMasterTrack().getClips() != null && p.getMasterTrack().getClips().size() > 0){
            File f = new File(p.getMasterTrack().getClips().get(0).getFileLocation());
            if(!isInProjectDirectory(p, f)){
                moveFile(f, new File(getProjectFolder(p), "master_" + p.getMasterTrack().getName() + "_" + p.getMasterTrack().getId()));
            }
        }
        return null;
    }

    public static File saveBitmap(Project p, Bitmap b, String name){
        FileOutputStream out = null;
        File picFile = new File(getPictureFolder(p), name);
        try {
            out = new FileOutputStream(picFile);
            b.compress(Bitmap.CompressFormat.PNG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return picFile;
    }

    public static List<File> saveAllBitmaps(Project p){
        try{
            List<File> newlySaved = new ArrayList<>();
            List<Bitmap> oldBitmaps = getBitmaps(p);
            Bitmap c;
            //Slow; O(n^2)
            for(Bitmap b : p.getPictures()){
                for(int i = 0; i < oldBitmaps.size(); i++){
                    c = oldBitmaps.get(i);
                    if(c.sameAs(b)){
                       break;
                    }else if(i + 1 == oldBitmaps.size()){
                        //Old bitmap list doesn't contain this bitmap so save it
                        newlySaved.add(saveBitmap(p, b, UUID.randomUUID().toString()));
                    }
                }
            }
            return newlySaved;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static List<Bitmap> getBitmaps(Project p){
        try{
            File picFolder = getPictureFolder(p);
            List<Bitmap> bitmaps = new ArrayList<>();
            for(File f : picFolder.listFiles()){
                bitmaps.add(getBitmap(f));
            }
            return bitmaps;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap getBitmap(File file){
        return getBitmap(file.getAbsolutePath());
    }

    public static Bitmap getBitmap(String path){
        try{
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static File copyFile(File oldFile, File newFile){
        if(oldFile == null || newFile == null || !oldFile.exists()){
            return null;
        }
        if(!newFile.exists()){
            newFile.mkdirs();
        }
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(oldFile);
            out = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            // write the output file
            out.flush();
            out.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return newFile;
    }

    public static File moveFile(File oldFile, File newFile){
        if(oldFile == null || newFile == null || !oldFile.exists()){
            return null;
        }
        if(!newFile.exists()){
            newFile.mkdirs();
        }
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(oldFile);
            out = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            // write the output file
            out.flush();
            out.close();
            oldFile.delete();
        }catch(Exception e){
            e.printStackTrace();
        }
        return newFile;
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
