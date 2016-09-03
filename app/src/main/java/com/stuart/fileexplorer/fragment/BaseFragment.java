package com.stuart.fileexplorer.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by stuart on 2016/9/1.
 */
public class BaseFragment extends Fragment{

    /**
     * true 拦截 false 拦截
     * @return
     */
    public boolean onBackPressed() {
        return false;
    }
}
