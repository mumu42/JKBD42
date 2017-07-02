package com.example.administrator.jkbd.dao;

import android.service.media.MediaBrowserService;
import android.util.Log;

import com.example.administrator.jkbd.ExamApplication;
import com.example.administrator.jkbd.bean.ExamInfo;
import com.example.administrator.jkbd.bean.Other;
import com.example.administrator.jkbd.bean.Question;
import com.example.administrator.jkbd.util.OkHttpUtils;
import com.example.administrator.jkbd.util.ResultUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/7/2.
 */

public class ExamDao implements IExamDao{

    public void loadExamInfo(){
        OkHttpUtils<ExamInfo> examInfoOkHttpUtils=new OkHttpUtils<>(ExamApplication.getInstance());
        String url="http://101.251.196.90:8080/JztkServer/examInfo";
        examInfoOkHttpUtils.url(url).targetClass(ExamInfo.class).execute(new OkHttpUtils.OnCompleteListener<ExamInfo>() {
            @Override
            public void onSuccess(ExamInfo result) {
                Log.e("main","result:"+result);
                ExamApplication.getInstance().setExamInfos(result);

            }

            @Override
            public void onError(String error) {
                Log.e("main","error:"+error);
            }
        });
    }

    public void loadQoestionlista(){

        OkHttpUtils<String> urllist=new OkHttpUtils<>(ExamApplication.getInstance());
        String url1="http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
        urllist.url(url1).targetClass(String.class).execute(new OkHttpUtils.OnCompleteListener<String>() {
            @Override
            public void onSuccess(String jsresult) {
                Other result= ResultUtils.getListResultFromJson(jsresult);
                if(result!=null&&result.getError_code()==0){
                    List<Question> questionslist=result.getResult();
                    if(questionslist!=null&&questionslist.size()>0)
                        ExamApplication.getInstance().setList(questionslist);
                }
            }

            @Override
            public void onError(String error) {
                Log.e("main","error:"+error);

            }
        });

    }
}
