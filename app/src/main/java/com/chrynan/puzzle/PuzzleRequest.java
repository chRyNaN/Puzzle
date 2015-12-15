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

    public PuzzleRequest(){

    }

    public PuzzleRequest<T> error(ErrorCallback errorCallback){
        this.errorCallback = errorCallback;
        return this;
    }

    public PuzzleRequest<T> success(SuccessCallback successCallback){
        this.successCallback = successCallback;
        return this;
    }

    public void then(Callback callback){
        this.callback = callback;
    }

    void setResult(Result<T> result, Error error){
        if(errorCallback != null && error != null){
            errorCallback.onError(error);
        }
        if(result != null && successCallback != null){
            successCallback.onSuccess(result);
        }
        if(callback != null){
            if(error != null){
                callback.onError(error);
            }else {
                callback.onSuccess(result);
            }
        }
    }

}
