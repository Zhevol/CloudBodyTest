package com.daoba.cloudbodytest.base.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.daoba.cloudbodytest.R;
import com.daoba.cloudbodytest.base.application.CbtApplication;
import com.daoba.cloudbodytest.base.config.IntentConfig;

import java.io.Serializable;

import static android.view.Window.FEATURE_NO_TITLE;

/**
 * Activity 的基类，抽取了一些基础的方法，方便可控化的管理。其他 Activity 都应继承此类
 *
 * @author LongJian
 * @date 2018/3/13 0013
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doBeforeSetContent();
        setContentView(bindLayoutId());
        bindViews();
        bindListeners();
        View rootView = findViewById(android.R.id.content);
        setButtonOnClickListener(rootView);
        bindData(savedInstanceState);
        CbtApplication.getInstance().addActivity(this);
    }

    /**
     * 在设置布局之前执行的方法
     */
    protected void doBeforeSetContent() {
        supportRequestWindowFeature(FEATURE_NO_TITLE);
    }

    /**
     * 返回一个用于显示界面布局的id
     *
     * @return 界面的布局id
     */
    protected abstract int bindLayoutId();

    /**
     * 设置头部状态栏
     * 如果要在Activity中增加控件或者新的View，则重写并添加{@code super.initView();}即可
     */
    protected void bindViews() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //半透明头部状态栏，底部导航栏   布局在状态栏，导航栏下方
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    /**
     * 初始化事件监听器
     */
    protected void bindListeners() {

    }

    /**
     * 查找Button、ImageButton并设置单击监听器
     *
     * @param view 要设置监听器的view
     */
    private void setButtonOnClickListener(View view) {
        //遍历view里面所有的Button和ImageButton
        if (view instanceof AppCompatImageButton || view instanceof ImageButton
                || view instanceof AppCompatButton || view instanceof Button) {
            view.setOnClickListener(this);
        } else if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View childView = viewGroup.getChildAt(i);
                setButtonOnClickListener(childView);
            }
        }
    }

    /**
     * 初始化数据，并且显示到界面上`
     */
    protected void bindData(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnFinishActivity:
                //处理共同的返回按钮的操作
                finishActivity();
                break;
            default:
                //如果单击的不是返回按钮，则还是由子类去做处理
                onClick(v.getId());
                break;
        }
    }

    /**
     * 退出当前Activity
     * 如果要修改返回按钮的相关执行过程，则应重写此方法
     */
    protected void finishActivity() {
        finish();
    }

    /**
     * 单击事件在这里面处理
     *
     * @param id 单击控件的id
     */
    protected void onClick(int id) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    // --------------------------------隐藏输入法---------------------------------- //

    /**
     * 获取屏幕触摸事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                //隐藏软键盘
                IBinder token = view.getWindowToken();
                if (token != null) {
                    InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (manager != null) {
                        manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判定是否需要隐藏软键盘
     *
     * @param v  当前的拥有焦点的 View
     * @param ev 事件
     * @return 是否需要隐藏
     */
    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            return !(ev.getX() > left && ev.getX() < right && ev.getY() > top
                    && ev.getY() < bottom);
        }
        return false;
    }

    // --------------------------------跳转的方法---------------------------------- //

    /**
     * 跳转 Activity，默认不关闭当前的 Activity
     *
     * @param cls 要跳转到的目标 Activity
     */
    protected void startActivity(Class<?> cls) {
        startActivity(cls, false);
    }

    /**
     * 跳转 Activity，并传递一个 Bundle 到目标Activity
     *
     * @param cls        要跳转到的目标 Activity
     * @param stringData 要携带的 String 数据
     */
    protected void startActivity(Class<?> cls, String stringData) {
        Intent intent = new Intent(this, cls);
        if (!TextUtils.isEmpty(stringData)) {
            intent.putExtra(IntentConfig.STRING_DATA, stringData);
        }
        startActivity(intent);
    }

    /**
     * * 跳转 Activity，并传递一个 Bundle 到目标Activity
     *
     * @param cls        要跳转到的目标 Activity
     * @param intentData 要携带的数据对象
     */
    protected void startActivity(Class<?> cls, Serializable intentData) {
        Intent intent = new Intent(this, cls);
        if (intentData != null) {
            intent.putExtra(IntentConfig.SERIAL_DATA, intentData);
        }
        startActivity(intent);
    }

    /**
     * 跳转 Activity
     *
     * @param cls    要跳转到的目标 Activity
     * @param finish 是否 finish 当前的 Activity
     */
    protected void startActivity(Class<?> cls, boolean finish) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
        if (finish) {
            finish();
        }
    }
}
