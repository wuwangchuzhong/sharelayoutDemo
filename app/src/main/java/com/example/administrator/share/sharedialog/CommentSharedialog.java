package com.example.administrator.share.sharedialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.share.R;

/**
 * Created by miao on 2017/11/24.
 * 评论分享的界面
 */

public class CommentSharedialog extends Dialog {

    private ImageView lot_image;
    private TextView tv_lot_name;
    private TextView lot_priecle;
    private TextView tv_comment;
    private ImageView tv_acout_circle;//头像
    private TextView tv_acout_name;
    private TextView iv_comment_time;
    private ImageView iv_erwei;

    public CommentSharedialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_comment_dialog);
        lot_image = (ImageView)findViewById(R.id.lot_image);
        tv_lot_name = (TextView)findViewById(R.id.tv_lot_name);
        lot_priecle = (TextView)findViewById(R.id.lot_priecle);
        tv_comment = (TextView)findViewById(R.id.tv_comment);
        tv_acout_circle = (ImageView)findViewById(R.id.tv_acout_circle);
        tv_acout_name = (TextView)findViewById(R.id.tv_acout_name);
        iv_comment_time = (TextView)findViewById(R.id.iv_comment_time);
        iv_erwei = (ImageView)findViewById(R.id.iv_erwei);
    }
}
