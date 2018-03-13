package com.daoba.cloudbodytest.welcome.activity;

import com.daoba.cloudbodytest.R;
import com.daoba.cloudbodytest.base.activity.BaseActivity;

/**
 * 欢迎界面
 *
 * @author LongJian
 * @date 2018/3/13 0013
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onClick(int id) {
        super.onClick(id);
        switch (id) {
            case R.id.btnLogin:
                startActivity(LoginActivity.class);
                break;
            case R.id.btnRegister:

                break;
            default:
                break;
        }
    }
}
