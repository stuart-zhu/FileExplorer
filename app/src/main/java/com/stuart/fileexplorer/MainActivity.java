package com.stuart.fileexplorer;

import android.os.Bundle;
import android.os.storage.StorageManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.stuart.fileexplorer.fragment.BaseFragment;
import com.stuart.fileexplorer.fragment.CategoryFragment;
import com.stuart.fileexplorer.fragment.FileListFragment;

import java.util.ArrayList;
import java.util.List;


@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    private String[] TAB;
    private List<BaseFragment> mFragments;
    private BaseFragment mCurrentFragment;

    @ViewInject(R.id.vp)
    private ViewPager pager;

    @ViewInject(R.id.tabs)
    private PagerTabStrip tabStrip;

    private StorageManager mStorageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewUtils.inject(this);
        mStorageManager = (StorageManager) getSystemService(STORAGE_SERVICE);
        initData();
        initView();

    }

    private void initData() {
        TAB = getResources().getStringArray(R.array.tab_);

        // 按照顺序添加
        mFragments = new ArrayList<>();
        mFragments.add(new CategoryFragment());
        mFragments.add(new FileListFragment());

        mCurrentFragment = mFragments.get(0);
    }


    @Override
    public void onBackPressed() {
        if (mCurrentFragment != null && mCurrentFragment.onBackPressed()) {

        } else {
            super.onBackPressed();
        }
    }

    /*private void initFragments() {
            mStorageVolumes = mStorageManager.getVolumeList();
            for (StorageVolume volume : mStorageVolumes) {
                if (VolumeUtils.isMounted(volume)) {
                    final FileViewFragment fileViewFragment = new FileViewFragment();
                    String title = VolumeUtils.getVolumeName(
                            getApplicationContext(), volume);
                    fileViewFragment.setNavigatorTitle(title);
                    fileViewFragment.setRootPath(volume.getPath());
                    fileViewFragment.setPostFileOperationCallback(this);
                    mPageTitles.add(title);
                    mFragments.add(fileViewFragment);
                    mStoragePosition.put(title, mFragments.size() - 1);
                }
            }
        }*/
    private void initView() {
        FragmentPagerAdapter adapter = new TabPageIndicatorAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);

        tabStrip.setTabIndicatorColorResource(android.R.color.black);
        pager.setOnPageChangeListener((ViewPager.OnPageChangeListener) adapter);

    }

    class TabPageIndicatorAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {
        public TabPageIndicatorAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //新建一个Fragment来展示ViewPager item的内容，并传递参数
            Fragment fragment = mFragments.get(position);
            Bundle args = new Bundle();
//            args.putString("arg", TITLE[position]);
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TAB[position];//TITLE[position % TITLE.length];
        }

        @Override
        public int getCount() {
            return TAB.length;//TITLE.length;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            mCurrentFragment = mFragments.get(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


}
