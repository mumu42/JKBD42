package com.example.administrator.jkbd.View;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.jkbd.ExamApplication;
import com.example.administrator.jkbd.R;
import com.example.administrator.jkbd.bean.Question;

import java.util.List;

/**
 * Created by Administrator on 2017/7/4.
 */

public class QuestionAdapter extends BaseAdapter {
    Context mcontext;
    List<Question> questionList;

    public QuestionAdapter(Context context){
        mcontext=context;
        questionList= ExamApplication.getInstance().getList();
    }
    @Override
    public int getCount() {
        return questionList==null?0:questionList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=View.inflate(mcontext, R.layout.item_question,null);
        TextView tvno=(TextView)view.findViewById(R.id.tv_no);
        ImageView ivQuestion=(ImageView)view.findViewById(R.id.iv_question);
        if(questionList.get(position).getUserswer()!=null&&!questionList.get(position).getUserswer().equals("")){
            ivQuestion.setImageResource(R.mipmap.answer24x24);
        }else {
            ivQuestion.setImageResource(R.mipmap.ques24x24);
        }
        tvno.setText("第"+(position+1)+"题");
        return view;
    }
}
