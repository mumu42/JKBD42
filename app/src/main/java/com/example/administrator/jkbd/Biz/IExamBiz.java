package com.example.administrator.jkbd.Biz;

import com.example.administrator.jkbd.bean.Question;

/**
 * Created by Administrator on 2017/7/2.
 */

public interface IExamBiz {
    void beginExam();
    Question getquestions();
    Question nextQuestion();
    Question preQuestion();
    String getQuestionIndex();
    void commitExam();
}
