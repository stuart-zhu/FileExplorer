package com.stuart.fileexplorer.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.stuart.fileexplorer.R;
import com.stuart.fileexplorer.adapter.CategoryItemAdapter;
import com.stuart.fileexplorer.entitiy.FileCategoryInfo;
import com.stuart.fileexplorer.entitiy.FileInfo;
import com.stuart.fileexplorer.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stuart on 2016/8/19.
 */
public class CategoryFragment extends Fragment {

    private ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.category_fragment, null);
        mListView = (ListView) view.findViewById(R.id.category_lv);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                List<FileInfo> allFile = FileUtils.getAllFile(getActivity(), FileInfo.Category.PICTURE);
                List<FileCategoryInfo> mFileCategoryInfos = new ArrayList<>();
                mFileCategoryInfos.add(new FileCategoryInfo(allFile, FileInfo.Category.PICTURE));

                allFile = FileUtils.getAllFile(getActivity(), FileInfo.Category.MUSIC);
                mFileCategoryInfos.add(new FileCategoryInfo(allFile, FileInfo.Category.MUSIC));

                allFile = FileUtils.getAllFile(getActivity(), FileInfo.Category.VIDEO);
                mFileCategoryInfos.add(new FileCategoryInfo(allFile, FileInfo.Category.VIDEO));

                CategoryItemAdapter adapter = new CategoryItemAdapter(getActivity(), mFileCategoryInfos);
                mListView.setAdapter(adapter);

            }
        });

    }
}
