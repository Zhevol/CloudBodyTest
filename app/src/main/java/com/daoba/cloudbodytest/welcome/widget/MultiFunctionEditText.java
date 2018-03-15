package com.daoba.cloudbodytest.welcome.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.daoba.cloudbodytest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 多功能输入框
 * <p>
 * 目前所具有的的功能：获取验证码，显示隐藏密码
 *
 * @author LongJian
 * @date 2018/3/14 0014
 */
public class MultiFunctionEditText extends LinearLayoutCompat {
    private final static int STYLE_NONE = 1;
    private final static int STYLE_PASSWORD = 2;
    private final static int STYLE_VERIFY_CODE = 3;
    @BindView(R.id.etInputAction)
    AppCompatEditText etInputAction;
    @BindView(R.id.btnVerifyCode)
    AppCompatButton btnVerifyCode;
    @BindView(R.id.btnShowHidePwd)
    AppCompatImageButton btnShowHidePwd;
    @BindView(R.id.vBottomLine)
    View vBottomLine;

    public MultiFunctionEditText(@NonNull Context context) {
        this(context, null);
    }

    public MultiFunctionEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public MultiFunctionEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.multi_function_edit_text, this);
        ButterKnife.bind(this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MultiFunctionEditText);
        int mType = ta.getInt(R.styleable.MultiFunctionEditText_type, STYLE_NONE);
        ta.recycle();
        if (mType == STYLE_NONE) {
            btnVerifyCode.setVisibility(GONE);
            btnShowHidePwd.setVisibility(GONE);
        } else if (mType == STYLE_PASSWORD) {
            btnVerifyCode.setVisibility(GONE);
            btnShowHidePwd.setVisibility(VISIBLE);
        } else if (mType == STYLE_VERIFY_CODE) {
            btnVerifyCode.setVisibility(VISIBLE);
            btnShowHidePwd.setVisibility(GONE);
        } else {
            btnVerifyCode.setVisibility(GONE);
            btnShowHidePwd.setVisibility(GONE);
        }
        onClick();
    }

    @OnClick({R.id.btnShowHidePwd, R.id.btnVerifyCode})
    public void onClick() {
        Log.e("AAA", "点击了");
    }
}
