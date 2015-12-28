package com.chrynan.puzzle;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.chrynan.puzzle.interfaces.Piece;
import com.chrynan.puzzle.model.Project;
import com.chrynan.puzzle.model.Result;
import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.io.File;
import java.util.List;

/**
 * Created by chRyNaN on 12/11/2015.
 */
public class Puzzle {
    private Context context;

    private static volatile Puzzle singleton = null;

    private Puzzle(Context context){
        this.context = context;
    }

    public PuzzleRequest<Project> pieceTogether(final Project project){
        final PuzzleRequest<Project> request = new PuzzleRequest<>();
        TaskUtil.performVoidParameterTask(new AsyncTask<Void, Void, Project>() {
            @Override
            protected Project doInBackground(Void... params) {
                try {
                    //Save the Entity to the database
                    project.save();
                    //Save all the bitmap files that were not already saved
                    Storage.saveAllBitmaps(project);
                    //Save all the audio files that were not already saved
                    Storage.saveAllTracks(project);
                    //Save all the extra files that were not already saved
                    Storage.saveAllExtras(project);
                    return project;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Project p) {
                if (p == null) {
                    setResult(request, null, new Error());
                } else {
                    Result<Project> result = new Result<>(project);
                    setResult(request, result, null);
                }
            }
        });
        return request;
    }

    public <T extends Piece> PuzzleRequest<T> findPiece(final Long id, final Class<T> piece){
        final PuzzleRequest<T> request = new PuzzleRequest<>();
        TaskUtil.performVoidParameterTask(new AsyncTask<Void, Void, List<T>>() {
            @Override
            protected List<T> doInBackground(Void... params) {
                try {
                    List<T> pieces = Select.from(piece).where(Condition.prop("id").eq(id)).list();
                    return pieces;
                } catch (Exception e) {
                    e.printStackTrace();
                    setResult(request, null, new Error());
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<T> pieces) {
                if (pieces != null && pieces.size() > 0) {
                    Result<T> result = new Result<>(pieces);
                    setResult(request, result, null);
                } else if (pieces != null) {
                    setResult(request, new Result<T>(), null);
                }
            }
        });
        return request;
    }

    public <T extends Piece> PuzzleRequest<T> getPieces(final Class<T> piece){
        final PuzzleRequest<T> request = new PuzzleRequest<>();
        TaskUtil.performVoidParameterTask(new AsyncTask<Void, Void, List<T>>() {
            @Override
            protected List<T> doInBackground(Void... params) {
                try {
                    List<T> pieces = Select.from(piece).list();
                    return pieces;
                } catch (Exception e) {
                    e.printStackTrace();
                    setResult(request, null, new Error());
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<T> pieces) {
                if (pieces != null && pieces.size() > 0) {
                    Result<T> result = new Result<>(pieces);
                    setResult(request, result, null);
                } else if (pieces != null) {
                    setResult(request, new Result<T>(), null);
                }
            }
        });
        return request;
    }

    public <T extends Piece> PuzzleRequest<T> removePiece(final Long id, final Class<T> piece){
        final PuzzleRequest<T> request = new PuzzleRequest<>();
        TaskUtil.performVoidParameterTask(new AsyncTask<Void, Void, List<T>>() {
            @Override
            protected List<T> doInBackground(Void... params) {
                try {
                    List<T> pieces = Select.from(piece).where(Condition.prop("id").eq(id)).list();
                    if (pieces != null && pieces.size() > 0) {
                        T p = pieces.get(0);
                        ((SugarRecord) p).delete();
                    }
                    return pieces;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(List<T> pieces) {
                if (pieces != null && pieces.size() > 0) {
                    Result<T> result = new Result<>(pieces);
                    setResult(request, result, null);
                } else {
                    setResult(request, null, new Error());
                }
            }
        });
        return request;
    }

    public Project newProject(String projectName){
        Project p = new Project();
        p.setName(projectName);
        p.save();
        File folder = Storage.createProjectFolder(p);
        if(folder != null){
            p.setFolderLocation(folder.getAbsolutePath());
        }
        return p;
    }

    public PuzzleRequest<Project> getProject(Long id){
        return findPiece(id, Project.class);
    }

    public PuzzleRequest<Project> getAllProjects(){
        return getPieces(Project.class);
    }

    public static Puzzle with(Context context){
        if(singleton == null){
            if(context == null){
                throw new IllegalArgumentException();
            }
            synchronized (Puzzle.class){
                if(singleton == null){
                    singleton = new Puzzle(context);
                }
            }
        }
        return singleton;
    }

    private <T extends Piece> void setResult(final PuzzleRequest<T> request, final Result<T> result, final Error error){
        if(TaskUtil.isOnUIThread()){
            if(request != null){
                request.setResult(result, error);
            }
        }else{
            Handler h = new Handler(Looper.getMainLooper());
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    request.setResult(result, error);
                }
            };
            h.post(runnable);
        }
    }

}
