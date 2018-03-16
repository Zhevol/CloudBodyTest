package com.daoba.cloudbodytest.welcome.activity;

import android.view.View;
import android.widget.RadioGroup;

import com.daoba.cloudbodytest.R;
import com.daoba.cloudbodytest.base.activity.BaseActivity;
import com.daoba.cloudbodytest.welcome.widget.NormalInputView;
import com.daoba.cloudbodytest.welcome.widget.PasswordView;
import com.daoba.cloudbodytest.welcome.widget.VerifyCodeView;

/**
 * 登录界面
 *
 * @author LongJian
 * @date 2018/3/13 0013
 */
public class LoginActivity extends BaseActivity {

    private RadioGroup rgLogin;
    private NormalInputView vTellNum;
    private PasswordView vPassword;
    private VerifyCodeView vVerifyCode;

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void bindViews() {
        super.bindViews();
        rgLogin = findViewById(R.id.rgLogin);
        vTellNum = findViewById(R.id.vTellNum);
        vPassword = findViewById(R.id.vPassword);
        vVerifyCode = findViewById(R.id.vVerifyCode);
    }

    @Override
    protected void bindListeners() {
        super.bindListeners();
        rgLogin.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbLogin1:
                        vVerifyCode.setVisibility(View.GONE);
                        vPassword.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rbLogin2:
                        vVerifyCode.setVisibility(View.VISIBLE);
                        vPassword.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });
        vVerifyCode.setOnSendVerifyCodeListener(new VerifyCodeView.OnSendVerifyCodeListener() {
            @Override
            public void onSendVerifyCode(String verifyCode) {

            }
        });
    }
}
