package com.chrynan.puzzle.interfaces;

import com.chrynan.puzzle.model.Result;

/**
 * Created by chryNaN on 12/12/2015.
 */
public interface Callback {

    void onError(Error error);
    <T extends Piece> void onSuccess(Result<T> result);

}
