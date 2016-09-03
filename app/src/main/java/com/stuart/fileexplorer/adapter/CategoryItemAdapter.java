package com.stuart.fileexplorer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.stuart.fileexplorer.R;
import com.stuart.fileexplorer.entitiy.FileCategoryInfo;

import java.util.List;

/**
 * Created by stuart on 2016/8/30.
 */
public class CategoryItemAdapter extends BaseAdapter {

    private List<FileCategoryInfo> mFileCategoryInfos;

    private LayoutInflater mInflator;

    public CategoryItemAdapter(Context context, List<FileCategoryInfo> mFileCategoryInfo) {
        mInflator = LayoutInflater.from(context);
        this.mFileCategoryInfos = mFileCategoryInfo;
    }

    @Override
    public int getCount() {
        return mFileCategoryInfos == null ? 0 :
                mFileCategoryInfos.size();
    }

    @Override
    public FileCategoryInfo getItem(int i) {
        return mFileCategoryInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null) {
            view = mInflator.inflate(R.layout.category_item_layout, null);
            vh = new ViewHolder(view);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        FileCategoryInfo item = getItem(i);
        int textId;
        int iconId = 0;
        switch (item.getCategory()) {
            case DOCUMENT:
                textId = R.string.category_doc;
//                iconId = R.drawable.ic_fileexplorer_download_normal
                break;
            case MUSIC:
                textId = R.string.category_music;
                iconId = R.drawable.ic_fileexplorer_music_normal;
                break;
            case PICTURE:
                textId = R.string.category_picture;
                iconId = R.drawable.ic_fileexplorer_image_normal;
                break;
            case VIDEO:
                textId = R.string.category_video;
                iconId = R.drawable.ic_fileexplorer_video_normal;
                break;

            case DOWNLOAD:
                textId = R.string.category_apk;
                iconId = R.drawable.ic_fileexplorer_download_normal;
                break;
            case APK:
                textId = R.string.category_other;
                iconId = R.drawable.ic_fileexplorer_apk_normal;
                break;

            case OTHER:


                textId = R.string.category_other;
                break;
            default:
                textId = R.string.category_other;
                break;
        }
        vh.tvName.setText(textId);
        vh.tvCount.setText(item.getFileInfo() == null ? 0 + "" : item.getFileInfo().size() + "");

        if (iconId != 0) {
            vh.iv.setImageResource(iconId);
        }
        return view;
    }

    private class ViewHolder {

        ImageView iv;
        TextView tvName;
        TextView tvCount;

        public ViewHolder(View v) {
            iv = (ImageView) v.findViewById(R.id.category_icon);
            tvName = (TextView) v.findViewById(R.id.category_name);
            tvCount = (TextView) v.findViewById(R.id.category_count);
            v.setTag(this);
        }
    }
}
