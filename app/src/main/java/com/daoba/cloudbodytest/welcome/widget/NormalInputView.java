package com.daoba.cloudbodytest.welcome.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.daoba.cloudbodytest.R;
import com.daoba.cloudbodytest.base.listener.OnTextChangedListener;

/**
 * 普通的输入框
 *
 * @author LongJian
 * @date 2018/3/16 0016
 */
public class NormalInputView extends RelativeLayout {

    /**
     * 顶部的提示文本
     */
    private AppCompatTextView tvNormalInput;

    /**
     * 底部的线
     */
    private View vNormalInput;

    /**
     * 输入框
     */
    private AppCompatEditText etNormalInput;

    public NormalInputView(Context context) {
        this(context, null);
    }

    public NormalInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NormalInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_normal_input, this);
        tvNormalInput = findViewById(R.id.tvNormalInput);
        vNormalInput = findViewById(R.id.vNormalInput);
        etNormalInput = findViewById(R.id.etNormalInput);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NormalInputView);
        String titleText = ta.getString(R.styleable.NormalInputView_titleText);
        int titleColor = ta.getColor(R.styleable.NormalInputView_titleColor, ContextCompat.getColor(context, R.color.colorWhitePure));
        String hintText = ta.getString(R.styleable.NormalInputView_hintText);
        int bottomLineColor = ta.getColor(R.styleable.NormalInputView_bottomLineColor, ContextCompat.getColor(context, R.color.colorWhite33));
        int maxLength = ta.getInt(R.styleable.NormalInputView_maxLength, Integer.MAX_VALUE);
        ta.recycle();
        tvNormalInput.setText(titleText);
        tvNormalInput.setTextColor(titleColor);
        etNormalInput.setHint(hintText);
        etNormalInput.setRawInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
        etNormalInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        vNormalInput.setBackgroundColor(bottomLineColor);

        etNormalInput.addTextChangedListener(new OnTextChangedListener() {
            @Override
            public void afterTextChanged(Editable s) {
                super.afterTextChanged(s);
                if (s.length() == 0) {
                    etNormalInput.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18.f);
                    return;
                }
                etNormalInput.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30.f);
            }
        });
    }

    /**
     * 获取文本输入框的文本
     *
     * @return 文本
     */
    public String getText() {
        return etNormalInput.getText().toString();
    }
}
