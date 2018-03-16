package com.daoba.cloudbodytest.welcome.activity;

import com.daoba.cloudbodytest.R;
import com.daoba.cloudbodytest.base.activity.BaseActivity;

/**
 * 注册的界面
 *
 * @author LongJian
 * @date 2018/3/16 0016
 */
public class RegisterActivity extends BaseActivity {
    @Override
    protected int bindLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void onClick(int id) {
        super.onClick(id);
        switch (id) {
            case R.id.btnNextStep:
                startActivity(VerifyActivity.class);
                break;
            default:
                break;
        }
    }
}
