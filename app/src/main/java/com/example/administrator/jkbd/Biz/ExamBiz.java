package com.example.administrator.jkbd.Biz;

import com.example.administrator.jkbd.ExamApplication;
import com.example.administrator.jkbd.bean.Question;
import com.example.administrator.jkbd.dao.ExamDao;
import com.example.administrator.jkbd.dao.IExamDao;
import com.squareup.okhttp.internal.NamedRunnable;

import java.util.List;

/**
 * Created by Administrator on 2017/7/2.
 */

public class ExamBiz implements IExamBiz{
    IExamDao dao;
    int QuestionIndex=0;
    List<Question> questionList=null;

    @Override
    public Question getquestions() {
        if(questionList!=null)
        return questionList.get(QuestionIndex);
        else
            return null;
    }

    public ExamBiz(){
        this.dao=new ExamDao();
    }
    @Override
    public void beginExam() {
        QuestionIndex=0;
        dao.loadExamInfo();
        dao.loadQoestionlista();
        questionList=ExamApplication.getInstance().getList();
    }

    @Override
    public Question nextQuestion() {
        if(questionList!=null&&QuestionIndex<questionList.size()-1){
            QuestionIndex++;
            return questionList.get(QuestionIndex);
        }
        else
        return null;

    }

    @Override
    public Question preQuestion() {
        if(questionList!=null&&QuestionIndex>0){
            QuestionIndex--;
            return questionList.get(QuestionIndex);
        }
        else
        return null;
    }

    @Override
    public String getQuestionIndex() {
        return QuestionIndex+1+".";
    }

    @Override
    public void commitExam() {

    }
}
