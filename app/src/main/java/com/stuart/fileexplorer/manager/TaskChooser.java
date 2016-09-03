package com.stuart.fileexplorer.manager;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.Stack;

/**
 * Created by stuart on 2016/9/2.
 */
public class TaskChooser {

    private static TaskChooser mInstacne;

    private Stack<LoadImageTask> mTask;

    private LoadImageTask mCurrentLoadImageTask;

    public static TaskChooser getTaskChooser() {
        if (mInstacne == null)
            mInstacne = new TaskChooser();
        return mInstacne;
    }

    public void addTask(LoadImageTask list) {
        if (mTask == null) {
            mTask = new Stack<>();
        }
        if (!mTask.contains(list))
            mTask.push(list);
        checkList();
    }

    private synchronized void checkList() {
        while (mTask.size() > 10) {

            LoadImageTask remove = mTask.remove(0);

            remove.cancle();

        }

        if (mCurrentLoadImageTask != null) {
            return;
        }
        if (mTask.isEmpty()) {
            return;
        }
        mCurrentLoadImageTask = mTask.pop();
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
