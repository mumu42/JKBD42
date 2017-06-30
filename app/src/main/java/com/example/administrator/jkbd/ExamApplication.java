package com.example.administrator.jkbd;

import android.app.Application;
import android.util.Log;

import com.example.administrator.jkbd.bean.ExamInfo;
import com.example.administrator.jkbd.bean.Other;
import com.example.administrator.jkbd.util.OkHttpUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamApplication extends Application {
    ExamInfo examInfos;
    List<Other> list;
    private static ExamApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        InitData();
    }

    public static ExamApplication getInstance(){
        return instance;
    }

    public ExamInfo getExamInfos() {
        return examInfos;
    }

    public void setExamInfos(ExamInfo examInfos) {
        this.examInfos = examInfos;
    }

    public List<Other> getList() {
        return list;
    }

    public void setList(List<Other> list) {
        this.list = list;
    }

    private void InitData() {
        OkHttpUtils<ExamInfo> u=new OkHttpUtils<>(instance);
        String URL="http://101.251.196.90:8080/JztkServer/examInfo";
        u.url(URL).targetClass(ExamInfo.class).execute(new OkHttpUtils.OnCompleteListener<ExamInfo>() {
            @Override
            public void onSuccess(ExamInfo result) {
                Log.e("main","Result:"+result);
                examInfos=result;
            }

            @Override
            public void onError(String error) {
                Log.e("main","Error:"+error);
            }

        });
    }
}
