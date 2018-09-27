package com.example.zl.commoncomponent;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zl.imageshowapplication.R;

/**
 * Created by Administrator on 2018/4/11.
 * 自定义 layout
 */

public class ImageTextLinearLayout extends LinearLayout {

    private ImageView imageView;
    private TextView textView;

    public ImageTextLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.imagetextlinearlayout, this, true);
        imageView = findViewById(R.id.cpimageView);
        textView = findViewById(R.id.cptextView);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ImageTextLinearLayout);
        if (attributes != null) {
            //设置资源图片
            int imageDrawable = attributes.getResourceId(R.styleable.ImageTextLinearLayout_default_src, R.color.colorWhite);
            if (imageDrawable != -1) {
                imageView.setImageResource(imageDrawable);
            }
            //设置背景
            int background = attributes.getResourceId(R.styleable.ImageTextLinearLayout_default_background, R.color.colorWhite);
            if (imageDrawable != -1) {
                imageView.setBackgroundResource(background);
            }
            //设置文字
            String text = attributes.getString(R.styleable.ImageTextLinearLayout_default_text);
            textView.setText(text);
            //设置文字颜色
            int textColor = attributes.getColor(R.styleable.ImageTextLinearLayout_default_text_color, Color.WHITE);
            textView.setTextColor(textColor);
            //设置文字大小
            float textSize = attributes.getDimension(R.styleable.ImageTextLinearLayout_default_text_size, 18);
            textView.setTextSize(textSize);
            //设置 TextView 是否显示
            boolean istextVisible = attributes.getBoolean(R.styleable.ImageTextLinearLayout_text_visible, true);
            if (istextVisible) {
                textView.setVisibility(View.VISIBLE);
            } else {
                textView.setVisibility(View.GONE);  //直接消失，不占位
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
