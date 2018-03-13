package com.daoba.cloudbodytest.base.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.Map;

/**
 * Soap 请求的管理者
 *
 * @author LongJian
 * @date 2018/3/8 0008
 */

public class SoapHttpManager {

    /**
     * SoapHttpManager
     */
    private static class SoapHttpManagerHolder {
        /**
         * 单例
         */
        final static SoapHttpManager INSTANCE = new SoapHttpManager();
    }

    /**
     * 获取 SoapHttpManager 单例
     *
     * @return SoapHttpManager 单例
     */
    public static SoapHttpManager getInstance() {
        return SoapHttpManagerHolder.INSTANCE.setShowDialog(true);
    }

    /**
     * 初始化一个 Dialog
     */
    private ProgressDialog mProgressDialog;

    /**
     * 展示 Dialog
     */
    private boolean showDialog;

    /**
     * 获取 showDialog 的值
     *
     * @return true-展示 Dialog,false-不展示 Dialog
     */
    private boolean isShowDialog() {
        return showDialog;
    }

    /**
     * 设置是否展示 Dialog
     *
     * @param showDialog true-展示 Dialog,false-不展示 Dialog
     */
    public SoapHttpManager setShowDialog(boolean showDialog) {
        SoapHttpManagerHolder.INSTANCE.showDialog = showDialog;
        return SoapHttpManagerHolder.INSTANCE;
    }

    /**
     * 发送网络请求
     */
    public <T> void doRequest(final OnRequestListener<T> listener) {
        if (isShowDialog()) {
            mProgressDialog = ProgressDialog.show(listener.domainContext(), "", "请稍候…", true);
        }
        SoapHttp.getInstance()
                .setRequestUrl("http://118.126.108.178/FTCloudService.php")
                .setNameSpace("urn:FtCloudService")
                .setMethodName(listener.requestMethod())
                .setParam(ParamUtil.buildParams(listener.requestParams()))
                .setOnSoapHttpListener(new SoapHttp.OnSoapHttpListener() {
                    @Override
                    public void onSuccess(String result) {
                        Log.e("MainActivity", "请求的结果" + result);
                        closeDialog();
                        listener.onSuccess(parseObject(result, listener.getTClass()));
                    }

                    @Override
                    public void onFailed(Exception e) {
                        Log.e("MainActivity", "错误信息" + e.toString());
                        closeDialog();
                        Toast.makeText(listener.domainContext(), "请求异常，请重试", Toast.LENGTH_SHORT).show();
                    }
                })
                .doSoapHttpRequest();
    }


    /**
     * 关闭 Dialog
     */
    private void closeDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    /**
     * 解析只包含一个对象的Json字符串
     *
     * @param jsonRes 源Json字符串
     * @param tClass  解析出来的对象的class
     * @param <T>     解析出来的对象的泛型标记
     * @return 解析出来的对象
     */
    private <T> T parseObject(String jsonRes, Class<T> tClass) {
        try {
            return new Gson().fromJson(jsonRes, tClass);
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    /**
     * 发送请求的接口
     *
     * @param <T> 泛型
     */
    public interface OnRequestListener<T> {

        /**
         * 请求发生的上下文内容
         *
         * @return 上下文
         */
        Context domainContext();

        /**
         * 请求的地址
         *
         * @return 请求的地址
         */
        String requestMethod();

        /**
         * 组装请求的参数
         *
         * @return 请求的参数
         */
        Map<String, String> requestParams();

        /**
         * 获取泛型的 Class 类型
         *
         * @return Class
         */
        Class<T> getTClass();

        /**
         * 请求成功的返回
         *
         * @param t 泛型对象
         */
        void onSuccess(T t);
    }

}
