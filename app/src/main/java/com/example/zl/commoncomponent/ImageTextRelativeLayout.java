package com.example.zl.commoncomponent;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zl.imageshowapplication.R;

/**
 * Created by Administrator on 2018/4/11.
 */

public class ImageTextRelativeLayout extends RelativeLayout {

    private ImageView imageView;
    private TextView textView;

    public ImageTextRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.imagetextrelativelayout, this, true);
        imageView = (ImageView)findViewById(R.id.cpimageView2);
        textView = (TextView)findViewById(R.id.cptextView2);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ImageTextRelativeLayout);
        if (attributes != null) {
            //设置资源图片
            int imageDrawable = attributes.getResourceId(R.styleable.ImageTextRelativeLayout_default_src_r, R.color.colorWhite);
            if (imageDrawable != R.color.colorWhite) {
                imageView.setImageResource(imageDrawable);
            }
            //设置背景
            int background = attributes.getResourceId(R.styleable.ImageTextRelativeLayout_default_background_r, R.color.colorWhite);
            if (imageDrawable != R.color.colorWhite) {
                imageView.setBackgroundResource(background);
            }
            //设置文字
            String text = attributes.getString(R.styleable.ImageTextRelativeLayout_default_text_r);
            textView.setText(text);
            //设置文字颜色
            int textColor = attributes.getColor(R.styleable.ImageTextRelativeLayout_default_text_color_r, Color.WHITE);
            textView.setTextColor(textColor);
            //设置文字大小
            float textSize = attributes.getDimension(R.styleable.ImageTextRelativeLayout_default_text_size_r, 18);
            textView.setTextSize(textSize);
            //设置 TextView 是否显示
            boolean istextVisible = attributes.getBoolean(R.styleable.ImageTextRelativeLayout_text_visible_r, true);
            if (istextVisible) {
                textView.setVisibility(View.VISIBLE);
            } else {
                textView.setVisibility(View.INVISIBLE);
            }
        }

    }

    /**
     * 设置图片点击响应事件
     * @param onClickListener
     */
    public void setOnImageClickListener(OnClickListener onClickListener) {
        if (onClickListener != null) {
            imageView.setOnClickListener(onClickListener);
        }
    }

    /**
     * 获取 ImageView
     * @return
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * 获取 TextView
     * @return
     */
    public TextView getTextView() {
        return textView;
    }

}
