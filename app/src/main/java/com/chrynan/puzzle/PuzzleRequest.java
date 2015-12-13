package com.chrynan.puzzle;

import com.chrynan.puzzle.interfaces.Callback;
import com.chrynan.puzzle.interfaces.ErrorCallback;
import com.chrynan.puzzle.interfaces.Piece;
import com.chrynan.puzzle.interfaces.SuccessCallback;
import com.chrynan.puzzle.model.Result;

/**
 * Created by chRyNaN on 12/12/2015.
 */
public class PuzzleRequest<T extends Piece> {
    private ErrorCallback errorCallback;
    private SuccessCallback successCallback;
    private Callback callback;
    private Result<T> result;

    public Puzzle error(ErrorCallback errorCallback){
        //TODO
        return null;
    }

    public Puzzle success(SuccessCallback successCallback){
        //TODO
        return null;
    }

    public void then(Callback callback){
        //TODO
    }

    void setResult(Result<T> result, Error error){
        //TODO
    }

}
