package com.catfee.shareader.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.catfee.shareader.R;
import com.catfee.shareader.entity.Result;
import com.catfee.shareader.util.Config;
import com.catfee.shareader.util.OkHttpUtil;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;

    private Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Config.STATUS_OK://网络请求成功，但响应需要根据实际来操作
                    Result result = JSONObject.parseObject(msg.obj.toString(), Result.class);

                    if (result.getCode() == Config.STATUS_SUCCESS) {
                        //登陆成功
                        registerSuccess();
                    } else {
                        //登陆失败

                        registerFailure();
                    }
                    break;
                default://登陆失败

                    registerFailure();
                    break;
            }

        }
    };

    private void registerSuccess() {
        Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

        finish();
    }

    /**
     * 登陆失败提示
     */
    private void registerFailure() {
        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        initEvent();
    }

    /**
     * 控件初始化
     */
    private void initView() {
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
    }

    /**
     * 事件监听
     */
    private void initEvent() {
        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
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

                register();
            }
        });

    }

    /**
     * 用户注册
     */
    private void register() {
        Map<String, Object> param = new HashMap<>();
        param.put(Config.REQUEST_PARAMETER_USERNAME, edtUsername.getText().toString());
        param.put(Config.REQUEST_PARAMETER_PASSWORD, edtPassword.getText().toString());

        OkHttpUtil.post(Config.URL_REGISTER, param, handler);
    }
}
