package com.stuart.fileexplorer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.stuart.fileexplorer.R;
import com.stuart.fileexplorer.entitiy.FileInfo;
import com.stuart.fileexplorer.utils.Utils;

import java.util.List;

/**
 * Created by lenovo on 2016/9/1.
 */
public class CategoryListItemAdapter extends BaseAdapter {

    private List<FileInfo> mList;
    private FileInfo.Category mCategory;
    private LayoutInflater mInflator;
    private Context mContext;

    public CategoryListItemAdapter(Context context, List<FileInfo> list) {
        mContext = context;
        mInflator = LayoutInflater.from(context);
        mList = list;
        if (mList != null && mList.size() > 0)
            mCategory = mList.get(0).getCategory();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public FileInfo getItem(int i) {
        return mList.get(i);
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

        FileInfo item = getItem(i);
        BitmapUtils bu = new BitmapUtils(mContext);
        switch (mCategory) {
            case MUSIC:
                break;
            case DOCUMENT:
                break;
            case PICTURE:
                bu.configDefaultLoadingImage(R.drawable.ic_fileexplorer_image_normal);
                bu.configDefaultLoadFailedImage(R.drawable.ic_fileexplorer_image_normal);
                bu.display(vh.iv, item.getFile().getAbsolutePath());
                break;
            case VIDEO:
                break;
            case OTHER:
                break;
            case APK:
                break;
            case DOWNLOAD:
                break;
        }

        vh.tvName.setText(item.getFile().getName());
        vh.tvCount.setText(Utils.formatFileSize(item.getFile().length()));
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
