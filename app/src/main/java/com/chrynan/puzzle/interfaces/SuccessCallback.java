package com.chrynan.puzzle.interfaces;

import com.chrynan.puzzle.model.Result;

/**
 * Created by chRyNaN on 12/12/2015.
 */
public interface SuccessCallback {

    <T extends Piece> void onSuccess(Result<T> result);

}
