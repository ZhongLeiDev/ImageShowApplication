package com.example.zl.locallogin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.zl.imageshowapplication.R;
import com.example.zl.imageshowapplication.bean.bcy.retro.ResultVO;
import com.example.zl.imageshowapplication.utils.AvatarSelectUtil;
import com.example.zl.imageshowapplication.utils.BcyActivityManager;
import com.example.zl.locallogin.bean.ISUser;
import com.example.zl.locallogin.bean.LocalError;
import com.example.zl.locallogin.util.LocalUtil;

import java.io.File;
import java.util.concurrent.ExecutionException;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class LocalLoginActivity extends AppCompatActivity {
  private AutoCompleteTextView mUsernameView;
  private EditText mPasswordView;
  private ImageView avatar;
  private LottieAnimationView loadingview;

  private boolean isAvatarExist = false;
  private static Handler handler=new Handler(); //通过 Handler 的 POST 方法改变 UI

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    //将当前 Activity 纳入管理
    BcyActivityManager.getActivityManager().addActivity(this);

    if (LocalUserHandle.currentUser() != null) {
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
      doLogin(username, password, "");
    }
  }

  /**
   * 登录
   * @param userName  用户名
   * @param passWord  用户密码
   * @param token 用户token
   */
  private void doLogin(String userName, String passWord, String token) {
    LocalUserHandle.doLogin(userName, passWord, token, new LocalCallback<ResultVO<ISUser>>() {
      @Override
      public void done(ResultVO<ISUser> isUserResultVO, LocalError e) {
        if (e == null) {
          LocalUtil.saveCurrentUser(isUserResultVO.getData(), LocalLoginActivity.this);
          LocalUserHandle.setCurrentUser(isUserResultVO.getData());
          Log.i("LOGINActivity", "UserGet->" + isUserResultVO.getData().toString());
          if (isAvatarExist) {  //如果头像存在，则登陆成功直接返回
            showProgress(false);
            LocalLoginActivity.this.finish();
          } else {  //若头像不存在，则从网络加载头像文件，并将头像文件存储在本地
            new Thread(() -> {
              try {
                File file = Glide.with(LocalLoginActivity.this)
                        .load(isUserResultVO.getData().getAvatarUrl())
                        .downloadOnly(100,100)
                        .get();
                boolean isok = AvatarSelectUtil.copyAvatar(file.getPath(),
                        AvatarSelectUtil.buildAvatarPath(
                                isUserResultVO.getData().getUserName()));
                if (isok) {
                  handler.post(() -> {
                    showProgress(false);
                    LocalLoginActivity.this.finish();
                  });
                } else {
                  handler.post(() -> {
                    showProgress(false);
                    Toast.makeText(LocalLoginActivity.this, "头像文件存储失败！", Toast.LENGTH_SHORT).show();
                  });

                }
              } catch (InterruptedException | ExecutionException e1) {
                e1.printStackTrace();
                handler.post(() -> {
                  showProgress(false);
                  Toast.makeText(LocalLoginActivity.this, e1.getMessage(), Toast.LENGTH_SHORT).show();
                });
              }
            }).start();
          }
        } else {
          showProgress(false);
          Toast.makeText(LocalLoginActivity.this, e.getMsg(), Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  private boolean isPasswordValid(String password) {
    //TODO: Replace this with your own logic
    return password.length() > 6;
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
  }

  @Override
  protected void onResume() {
    super.onResume();
  }
}

