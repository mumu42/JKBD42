package com.example.administrator.jkbd.Biz;

import com.example.administrator.jkbd.dao.ExamDao;
import com.example.administrator.jkbd.dao.IExamDao;

/**
 * Created by Administrator on 2017/7/2.
 */

public class ExamBiz implements IExamBiz{
    IExamDao dao;

    public ExamBiz(){
        this.dao=new ExamDao();
    }
    @Override
    public void beginExam() {
        dao.loadExamInfo();
        dao.loadQoestionlista();
    }

    @Override
    public void nextQuestion() {

    }

    @Override
    public void preQuestion() {

    }

    @Override
    public void commitExam() {

    }
}
