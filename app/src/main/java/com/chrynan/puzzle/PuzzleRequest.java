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
    private Error error;

    public PuzzleRequest(){

    }

    public PuzzleRequest<T> error(ErrorCallback errorCallback){
        this.errorCallback = errorCallback;
        if(error != null){
            this.errorCallback.onError(error);
            this.error = null;
        }
        return this;
    }

    public PuzzleRequest<T> success(SuccessCallback successCallback){
        this.successCallback = successCallback;
        if(result != null){
            this.successCallback.onSuccess(result);
            this.result = null;
        }
        return this;
    }

    public void then(Callback callback){
        this.callback = callback;
        if(error != null){
            this.callback.onError(error);
        }else if(result != null){
            this.callback.onSuccess(result);
        }
        this.result = null;
        this.error = null;
    }

    void setResult(Result<T> result, Error error){
        this.result = result;
        this.error = error;
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
