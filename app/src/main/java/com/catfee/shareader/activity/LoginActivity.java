package com.catfee.shareader.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.catfee.shareader.R;
import com.catfee.shareader.entity.Result;
import com.catfee.shareader.util.Config;
import com.catfee.shareader.util.OkHttpUtil;
import com.catfee.shareader.util.PreferenceUtil;


import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private CheckBox checkRemember, checkAuoLogin;


    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Config.STATUS_OK://网络请求成功，但响应需要根据实际来操作
                    Result result = JSONObject.parseObject(msg.obj.toString(), Result.class);

                    if (result.getCode() == Config.STATUS_SUCCESS) {
                        //登陆成功
                        loginSuccess();
                    } else {
                        //登陆失败
                        Log.e(Config.LOGIN_ERROR_TAG,"请求成功但staus不是succ");
                        loginFailure();
                    }
                    break;
                default://登陆失败
                    Log.e(Config.LOGIN_ERROR_TAG,"请求失败");
                    //Log.e(Config.LOGIN_ERROR_TAG, )
                    loginFailure();
                    break;
            }

        }
    };

    private void loginSuccess() {
        Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));

        finish();
    }

    /**
     * 登陆失败提示
     */
    private void loginFailure() {
        Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initEvent();
        initBind();
    }


    /**
     * 控件初始化
     */
    private void initView() {
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);

        checkRemember = findViewById(R.id.check_remember);
        checkAuoLogin = findViewById(R.id.check_auto_login);
    }

    /**
     * 事件监听
     */
    private void initEvent() {
        //记住密码的选中事件监听
        checkRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    //在记住密码选项被取消的时候，同步要取消掉自动登录的选中
                    checkAuoLogin.setChecked(false);
                }
            }
        });

        //自动登录选择监听，但要注意与 checkRemember 的关联关系
        checkAuoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    //在自动登录选项呗选中的时候，同步要选中记住密码
                    checkRemember.setChecked(true);
                }
            }
        });

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //验证数据有效性
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                if (username.trim().length() == 0) {
                    edtUsername.setError("用户名不能为空");
                    return;
                }

                if (password.trim().length() == 0) {
                    edtPassword.setError("密码不能为空");
                    return;
                }


                //若有记住密码和自动登录选项，记录数据持久化存储
                if (checkRemember.isChecked()) {
                    //将自动保存密码选项配置保存为 true
                    PreferenceUtil.putBoolean(LoginActivity.this, Config.SETTTING_REMEMBER, true);
                    //持久化保存数据
                    PreferenceUtil.putString(LoginActivity.this, Config.DATA_USERNAME, username);
                    PreferenceUtil.putString(LoginActivity.this, Config.DATA_PASSWORD, password);

                    if (checkAuoLogin.isChecked()) {
                        PreferenceUtil.putBoolean(LoginActivity.this, Config.SETTTING_AUTO_LOGIN, true);
                    } else {
                        PreferenceUtil.putBoolean(LoginActivity.this, Config.SETTTING_AUTO_LOGIN, false);
                    }
                } else {
                    PreferenceUtil.putBoolean(LoginActivity.this, Config.SETTTING_REMEMBER, false);
                    PreferenceUtil.putBoolean(LoginActivity.this, Config.SETTTING_AUTO_LOGIN, false);
                }

                //登录操作
                login();
            }
        });

        findViewById(R.id.btn_to_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    /**
     * 自动注入及登陆
     */
    private void initBind() {
        if (PreferenceUtil.getBoolean(this, Config.SETTTING_REMEMBER)) {
            checkRemember.setChecked(true);

            edtUsername.setText(PreferenceUtil.getString(this, Config.DATA_USERNAME));
            edtPassword.setText(PreferenceUtil.getString(this, Config.DATA_PASSWORD));

            if (PreferenceUtil.getBoolean(this, Config.SETTTING_AUTO_LOGIN)) {
                checkAuoLogin.setChecked(true);

                login();
            }
        }
    }

    /**
     * 用户登陆
     */
    private void login() {
        Map<String, Object> param = new HashMap<>();
        param.put(Config.REQUEST_PARAMETER_USERNAME, edtUsername.getText().toString());
        param.put(Config.REQUEST_PARAMETER_PASSWORD, edtPassword.getText().toString());

        OkHttpUtil.post(Config.URL_LOGIN, param, handler);
    }
}
