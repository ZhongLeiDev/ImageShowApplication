package com.example.zl.imageshowapplication.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.zl.imageshowapplication.MainActivity;
import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.bean.huaban.devided.DevidedJsonRootBean;
import com.example.zl.imageshowapplication.bean.huaban.devided.DevidedPins;
import com.example.zl.imageshowapplication.config.UILConfig;
import com.example.zl.imageshowapplication.linkanalyzestrategy.retrofits.RetrofitFactory;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ZhongLeiDev on 2018/10/10.
 * 欢迎页面
 */

public class MainWelcomePageActivity extends AppCompatActivity {

    private static final String TAG = "WelcomeActivity";

    @Bind(R.id.welcome_image_view)
    ImageView background;
    @Bind(R.id.welcome_lottie_view)
    LottieAnimationView lottieview;
    @Bind(R.id.welcome_icon_text_view)
    TextView applicationicon;
    @Bind(R.id.welcome_skip_button)
    Button skipbtn;

    private Timer timer = new Timer();
    private MyHandler mHandler;

    private int delayTimeCount = 0; //延时跳转计数器
    private int[] rest = {R.string.welcome_skip,
            R.string.welcome_skip1,
            R.string.welcome_skip2,
            R.string.welcome_skip3,
            R.string.welcome_skip4};

    /**欢迎页面下载线程*/
    private Runnable downloadWelcomePageRunnable = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG,"Start Download Thread !");
            RetrofitFactory.getHuaBanRetroSingleInstance().getIllustrationFromHuaBanWithRx(42)
                    .map(new Func1<DevidedJsonRootBean, String>() {
                        @Override
                        public String call(DevidedJsonRootBean devidedJsonRootBean) {
                            List<DevidedPins> pinsList = devidedJsonRootBean.getPins();
                            return "http://img.hb.aicdn.com/" + pinsList.get(
                                    new Random(System.currentTimeMillis()).nextInt(pinsList.size())
                            ).getFile().getKey();
                        }
                    })
                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
                    .observeOn(Schedulers.io()) //下载操作不能放在 Main 线程
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(String s) {
                            downloadImage(s);
                        }
                    });
        }
    };

    @Override
    public void onBackPressed() {
        if (timer != null) {
            timer.cancel();
        }
        MainWelcomePageActivity.this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去除标题栏
        setContentView(R.layout.activity_welcome_layout);

        ButterKnife.bind(this);

        initView();

        initData();

    }

    private void initView() {

        File file=new File(UILConfig.WELCOMEPATH + "background.jpg");
        if (file.exists()) {    //如果欢迎页缓存存在，则显示缓存的欢迎页，否则显示默认欢迎页
            lottieview.setVisibility(View.GONE); //隐藏动画效果
            ImageLoader.getInstance().displayImage( //UIL加载本地图片
                    ImageDownloader.Scheme.FILE.wrap(file.getAbsolutePath()),background);
        } else {
            background.setVisibility(View.GONE);
        }

        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer != null) {
                    timer.cancel();
                    startActivity(new Intent(MainWelcomePageActivity.this, MainActivity.class));
                    MainWelcomePageActivity.this.finish();
                }
            }
        });

    }

    private void initData() {

        mHandler = new MyHandler();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                mHandler.sendEmptyMessage(1);

            }
        },0,1000);

    }

    private void handleTimerTask() {
        skipbtn.setText(rest[delayTimeCount]);

        if (delayTimeCount == 1) {

            new Thread(downloadWelcomePageRunnable).start();

        } else if (delayTimeCount == 4) {
            timer.cancel();
            startActivity(new Intent(MainWelcomePageActivity.this, MainActivity.class));
            MainWelcomePageActivity.this.finish();
        }

        delayTimeCount ++;

    }

    class MyHandler extends Handler {

        public MyHandler() {

        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i(TAG,"Count->" + delayTimeCount);
            handleTimerTask();
        }
    }

    /**
     * 下载欢迎页
     * @param url 图片链接
     */
    private void downloadImage(String url) {
        File file = null;
        try {
            Log.i(TAG,"start download welcome page -> " + url);
            URL murl=new URL(url);
            InputStream inputStream = murl.openStream();
            file=new File(UILConfig.WELCOMEPATH + "background.jpg");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            byte[] b = new byte[1024];
            int hasRead;
            while((hasRead=inputStream.read(b))!=-1){
                fileOutputStream.write(b, 0, hasRead);
            }
            fileOutputStream.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
            if (file != null) {
                file.delete();
            }
        }
    }

}
