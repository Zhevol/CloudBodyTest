package com.daoba.cloudbodytest.welcome.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.daoba.cloudbodytest.R;
import com.daoba.cloudbodytest.base.listener.OnTextChangedListener;

/**
 * 显示隐藏密码的控件
 *
 * @author LongJian
 * @date 2018/3/16 0016
 */
public class PasswordView extends RelativeLayout {

    /**
     * 显示/隐藏密码的控件
     */
    private AppCompatCheckBox cbPassword;

    /**
     * 密码编辑框
     */
    private AppCompatEditText etPassword;

    /**
     * 默认是 false - 密文显示密码，否则 true - 明文显示密码
     */
    private boolean mIsShowPassword;

    /**
     * 构造器一
     *
     * @param context 上下文
     */
    public PasswordView(Context context) {
        this(context, null);
    }

    /**
     * 构造器二
     *
     * @param context 上下文
     * @param attrs   属性集
     */
    public PasswordView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 构造器三
     *
     * @param context      上下文
     * @param attrs        属性集
     * @param defStyleAttr 默认属性
     */
    public PasswordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_password, this);
        cbPassword = findViewById(R.id.cbPassword);
        etPassword = findViewById(R.id.etPassword);
        mIsShowPassword = cbPassword.isChecked();
        setTransformationMethod();
        etPassword.addTextChangedListener(new OnTextChangedListener() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (s.length() == 0) {
                    etPassword.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.f);
                    return;
                }
                etPassword.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30.f);
            }
        });
        cbPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mIsShowPassword = isChecked;
                setTransformationMethod();
            }
        });
    }

    /**
     * 设置密码的可视化状态
     */
    private void setTransformationMethod() {
        if (mIsShowPassword) {
            // 可视密码输入
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            // 非可视密码状态
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        // 移动光标
        etPassword.setSelection(etPassword.getText().length());
    }

    /**
     * 获取密码内容
     *
     * @return 密码内容
     */
    public String getText() {
        return etPassword.getText().toString();
    }
}
