package com.example.administrator.jkbd;

import android.app.Application;
import android.util.Log;

import com.example.administrator.jkbd.Biz.ExamBiz;
import com.example.administrator.jkbd.Biz.IExamBiz;
import com.example.administrator.jkbd.bean.ExamInfo;
import com.example.administrator.jkbd.bean.Other;
import com.example.administrator.jkbd.bean.Question;
import com.example.administrator.jkbd.util.OkHttpUtils;
import com.example.administrator.jkbd.util.ResultUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamApplication extends Application {
    ExamInfo examInfos;
    List<Question> list;
    private static ExamApplication instance;
    IExamBiz biz;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        biz=new ExamBiz();
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

    public List<Question> getList() {
        return list;
    }

    public void setList(List<Question> list) {
        this.list = list;
    }

    private void InitData() {
        OkHttpUtils<ExamInfo> u = new OkHttpUtils<>(instance);
        String URL = "http://101.251.196.90:8080/JztkServer/examInfo";
        u.url(URL).targetClass(ExamInfo.class).execute(new OkHttpUtils.OnCompleteListener<ExamInfo>() {
            @Override
            public void onSuccess(ExamInfo result) {
                Log.e("main", "Result:" + result);
                examInfos = result;
            }

            @Override
            public void onError(String error) {
                Log.e("main", "Error:" + error);
            }

        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils<ExamInfo> u = new OkHttpUtils<>(instance);
                String URL = "http://101.251.196.90:8080/JztkServer/examInfo";
                u.url(URL).targetClass(ExamInfo.class).execute(new OkHttpUtils.OnCompleteListener<ExamInfo>() {
                    @Override
                    public void onSuccess(ExamInfo result) {
                        Log.e("main", "Result:" + result);
                        examInfos = result;
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main", "Error:" + error);
                    }
                });
                OkHttpUtils<String> utils1=new OkHttpUtils<>(instance);
                String URL2="http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
                utils1.url(URL2).targetClass(String.class).execute(new OkHttpUtils.OnCompleteListener<String>() {
                    @Override
                    public void onSuccess(String jsonStr) {
                        Other result=new Other();
                        result= ResultUtils.getListResultFromJson(jsonStr);
                        if(result!=null&&result.getError_code()==0){
                            List<Question> questionslist=result.getResult();
                            if(questionslist!=null)
                                list=questionslist;
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error:"+error);
                    }
                });
                biz.beginExam();
            }
        }).start();


    }
}
