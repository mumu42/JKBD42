package com.example.administrator.jkbd.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.jkbd.Biz.ExamBiz;
import com.example.administrator.jkbd.Biz.IExamBiz;
import com.example.administrator.jkbd.bean.ExamInfo;
import com.squareup.picasso.Picasso;

import com.example.administrator.jkbd.ExamApplication;
import com.example.administrator.jkbd.R;
import com.example.administrator.jkbd.bean.Question;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/6/29.
 */

public class Exam extends AppCompatActivity {

    TextView tv_exam,tv_question,tv_item1,tv_item2,tv_item3,tv_item4,tv_id,tvload,tv_time;
    ImageView image;
    LinearLayout layoutload,layout_c,layout_d;
    CheckBox cb_a,cb_b,cb_c,cb_d;
    ProgressBar load;
    IExamBiz biz;
    Question questionuser;
    CheckBox[] cb=new CheckBox[4];

    boolean isLoadExamInfo=false;
    boolean isLoadQuestions =false;
    boolean isLoadQuestionslayotload=false;
    boolean isLoadExamInfolayoutload=false;

    LoadExamBroadcast mLoadExamBroadcast;
    LoadQuestionBroadcast mLoadQuestionBroadcast;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exam);
        mLoadExamBroadcast=new LoadExamBroadcast();
        mLoadQuestionBroadcast=new LoadQuestionBroadcast();
        questionuser=new Question();
        setListener();
        init();
        InitData();
        biz=new ExamBiz();
        loadData();
    }

    private void setListener() {
        registerReceiver(mLoadExamBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(mLoadQuestionBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));
    }

    private void loadData() {
        layoutload.setEnabled(false);
        layoutload.setVisibility(View.VISIBLE);
        tvload.setText("玩命加载中……");
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();
            }
        }).start();
    }

    private void InitData() {
        if(isLoadQuestionslayotload&&isLoadExamInfolayoutload){
            if(isLoadQuestions&&isLoadExamInfo){
                layoutload.setVisibility(View.GONE);
                ExamInfo examInfo=ExamApplication.getInstance().getExamInfos();
                if(examInfo!=null){
                    showData(examInfo);
                    initTimer(examInfo);
                }
                showQuestions(biz.getquestions());
            }
        }else {
            layoutload.setEnabled(true);
            layoutload.setVisibility(View.GONE);
            tvload.setText("数据加载失败，请重新加载！");
        }

    }

    private void initTimer(ExamInfo examInfo) {
        final long LiniTime=examInfo.getLimitTime()*1000*60;
        final long SumTime=LiniTime+System.currentTimeMillis();
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                final long min=(SumTime-System.currentTimeMillis())/1000/60;
                final long sec=(SumTime-System.currentTimeMillis())/1000%60;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_time.setText("剩余时间："+min+"分"+sec+"秒");
                        if(min==0&&sec==0){
                            tv_time.setVisibility(View.GONE);
                            ExamCommit(null);
                        }
                    }
                });
            }
        },0,1000);
    }


    private void showData(ExamInfo examInfo) {
        tv_exam.setText(examInfo.toString());
    }

    private void showQuestions(Question questions) {

        if(questions!=null){
            tv_id.setText(biz.getQuestionIndex());
            tv_question.setText(questions.getQuestion());
            if(questions.getUrl()!=null&&!questions.getUrl().equals(""))
            Picasso.with(Exam.this).load(questions.getUrl()).into(image);
            else
                image.setVisibility(View.GONE);
            tv_item1.setText(questions.getItem1());
            tv_item2.setText(questions.getItem2());
            tv_item3.setText(questions.getItem3());
            tv_item4.setText(questions.getItem4());
            if(questions.getItem3().equals("")){
                layout_c.setVisibility(View.GONE);
                cb_c.setVisibility(View.GONE);
            }else {
                cb_c.setVisibility(View.VISIBLE);
                layout_c.setVisibility(View.VISIBLE);
            }
            if(questions.getItem4().equals("")){
                cb_d.setVisibility(View.GONE);
                layout_d.setVisibility(View.GONE);
            }else {
                cb_d.setVisibility(View.VISIBLE);
                layout_d.setVisibility(View.VISIBLE);
            }
        }
        resetCheckBox();
        if(questions.getUserswer()!=null&&!questions.getUserswer().equals(""))
        {
            cb[Integer.parseInt(questions.getUserswer())-1].setChecked(true);
        }
    }

    private void init() {
        tv_time=(TextView)findViewById(R.id.tv_times);
        layout_c=(LinearLayout)findViewById(R.id.layout_c);
        layout_d=(LinearLayout)findViewById(R.id.layout_d);
        cb_a=(CheckBox)findViewById(R.id.cb_a);
        cb_b=(CheckBox)findViewById(R.id.cb_b);
        cb_c=(CheckBox)findViewById(R.id.cb_c);
        cb_d=(CheckBox)findViewById(R.id.cb_d);
        tv_id=(TextView)findViewById(R.id.tv_id);
        tv_item4=(TextView)findViewById(R.id.tv_item4);
        tv_item3=(TextView)findViewById(R.id.tv_item3);
        tv_item2=(TextView)findViewById(R.id.tv_item2);
        tv_item1=(TextView)findViewById(R.id.tv_item1);
        tv_question=(TextView)findViewById(R.id.tv_question);
        tv_exam=(TextView)findViewById(R.id.tv_examinfo);
        image=(ImageView)findViewById(R.id.images);
        layoutload=(LinearLayout)findViewById(R.id.layout_load);
        tvload=(TextView)findViewById(R.id.tv_load);
        load=(ProgressBar)findViewById(R.id.loadpicture);
        load.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        cb[0]=cb_a;
        cb[1]=cb_b;
        cb[2]=cb_c;
        cb[3]=cb_d;
        cb_a.setOnCheckedChangeListener(listener);
        cb_d.setOnCheckedChangeListener(listener);
        cb_c.setOnCheckedChangeListener(listener);
        cb_d.setOnCheckedChangeListener(listener);
    }

    CompoundButton.OnCheckedChangeListener listener=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            if (isChecked) {
                int useranswer = 0;
                switch (buttonView.getId()) {
                    case R.id.cb_a:
                        useranswer = 1;
                        break;
                    case R.id.cb_b:
                        useranswer = 2;
                        break;
                    case R.id.cb_c:
                        useranswer = 3;
                        break;
                    case R.id.cb_d:
                        useranswer = 4;
                        break;
                }
                if (useranswer > 0) {
                    for (CheckBox cbs : cb) {
                        cbs.setChecked(false);
                    }
                    cb[useranswer - 1].setChecked(true);
                }
            }
        }
    };

    public void preExam(View view) {
        saveanswer();
        showQuestions(biz.preQuestion());
    }


    public void nextExam(View view) {
        saveanswer();
        showQuestions(biz.nextQuestion());
    }

    private void saveanswer() {
        for(int i=0;i<cb.length;i++)
        {
            if(cb[i].isChecked())
            {
                biz.getquestions().setUserswer(Integer.toString(i+1));
                return;
            }
        }
    }

    private void resetCheckBox() {
        for (CheckBox cbs : cb) {
            cbs.setChecked(false);
        }
    }

    public void ExamCommit(View view) {
        saveanswer();
        View inflate=View.inflate(this,R.layout.layout_commit,null);
        TextView commit_id=(TextView)inflate.findViewById(R.id.commit_id);
        commit_id.setText("你的分数是\n"+biz.commitExam()+"分");
        AlertDialog.Builder ab=new AlertDialog.Builder(this);
        ab.setIcon(R.mipmap.exam_commit32x32)
                .setTitle("交卷")
                .setView(inflate)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        ab.create().show();
    }

    class LoadExamBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean issucces=intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            Log.e("LoadExamBroadcast","LoadExamBroadcast,issucces:"+issucces);
            if(issucces){
                isLoadExamInfo=true;
            }
            isLoadExamInfolayoutload=true;
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
            isLoadQuestionslayotload=true;
            InitData();
        }
    }

}