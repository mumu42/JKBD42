package com.example.administrator.jkbd.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
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
        List<Question> questions=ExamApplication.getInstance().getList();
        if(questions!=null){
            showQuestions(questions);
        }
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

}
