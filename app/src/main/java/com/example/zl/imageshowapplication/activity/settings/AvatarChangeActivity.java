package com.example.zl.imageshowapplication.activity.settings;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.DeleteCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.bean.bcy.retro.ResultVO;
import com.example.zl.imageshowapplication.config.UILConfig;
import com.example.zl.imageshowapplication.utils.AvatarSelectUtil;
import com.example.zl.imageshowapplication.utils.BcyActivityManager;
import com.example.zl.imageshowapplication.utils.MD5Utils;
import com.example.zl.locallogin.LocalCallback;
import com.example.zl.locallogin.LocalUserHandle;
import com.example.zl.locallogin.bean.LocalError;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.example.zl.imageshowapplication.utils.AvatarSelectUtil.PHONE_CROP;
import static com.example.zl.imageshowapplication.utils.AvatarSelectUtil.SCAN_OPEN_PHONE;

/**
 * Created by ZhongLeiDev on 2018/10/16.
 * 变更头像 Activity
 */

public class AvatarChangeActivity extends AppCompatActivity{

    private static final String TAG = "AvatarChange";

    @Bind(R.id.change_avatar_image)
    ImageView avatar;
    @Bind(R.id.change_avatar_text)
    TextView name;
    @Bind(R.id.change_avatar_from_album)
    LinearLayout fromAlbum;
    @Bind(R.id.change_avatar_from_phone)
    LinearLayout fromPhone;
    @Bind(R.id.change_avatar_upload)
    Button upload;
    @Bind(R.id.change_avatar_loading)
    LottieAnimationView loading;

    /**当前头像临时存储路径*/
    private static final String CURRENTAVATAR = UILConfig.AVATARPATH + File.separator + "currentAvatar.png";
    private Uri mCutUri;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity_changeavatar);

        ButterKnife.bind(this);

        //将当前 Activity 纳入管理
        BcyActivityManager.getActivityManager().addActivity(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.change_avatar_title));

        setAvatar();

        fromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, SCAN_OPEN_PHONE);
            }
        });

        fromPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 添加相机调用程序
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProgress();
                uploadAvatar();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case SCAN_OPEN_PHONE: //从相册图片后返回的uri
                    //启动裁剪
                    startActivityForResult(CutForPhoto(data.getData()), PHONE_CROP);
                    break;
                case AvatarSelectUtil.PHONE_CAMERA: //相机返回的 uri
                    //启动裁剪
                    String path = this.getExternalCacheDir().getPath();
                    String name = "output.png";
                    startActivityForResult(CutForCamera(path,name),PHONE_CROP);
                    break;
                case PHONE_CROP:
                    Glide.with(this).load(mCutUri)
                            .transition(withCrossFade())    //渐隐特效显示
                            .apply(
                                    RequestOptions
                                            .bitmapTransform(new CircleCrop())  //圆形显示
                                            .skipMemoryCache(true)  //跳过内存缓存
                                            .diskCacheStrategy(DiskCacheStrategy.NONE)  //跳过磁盘缓存
                            )
                            .into(avatar);

                    break;
            }
        }
    }

    /**
     * 上传头像文件
     */
    private void uploadAvatar() {
        if (mCutUri != null) {
            LocalUserHandle.doAvatarChange(mCutUri.getPath(), new LocalCallback<ResultVO<String>>() {
                @Override
                public void done(ResultVO<String> stringResultVO, LocalError e) {
                    if (e == null) {
                        Log.i(TAG, "avatar upload succeed!");

                        if (AvatarSelectUtil.copyAvatar(CURRENTAVATAR,
                                AvatarSelectUtil.buildAvatarPath(LocalUserHandle.currentUser().getUserName()))) {
                            stopProgress();
                            Toast.makeText(AvatarChangeActivity.this,"上传成功！",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        stopProgress();
                        Toast.makeText(AvatarChangeActivity.this,e.getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else { //如果没有选择头像文件则给出提示
            stopProgress();
            Toast.makeText(this,"还未选择头像文件！",Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 开启进度条
     */
    private void startProgress() {
        loading.setVisibility(View.VISIBLE);
    }

    /**
     * 关闭进度条
     */
    private void stopProgress() {
        setAvatar();
        loading.setVisibility(View.GONE);
    }

    /**
     * 图片裁剪
     * @param uri
     * @return
     */
    @NonNull
    private Intent CutForPhoto(Uri uri) {
        try {
            //直接裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            //设置裁剪之后的图片路径文件
//      File cutfile = new File(Environment.getExternalStorageDirectory().getPath(),
//              "cutcamera.png"); //随便命名一个
            File cutfile = new File(CURRENTAVATAR); //随便命名一个
            if (cutfile.exists()){ //如果已经存在，则先删除
                boolean delete = cutfile.delete();
                Log.i(TAG,"File Delete Status -> " + delete);
            }
            cutfile.createNewFile();
            //初始化 uri
            Uri imageUri = uri; //返回来的 uri
            Uri outputUri = null; //真实的 uri
            Log.d(TAG, "CutForPhoto: "+ cutfile);
            outputUri = Uri.fromFile(cutfile);
            mCutUri = outputUri;
            Log.d(TAG, "mCameraUri: "+ mCutUri);
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop",true);
            // aspectX,aspectY 是宽高的比例，这里设置正方形
            intent.putExtra("aspectX",1);
            intent.putExtra("aspectY",1);
            //设置要裁剪的宽高
            intent.putExtra("outputX", AvatarSelectUtil.dip2px(this,200)); //200dp
            intent.putExtra("outputY", AvatarSelectUtil.dip2px(this,200));
            intent.putExtra("scale",true);
            //如果图片过大，会导致oom，这里设置为false
            intent.putExtra("return-data",false);
            if (imageUri != null) {
                intent.setDataAndType(imageUri, "image/*");
            }
            if (outputUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            }
            intent.putExtra("noFaceDetection", true);
            //压缩图片
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            return intent;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 相机获取图片裁剪
     * @param path
     * @param name
     * @return
     */
    private Intent CutForCamera(String path, String name) {
        //TODO 相机获取图片处理
        return null;
    }

    private void setAvatar() {
        if (LocalUserHandle.currentUser() != null){
            AvatarSelectUtil.setAvatarWithPath1(
                    this,avatar, AvatarSelectUtil.buildAvatarPath(LocalUserHandle.currentUser().getUserName()));
            name.setText(LocalUserHandle.currentUser().getUserName());
        } else {
            Glide.with(this).load(R.drawable.default_user)  //圆形显示
                    .transition(withCrossFade())    //渐隐特效显示
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(avatar);
            name.setText("未登录");
        }
    }

}
