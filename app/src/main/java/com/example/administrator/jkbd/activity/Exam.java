package com.example.administrator.jkbd.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.administrator.jkbd.ExamApplication;
import com.example.administrator.jkbd.R;

/**
 * Created by Administrator on 2017/6/29.
 */

public class Exam extends AppCompatActivity {

    TextView tv_exam,tv_question,tv_item1,tv_item2,tv_item3,tv_item4;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam);
        init();
        InitData();
    }

    private void InitData() {
        String e=ExamApplication.getInstance().getExamInfos().toString();
        if(e!=null)
        tv_exam.setText(e);
    }

    private void init() {
        tv_item4=(TextView)findViewById(R.id.tv_item4);
        tv_item3=(TextView)findViewById(R.id.tv_item3);
        tv_item2=(TextView)findViewById(R.id.tv_item2);
        tv_item1=(TextView)findViewById(R.id.tv_item1);
        tv_question=(TextView)findViewById(R.id.tv_question);
        tv_exam=(TextView)findViewById(R.id.tv_examinfo);
    }

}
