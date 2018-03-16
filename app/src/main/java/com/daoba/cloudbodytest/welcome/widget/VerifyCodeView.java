package com.daoba.cloudbodytest.welcome.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.daoba.cloudbodytest.R;
import com.daoba.cloudbodytest.base.listener.OnTextChangedListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 验证码 & 发送验证码的自定义控件
 *
 * @author LongJian
 * @date 2018/3/16 0016
 */
public class VerifyCodeView extends RelativeLayout {

    /**
     * 重发验证码的时间间隔
     */
    private static final int RESEND_SECONDS = 59;

    /**
     * 验证码发送/重发按钮
     */
    private AppCompatTextView tvVerifyCode;

    /**
     * 验证码编辑框
     */
    private AppCompatEditText etVerifyCode;

    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 倒计时的秒数，默认是59
     */
    private int seconds;

    /**
     * 发送验证码的接口
     */
    private OnSendVerifyCodeListener mOnSendVerifyCodeListener;

    /**
     * 构造器一
     *
     * @param context 上下文
     */
    public VerifyCodeView(Context context) {
        this(context, null);
    }

    /**
     * 构造器二
     *
     * @param context 上下文
     * @param attrs   参数集
     */
    public VerifyCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 构造器三
     *
     * @param context      上下文
     * @param attrs        参数集
     * @param defStyleAttr 默认参数
     */
    public VerifyCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        seconds = RESEND_SECONDS;
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_verify_code, this);
        tvVerifyCode = findViewById(R.id.tvVerifyCode);
        etVerifyCode = findViewById(R.id.etVerifyCode);
        etVerifyCode.addTextChangedListener(new OnTextChangedListener() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (s.length() == 0) {
                    etVerifyCode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.f);
                    return;
                }
                etVerifyCode.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30.f);
            }
        });
        tvVerifyCode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSendVerifyCodeListener != null) {
                    mOnSendVerifyCodeListener.onSendVerifyCode(getVerifyCode());
                    verifyCodeEnable();
                }
            }
        });
    }

    /**
     * 循环指定时间后，重置按钮的状态
     */
    private void verifyCodeEnable() {
        tvVerifyCode.setEnabled(false);
        tvVerifyCode.setText(mContext.getString(R.string.ViewVerifyCodeResend, seconds));
        tvVerifyCode.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvVerifyCode.setText(mContext.getString(R.string.ViewVerifyCodeResend, seconds));
                seconds--;
                if (seconds != 0) {
                    verifyCodeEnable();
                } else {
                    seconds = RESEND_SECONDS;
                    tvVerifyCode.setEnabled(true);
                    tvVerifyCode.setText(mContext.getString(R.string.ViewVerifyCodeSend));
                }
            }
        }, 1000);
    }

    /**
     * 设置发送验证码的接口
     *
     * @param onSendVerifyCodeListener 接口
     */
    public void setOnSendVerifyCodeListener(OnSendVerifyCodeListener onSendVerifyCodeListener) {
        this.mOnSendVerifyCodeListener = onSendVerifyCodeListener;
    }

    /**
     * 获取验证码内容
     *
     * @return 验证码
     */
    public String getText() {
        return etVerifyCode.getText().toString();
    }


    /**
     * 发送验证码的方法
     */
    public interface OnSendVerifyCodeListener {
        /**
         * 发送验证码
         *
         * @param verifyCode 验证码
         */
        void onSendVerifyCode(String verifyCode);
    }

    /**
     * 获取验证码
     *
     * @param size 验证码位数
     * @return 验证码
     */
    private String getVerifyCode(int size) {
        Random random = new Random();
        List<Integer> list = new ArrayList<>();
        while (list.size() < size) {
            int randomInt = random.nextInt(10);
            list.add(randomInt);
        }
        StringBuilder sb = new StringBuilder();
        for (Integer i : list) {
            sb.append(i);
        }
        return sb.toString();
    }

    /**
     * 获取验证码
     *
     * @return 验证码
     */
    private String getVerifyCode() {
        int randomInt = new Random().nextInt(3);
        if (randomInt == 0) {
            return getVerifyCode(4);
        } else if (randomInt == 1) {
            return getVerifyCode(5);
        } else {
            return getVerifyCode(6);
        }
    }
}
