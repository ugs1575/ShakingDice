package com.example.woogyeongseo.shakingdice;



public class Question {

    private int questionNum;
    private String question;

    public Question(int questionNum, String question)
    {
        this.questionNum = questionNum;
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

}
