package com.stuart.fileexplorer.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.stuart.fileexplorer.R;
import com.stuart.fileexplorer.adapter.CategoryItemAdapter;
import com.stuart.fileexplorer.adapter.CategoryListItemAdapter;
import com.stuart.fileexplorer.entitiy.FileCategoryInfo;
import com.stuart.fileexplorer.entitiy.FileInfo;
import com.stuart.fileexplorer.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stuart on 2016/8/19.
 */
public class CategoryFragment extends BaseFragment {

    private ListView mListViewCategory;
    private ListView mListViewFile;

    private CategoryItemAdapter mAdapter;

    private FileInfo.Category mCurrentCategory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.category_fragment, null);
        mListViewCategory = (ListView) view.findViewById(R.id.category_lv);
        mListViewFile = (ListView) view.findViewById(R.id.file_list_lv);

        mListViewCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                mCurrentCategory = mAdapter.getItem(i).getCategory();
                showCategory();


            }
        });

        return view;
    }

    private void showCategory() {
        mState = State.LIST;
        List<FileInfo> allFile = FileUtils.getAllFile(getActivity(), mCurrentCategory);
        CategoryListItemAdapter adapter = new CategoryListItemAdapter(getActivity(), allFile);
        mListViewFile.setAdapter(adapter);

        showListFile(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        if (mState == State.CATEGORY) {
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

                    mAdapter = new CategoryItemAdapter(getActivity(), mFileCategoryInfos);
                    mListViewCategory.setAdapter(mAdapter);

                    showListFile(false);
                }
            });
        } else {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    showCategory();
                }
            });
        }


    }

    private void showListFile(boolean show) {
        mListViewFile.setVisibility(show ? View.VISIBLE : View.GONE);
        mListViewCategory.setVisibility(!show ? View.VISIBLE : View.GONE);
    }

    private State mState = State.CATEGORY;

    private enum State {
        CATEGORY(1), LIST(2);

        public int value;

        State(int i) {
            this.value = i;
        }
    }


    @Override
    public boolean onBackPressed() {
        if (mState == State.LIST) {

            mState = State.CATEGORY;

            showListFile(false);
            return true;
        }

        return false;
    }
}
