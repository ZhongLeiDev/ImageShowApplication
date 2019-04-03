package com.example.zl.locallogin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.zl.imageshowapplication.MainActivity;
import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.bean.bcy.retro.ResultVO;
import com.example.zl.imageshowapplication.config.UILConfig;
import com.example.zl.imageshowapplication.utils.AvatarSelectUtil;
import com.example.zl.imageshowapplication.utils.BcyActivityManager;
import com.example.zl.imageshowapplication.utils.MD5Utils;
import com.example.zl.locallogin.bean.LocalError;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;
import static com.example.zl.imageshowapplication.utils.AvatarSelectUtil.PHONE_CROP;
import static com.example.zl.imageshowapplication.utils.AvatarSelectUtil.SCAN_OPEN_PHONE;

/**
 * 自定义 Register 方法，不借助 LeanCloud
 */
public class LocalRegisterActivity extends AppCompatActivity {

  private static final String TAG = "LocalRegisterActivity";
  /**当前头像临时存储路径*/
  private static final String CURRENTAVATAR = UILConfig.AVATARPATH + File.separator + "currentAvatar.png";
  private Uri mCutUri;

  private AutoCompleteTextView mUsernameView;
  private EditText mPasswordView;
  private ImageView avatar;
  private LottieAnimationView register_loading;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_register);

    //将当前 Activity 纳入管理
    BcyActivityManager.getActivityManager().addActivity(this);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle(getString(R.string.register));
    // Set up the register form.
    mUsernameView = findViewById(R.id.register_username);

    mPasswordView = findViewById(R.id.register_password);
    mPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> {
      if (id == R.id.register || id == EditorInfo.IME_NULL) {
        attemptRegister();
        return true;
      }
      return false;
    });

    Button musernameSignInButton = findViewById(R.id.username_register_button);
    musernameSignInButton.setOnClickListener(view -> attemptRegister());

    register_loading = findViewById(R.id.register_animation_view);
    avatar = findViewById(R.id.register_avatar);

    Glide.with(this).load(R.drawable.default_user)  //圆形显示
            .apply(RequestOptions.bitmapTransform(new CircleCrop()))
            .into(avatar);

    //设置头像选择监听
    avatar.setOnClickListener(v -> {
      Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
      startActivityForResult(intent, SCAN_OPEN_PHONE);
    });

  }

  private void attemptRegister() {
    mUsernameView.setError(null);
    mPasswordView.setError(null);

    String username = mUsernameView.getText().toString();
    String password = mPasswordView.getText().toString();

    boolean cancel = false;
    View focusView = null;

    if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
      mPasswordView.setError(getString(R.string.error_invalid_password));
      focusView = mPasswordView;
      cancel = true;
    }

    if (TextUtils.isEmpty(username)) {
      mUsernameView.setError(getString(R.string.error_field_required));
      focusView = mUsernameView;
      cancel = true;
    }

    if (cancel) {
      focusView.requestFocus();
    } else {

      showProgress(true); //开启进度条显示
      doRegister(username, password,"sample@qq.com");

    }
  }

  /**
   * 注册
   * @param userName  用户名
   * @param passWord  用户密码
   * @param mail  邮箱
   */
  private void doRegister(String userName, String passWord, String mail) {
    String avatarPath;
    if (mCutUri != null) {
      avatarPath = mCutUri.getPath();
      LocalUserHandle.doRegister(userName, passWord, avatarPath, mail, new LocalCallback<ResultVO>() {

        @Override
        public void done(ResultVO resultVO, LocalError e) {
          if (e == null) {
            Log.i(TAG, resultVO.toString());
            showMessage("注册成功！");
          } else {
            showMessage(e.getMsg());
          }
          showProgress(false);  //隐藏进度条
        }
      });
    } else {
      showMessage("未选择图像文件！");
      showProgress(false);  //隐藏进度条
    }

  }

  private boolean isusernameValid(String username) {
    //TODO: Replace this with your own logic
    return username.contains("@");
  }

  private boolean isPasswordValid(String password) {
    //TODO: Replace this with your own logic
    return password.length() > 6;
  }

  /**
   * 加载进度条显示
   * @param show 显示状态
   */
  private void showProgress(final boolean show) {
    register_loading.setVisibility(show ? View.VISIBLE : View.GONE);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      onBackPressed();
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onPause() {
    super.onPause();
    AVAnalytics.onPause(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
    AVAnalytics.onResume(this);
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
   * 显示信息
   * @param msg
   */
  private void showMessage(String msg) {
    Toast.makeText(LocalRegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
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

}

