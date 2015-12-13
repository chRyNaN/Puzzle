package com.chrynan.puzzle;

import android.content.Context;

import com.chrynan.puzzle.interfaces.Piece;
import com.chrynan.puzzle.model.Project;

/**
 * Created by chRyNaN on 12/11/2015.
 */
public class Puzzle {
    private Context context;

    static volatile Puzzle singleton = null;

    private Puzzle(Context context){
        this.context = context;
    }

    public PuzzleRequest<Project> pieceTogether(Project project){
        //TODO
        return null;
    }

    public <T extends Piece> PuzzleRequest<T> findPiece(String id, Class<T> piece){
        //TODO
        return null;
    }

    public <T extends Piece> PuzzleRequest<T> getPieces(Class<T> piece){
        //TODO
        return null;
    }

    public <T extends Piece> PuzzleRequest<T> removePiece(Class<T> piece){
        //TODO
        return null;
    }

    public PuzzleRequest<Project> getProject(String id){
        //TODO
        return null;
    }

    public PuzzleRequest<Project> getAllProjects(){
        //TODO
        return null;
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
