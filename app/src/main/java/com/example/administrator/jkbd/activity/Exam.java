package com.example.administrator.jkbd.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jkbd.Biz.ExamBiz;
import com.example.administrator.jkbd.Biz.IExamBiz;
import com.example.administrator.jkbd.bean.ExamInfo;
import com.squareup.picasso.Picasso;

import com.example.administrator.jkbd.ExamApplication;
import com.example.administrator.jkbd.R;
import com.example.administrator.jkbd.bean.Question;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public class Exam extends AppCompatActivity {

    TextView tv_exam,tv_question,tv_item1,tv_item2,tv_item3,tv_item4,tv_id;
    ImageView image;
    IExamBiz biz;
    boolean isLoadExamInfo=false;
    boolean isLoadQuestions =false;
    LoadExamBroadcast mLoadExamBroadcast;
    LoadQuestionBroadcast mLoadQuestionBroadcast;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam);
        mLoadExamBroadcast=new LoadExamBroadcast();
        mLoadQuestionBroadcast=new LoadQuestionBroadcast();
        setListener();
        init();
        InitData();
        loadData();
    }

    private void setListener() {
        registerReceiver(mLoadExamBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(mLoadQuestionBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));
    }

    private void loadData() {
        biz=new ExamBiz();
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();
            }
        }).start();
    }

    private void InitData() {
        String e=ExamApplication.getInstance().getExamInfos().toString();
        if(e!=null)
        tv_exam.setText(e);
        List<Question> questions=ExamApplication.getInstance().getList();
        if(questions!=null){
            showQuestions(questions);
        }
        if(isLoadQuestions&&isLoadExamInfo){
            ExamInfo examInfo=ExamApplication.getInstance().getExamInfos();
            if(examInfo!=null){
                showData(examInfo);
            }
            List<Question> questionlist=ExamApplication.getInstance().getList();
            if(questionlist!=null){
                showQuestions(questionlist);
            }
        }
    }

    private void showExam(List<Question> questionlist) {
    }

    private void showData(ExamInfo examInfo) {
        tv_exam.setText(examInfo.toString());
    }

    private void showQuestions(List<Question> questions) {
        Question questionlist=questions.get(0);
        if(questionlist!=null){
            tv_id.setText(questionlist.getId());
            tv_question.setText(questionlist.getQuestion());
            Picasso.with(Exam.this).load(questionlist.getUrl()).into(image);
            tv_item1.setText(questionlist.getItem1());
            tv_item2.setText(questionlist.getItem2());
            tv_item3.setText(questionlist.getItem3());
            tv_item4.setText(questionlist.getItem4());
        }

    }

    private void init() {
        tv_id=(TextView)findViewById(R.id.tv_id);
        tv_item4=(TextView)findViewById(R.id.tv_item4);
        tv_item3=(TextView)findViewById(R.id.tv_item3);
        tv_item2=(TextView)findViewById(R.id.tv_item2);
        tv_item1=(TextView)findViewById(R.id.tv_item1);
        tv_question=(TextView)findViewById(R.id.tv_question);
        tv_exam=(TextView)findViewById(R.id.tv_examinfo);
        image=(ImageView)findViewById(R.id.images);
    }

    class LoadExamBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean issucces=intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            Log.e("LoadExamBroadcast","LoadExamBroadcast,issucces:"+issucces);
            if(issucces){
                isLoadExamInfo=true;
            }
            InitData();
        }
    }

    class LoadQuestionBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean issucces=intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            Log.e("LoadQuestionBroadcast","LoadQuestionBroadcast,issucces:"+issucces);
            if(issucces){
                isLoadQuestions=true;
            }
            InitData();
        }
    }

}
