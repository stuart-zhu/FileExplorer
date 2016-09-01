package com.stuart.fileexplorer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.stuart.fileexplorer.R;
import com.stuart.fileexplorer.entitiy.FileInfo;
import com.stuart.fileexplorer.utils.Utils;

import java.io.File;
import java.util.List;

/**
 * Created by lenovo on 2016/8/31.
 */
public class FileListItemAdapter extends BaseAdapter {

    private FileListItemAdapter() {
    }

    private LayoutInflater inflater;

    private List<FileInfo> mList;

    public FileListItemAdapter(Context context, List<FileInfo> list) {
        inflater = LayoutInflater.from(context);
        mList = list;
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
            view = inflater.inflate(R.layout.file_list_item_layout, null);
            vh = new ViewHolder(view);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        FileInfo fileInfo = getItem(i);

        File file = fileInfo.getFile();

        vh.tvName.setText(file.getName());
        if (file.isFile()) {
            vh.tvCount.setVisibility(View.GONE);

        } else if (file.isDirectory()) {
            vh.iv.setImageResource(R.drawable.icon_folder);
            vh.tvCount.setVisibility(View.VISIBLE);
            vh.tvCount.setText(file.list().length);
        }
        vh.tvSize.setText(Utils.formatFileSize(file.length()));
//        vh.tvSize.setText(file.length());
        return null;
    }

    private class ViewHolder {
        ImageView iv;
        TextView tvName, tvSize, tvCount;

        public ViewHolder(View v) {
            iv = (ImageView) v.findViewById(R.id.file_icon);
            tvName = (TextView) v.findViewById(R.id.file_name);
            tvSize = (TextView) v.findViewById(R.id.file_size);
            tvCount = (TextView) v.findViewById(R.id.file_count);
            v.setTag(this);
        }
    }

}
