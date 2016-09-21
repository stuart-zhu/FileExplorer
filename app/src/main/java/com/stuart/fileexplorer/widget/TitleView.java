package com.stuart.fileexplorer.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stuart.fileexplorer.R;

/**
 * Created by lenovo on 2016/9/21.
 */
public class TitleView extends RelativeLayout {

    private ImageView ivBack, ivShare;
    private TextView tvTilteSummer, tvTitleName;

    public TitleView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title_view, this);

        ivBack = (ImageView) findViewById(R.id.title_back);

        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });
        ivShare = (ImageView) findViewById(R.id.title_share);

        tvTilteSummer = (TextView) findViewById(R.id.title_summer);
        tvTitleName = (TextView) findViewById(R.id.title_name);
    }

    public void setTitle(String summer, String title) {
        tvTilteSummer.setText(summer);

        tvTitleName.setText(title);
    }
}
