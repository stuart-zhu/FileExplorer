package com.stuart.fileexplorer.manager;

import android.content.Context;
import android.util.Log;

import com.stuart.fileexplorer.utils.BitmapCache;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by stuart on 2016/9/2.
 */
public class TaskChooser {

    private static TaskChooser mInstacne;

    private Queue<LoadImageTask> mTask;

    private LoadImageTask mCurrentLoadImageTask;

    public static TaskChooser getTaskChooser() {
        if (mInstacne == null)
            mInstacne = new TaskChooser();
        return mInstacne;
    }

    public void addTask(LoadImageTask list) {
        if (mTask == null) {
            mTask = new LinkedBlockingQueue<>();
        }
        if (!mTask.contains(list))
            mTask.offer(list);
        checkList();
    }

    public void clearTask() {
        while (mTask.size() > 0) {
            LoadImageTask task = mTask.poll();
            task.cancle();

        }
    }

    private synchronized void checkList() {
        while (mTask.size() > 10) {

            LoadImageTask task = mTask.poll();
            task.cancle();

        }

        if (mCurrentLoadImageTask != null) {
            return;
        }
        if (mTask.isEmpty()) {
            return;
        }
        mCurrentLoadImageTask = mTask.poll();
        mCurrentLoadImageTask.startOnLoadImageListener(new LoadImageTask.onLoadImageListener() {
            @Override
            public void onLoadStart() {
                Log.i("stuart", "" + mCurrentLoadImageTask + "onLoadStart");
            }

            @Override
            public void onLoading() {

                Log.i("stuart", "" + mCurrentLoadImageTask + "onLoading");
            }

            @Override
            public void onLoadFinish() {
                Log.i("stuart", "" + mCurrentLoadImageTask + "onLoadFinish");
                mCurrentLoadImageTask = null;
                checkList();
            }
        });

        Log.i("stuart", mTask.size() + "," + mCurrentLoadImageTask);
        if (!mCurrentLoadImageTask.isUsed())
            mCurrentLoadImageTask.execute();
        else
            checkList();
    }

}
