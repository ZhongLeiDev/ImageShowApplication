package com.example.zl.locallogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
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
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.LogInCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.utils.AvatarSelectUtil;
import com.example.zl.imageshowapplication.utils.BcyActivityManager;
import com.example.zl.imageshowapplication.utils.MD5Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class LocalLoginActivity extends AppCompatActivity {
  private AutoCompleteTextView mUsernameView;
  private EditText mPasswordView;
  private ImageView avatar;
  private LottieAnimationView loadingview;

  private boolean isAvatarExist = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    //将当前 Activity 纳入管理
    BcyActivityManager.getActivityManager().addActivity(this);

    if (AVUser.getCurrentUser() != null) {
//      startActivity(new Intent(LoginActivity.this, MainActivity.class));
      LocalLoginActivity.this.finish();
    }

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setTitle(getString(R.string.login));
    mUsernameView = findViewById(R.id.login_username);

    mPasswordView = findViewById(R.id.login_password);
    mPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> {
      if (id == R.id.login || id == EditorInfo.IME_NULL) {
        attemptLogin();
        return true;
      }
      return false;
    });
    mPasswordView.setOnFocusChangeListener((v, hasFocus) -> {
      if (hasFocus) {
        isAvatarExist = AvatarSelectUtil.setAvatarWithPath1(
                LocalLoginActivity.this,avatar,
                AvatarSelectUtil.buildAvatarPath(mUsernameView.getText().toString())
        );
      }
    });

    Button mUsernameLoginButton = findViewById(R.id.username_login_button);
    mUsernameLoginButton.setOnClickListener(view -> attemptLogin());

    TextView tvlogin = findViewById(R.id.login_go_register);
    tvlogin.setOnClickListener(view -> {
      startActivity(new Intent(LocalLoginActivity.this, LocalRegisterActivity.class));
      LocalLoginActivity.this.finish();
    });

    loadingview = findViewById(R.id.login_animation_view);
    avatar = findViewById(R.id.login_avatar);

    Glide.with(this).load(R.drawable.default_user)  //圆形显示
            .transition(withCrossFade())    //渐隐特效显示
            .apply(RequestOptions.bitmapTransform(new CircleCrop()))
            .into(avatar);

  }

  private void attemptLogin() {
    mUsernameView.setError(null);
    mPasswordView.setError(null);

    final String username = mUsernameView.getText().toString();
    final String password = mPasswordView.getText().toString();

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
      showProgress(true);

      AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
        @Override
        public void done(AVUser avUser, AVException e) {
          if (e == null) {
            if (isAvatarExist){

              showProgress(false);
              LocalLoginActivity.this.finish();

            } else {

              //下载头像文件
              AVQuery<AVObject> query = new AVQuery<>("_File");
              query.whereEqualTo("name",MD5Utils.getStringMD5(AVUser.getCurrentUser().getUsername()) + ".png");
              query.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                  if (e == null){
                    if (list.size()>0) {
                      AVFile file = new AVFile("test.png", list.get(0).getString("url"), new HashMap<String, Object>());
                      file.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] bytes, AVException e) {
                          if (e == null) {
                            File f = new File(AvatarSelectUtil.buildAvatarPath(AVUser.getCurrentUser().getUsername()));
                            try {
                              f.createNewFile();
                              FileOutputStream fos = new FileOutputStream(f);
                              fos.write(bytes, 0, bytes.length);
                              fos.flush();
                              fos.close();

                              showProgress(false);
                              LocalLoginActivity.this.finish();

                            } catch (IOException e1) {
                              e1.printStackTrace();
                            }
                          } else {
                            e.printStackTrace();
                          }
                        }
                      });
                    } else { //如果没有查询到对应用户的头像文件，则使用默认头像
                      showProgress(false);
                      LocalLoginActivity.this.finish();
                    }
                  } else {
                    showProgress(false);
                    Toast.makeText(LocalLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                  }
                }
              });

            }
//            startActivity(new Intent(LoginActivity.this, MainActivity.class));
          } else {
            showProgress(false);
            Toast.makeText(LocalLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
          }
        }
      });
    }
  }

  private boolean isPasswordValid(String password) {
    //TODO: Replace this with your own logic
    return password.length() > 4;
  }

  /**
   * Shows the progress UI
   */
  private void showProgress(final boolean show) {
    loadingview.setVisibility(show ? View.VISIBLE : View.GONE);
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
}

