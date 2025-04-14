package com.example.trivia.model;

public class Questions {
    private String question;
   private String ans;

    public Questions() {
    }

    public Questions(String question, String ans) {
        this.question = question;
        this.ans = ans;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    public String getQuestion() {
        return question;
    }

    public String getAns() {
        return ans;
    }
}
