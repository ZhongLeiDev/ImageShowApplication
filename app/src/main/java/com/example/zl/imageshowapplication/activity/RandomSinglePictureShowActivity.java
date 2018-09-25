package com.example.zl.imageshowapplication.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.zl.commoncomponent.GridPopupWindow;
import com.example.zl.commoncomponent.SimpleToolBar;
import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.widget.ZoomImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ZhongLeiDev on 2018/9/20.
 * 淘图显示 Activity
 */

public class RandomSinglePictureShowActivity extends AppCompatActivity {

    private static final String TAG = "RandomSingleActivity";

    @Bind(R.id.random_simple_toolbar)
    SimpleToolBar simpleToolBar;
    @Bind(R.id.random_zoom_image_view)
    ZoomImageView zoomImageView;

    private SensorManager sensorManager;
    private Sensor sensor;

    /**初始化图标*/
    private int[] icons = {R.drawable.bg_shake};
    /**图标选择之后的图标*/
    private int[] icons_selected = {R.drawable.bg_shake_selected};
    private String[] icon_names = {"摇一摇"};
    private GridPopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_picture_layout);

        ButterKnife.bind(this);

        popupWindow = new GridPopupWindow(this, icons, icon_names);

        initSensor();

        initView();

        initData();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterSensor();
    }

    private void initView() {

        /*自定义工具栏*/
        Drawable dwRight1 = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.icon_refresh);
        dwRight1.setBounds(0, 0, dwRight1.getMinimumWidth(), dwRight1.getMinimumHeight());
        Drawable dwRight2 = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.icon_plus);
        dwRight2.setBounds(0, 0, dwRight2.getMinimumWidth(), dwRight2.getMinimumHeight());
        simpleToolBar.setRightTitleDrawables(dwRight1,null,dwRight2,null);
        simpleToolBar.setRightTitleText("      ");
        simpleToolBar.setRightTitleOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        /*extView.setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom)*/
                        Drawable drawableLeft = simpleToolBar.getmTxtRightTitle().getCompoundDrawables()[0];
                        Drawable drawableRight = simpleToolBar.getmTxtRightTitle().getCompoundDrawables()[2];

//                        Log.i(TAG, "event.getEawX()->" + event.getRawX() +
//                                " ,RightBound->" + simpleToolBar.getmTxtRightTitle().getRight() +
//                                " ,LeftBound->" + simpleToolBar.getmTxtRightTitle().getLeft() +
//                                " ,DrawableBoundWidth->" + drawableRight.getBounds().width());

                        if (drawableRight != null
                                && event.getRawX() >= (simpleToolBar.getmTxtRightTitle().getRight() - drawableRight.getBounds().width())
                                && event.getRawX() <= simpleToolBar.getmTxtRightTitle().getRight()) {
                            //加号图标被点击
                            showMenu();
                            Log.i(TAG, "IconPlus clicked!");
                            Toast.makeText(RandomSinglePictureShowActivity.this,"IconRefresh clicked!",Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (drawableLeft != null
                                && event.getRawX() <= (simpleToolBar.getmTxtRightTitle().getLeft() + drawableLeft.getBounds().width())
                                && event.getRawX() >= simpleToolBar.getmTxtRightTitle().getLeft()) {
                            //刷新图标被点击
                            requestData();
                            Log.i(TAG, "IconRefresh clicked!");
                            Toast.makeText(RandomSinglePictureShowActivity.this,"IconPlus clicked!",Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        break;
                }
                return false;
            }
        });

    }

    /**
     * 初始化传感器
     */
    private void initSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    /**
     * 注册传感器
     */
    private void registerSensor() {
        sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * 注销传感器
     */
    private void unregisterSensor() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }
    }

    private void initData() {

        popupWindow.setOnGridViewItemClick(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        if ((int)popupWindow.dataList.get(position).get("img") == icons[0]) {
                            popupWindow.dataList.get(position).put("img", icons_selected[0]);
                            popupWindow.adapter.notifyDataSetChanged();
                            registerSensor();
                        } else {
                            popupWindow.dataList.get(position).put("img", icons[0]);
                            popupWindow.adapter.notifyDataSetChanged();
                            unregisterSensor();
                        }
                        break;
                }

//                popupWindow.popupDismiss();

            }
        });

    }

    /**
     * 随机获取一张图片
     */
    private void requestData() {



    }

    /**
     * 显示选择菜单
     */
    private void showMenu() {
        popupWindow.popupShow();
    }

    private SensorEventListener listener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {

            float xValue = Math.abs(event.values[0]);
            float yValue = Math.abs(event.values[1]);
            float zValue = Math.abs(event.values[2]);

//            Log.i(TAG,"xValue->" + xValue + " ,yValue->" + yValue + " ,zValue->" + zValue);

            if (xValue > 15 || yValue > 15 || zValue > 15) {
                Toast.makeText(RandomSinglePictureShowActivity.this,"摇一摇!",Toast.LENGTH_SHORT).show();
                requestData();
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

}
