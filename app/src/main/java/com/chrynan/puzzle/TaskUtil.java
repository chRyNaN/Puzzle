package com.chrynan.puzzle;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

/**
 * Created by chRyNaN on 12/15/2015. A simple class that handles executing tasks on the main thread.
 */
public class TaskUtil {

    public static boolean isOnUIThread(){
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static void performTask(AsyncTask task){
        if(isOnUIThread()){
            task.execute();
        }else{
            //Not on UI/Main Thread so launch from it explicitly
            Handler h = new Handler(Looper.getMainLooper());
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    task.execute();
                }
            };
            h.post(runnable);
        }
    }

    public static <T1, T2, T3> void performTask(AsyncTask<T1, T2, T3> task, T1[] params){
        if(isOnUIThread()){
            task.execute(params);
        }else{
            //Not on UI/Main Thread so launch from it explicitly
            Handler h = new Handler(Looper.getMainLooper());
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    task.execute(params);
                }
            };
            h.post(runnable);
        }
    }

}
