package com.stuart.fileexplorer.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.stuart.fileexplorer.R;
import com.stuart.fileexplorer.entitiy.FileInfo;
import com.stuart.fileexplorer.manager.LoadImageTask;
import com.stuart.fileexplorer.manager.TaskChooser;
import com.stuart.fileexplorer.utils.Utils;

import java.util.List;

/**
 * Created by stuart on 2016/9/1.
 */
public class CategoryListItemAdapter extends BaseAdapter {

    private List<FileInfo> mList;
    private FileInfo.Category mCategory;
    private LayoutInflater mInflator;
    private Context mContext;

    private Handler mHandler = new Handler();

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
        final ViewHolder vh;
        if (view == null) {
            view = mInflator.inflate(R.layout.category_list_item_layout, null);
            vh = new ViewHolder(view);
        } else {
            vh = (ViewHolder) view.getTag();
        }

        final FileInfo item = getItem(i);
        TaskChooser chooser = TaskChooser.getTaskChooser();

        chooser.addTask(new LoadImageTask(mContext, vh.iv, item.getFile(), item.getCategory()));

        vh.tvName.setText(item.getFile().getName());
        vh.tvCount.setText(Utils.formatFileSize(item.getFile().length()));
        vh.tvChangeTime.setText(Utils.formatDate(item.getFile().lastModified()));
        return view;
    }


    private class ViewHolder {

        ImageView iv;
        TextView tvName;
        TextView tvCount;
        TextView tvChangeTime;

        public ViewHolder(View v) {
            iv = (ImageView) v.findViewById(R.id.category_icon);
            tvName = (TextView) v.findViewById(R.id.category_name);
            tvCount = (TextView) v.findViewById(R.id.category_count);
            tvChangeTime = (TextView) v.findViewById(R.id.category_change_time);
            v.setTag(this);
        }
    }
}
