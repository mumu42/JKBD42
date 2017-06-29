package com.example.administrator.jkbd.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.administrator.jkbd.R;
import com.example.administrator.jkbd.bean.ExamInfo;
import com.example.administrator.jkbd.util.OkHttpUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void exit(View view) {
        AlertDialog.Builder bd=new AlertDialog.Builder(MainActivity.this);
        bd.setMessage("确认退出吗？");
        bd.setTitle("提示");
        bd.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        bd.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        bd.create().show();
    }

    public void text(View view) {
        OkHttpUtils<ExamInfo> u=new OkHttpUtils<>(getApplicationContext());
        String URL="http://101.251.196.90:8080/JztkServer/examInfo";
        u.url(URL).targetClass(ExamInfo.class).execute(new OkHttpUtils.OnCompleteListener<ExamInfo>() {
            @Override
            public void onSuccess(ExamInfo result) {
                Log.e("main","Result:"+result);
            }

            @Override
            public void onError(String error) {
                Log.e("main","Error:"+error);
            }

        });
        Intent it=new Intent(MainActivity.this,Exam.class);
        startActivity(it);
    }
}
