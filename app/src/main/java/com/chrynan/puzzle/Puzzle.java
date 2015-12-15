package com.chrynan.puzzle;

import android.content.Context;
import android.os.AsyncTask;

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

    public PuzzleRequest<Project> pieceTogether(Project project){
        //TODO
        return null;
    }

    public <T extends Piece> PuzzleRequest<T> findPiece(final Long id, final Class<T> piece){
        final PuzzleRequest<T> request = new PuzzleRequest();
        TaskUtil.performTask(new AsyncTask<Void, Void, List<T>>(){
            @Override
            protected List<T> doInBackground(Void... params) {
                try {
                    List<T> pieces = Select.from(piece).where(Condition.prop("id").eq(id)).list();
                    return pieces;
                }catch(Exception e){
                    e.printStackTrace();
                    request.setResult(null, new Error());
                }
                return null;
            }
            @Override
            protected void onPostExecute(List<T> pieces){
                if(pieces != null && pieces.size() > 0){
                    Result<T> result = new Result<>(pieces);
                    request.setResult(result, null);
                }else if(pieces != null){
                    request.setResult(new Result<T>(), null);
                }
            }
        });
        return request;
    }

    public <T extends Piece> PuzzleRequest<T> getPieces(final Class<T> piece){
        final PuzzleRequest<T> request = new PuzzleRequest();
        TaskUtil.performTask(new AsyncTask<Void, Void, List<T>>(){
            @Override
            protected List<T> doInBackground(Void... params) {
                try {
                    List<T> pieces = Select.from(piece).list();
                    return pieces;
                }catch(Exception e){
                    e.printStackTrace();
                    request.setResult(null, new Error());
                }
                return null;
            }
            @Override
            protected void onPostExecute(List<T> pieces){
                if(pieces != null && pieces.size() > 0){
                    Result<T> result = new Result<>(pieces);
                    request.setResult(result, null);
                }else if(pieces != null){
                    request.setResult(new Result<T>(), null);
                }
            }
        });
        return request;
    }

    public <T extends Piece> PuzzleRequest<T> removePiece(final Long id, final Class<T> piece){
        final PuzzleRequest<T> request = new PuzzleRequest();
        TaskUtil.performTask(new AsyncTask<Void, Void, List<T>>(){
            @Override
            protected List<T> doInBackground(Void... params) {
                try {
                    List<T> pieces = Select.from(piece).where(Condition.prop("id").eq(id)).list();
                    if(pieces != null && pieces.size() > 0) {
                        T p = pieces.get(0);
                        ((SugarRecord) p).delete();
                    }
                    return pieces;
                }catch(Exception e){
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(List<T> pieces){
                if(pieces != null && pieces.size() > 0){
                    Result<T> result = new Result<>(pieces);
                    request.setResult(result, null);
                }else{
                    request.setResult(null, new Error());
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

}
