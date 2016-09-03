package com.stuart.fileexplorer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.stuart.fileexplorer.R;

/**
 * Created by stuart on 2016/8/19.
 */
public class FileListFragment extends BaseFragment{

    private ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.file_list_fragment, null);
        mListView = (ListView) view.findViewById(R.id.file_list_lv);


        return view;
    }
}
