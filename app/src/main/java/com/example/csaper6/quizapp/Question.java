package com.example.csaper6.quizapp;

/**
 * Created by csaper6 on 9/15/16.
 */
public class Question {
    private int questionId;
    private boolean isAnswerTrue;

    public Question(int questionId, boolean isAnswerTrue) {
        this.isAnswerTrue = isAnswerTrue;
        this.questionId = questionId;
    }

    /**
     * if answerGiven and answerTrue match, return true
     * otherwise return false
     * @param answerGiven the answer clicked
     * @return true if they got the question right
     */
    public boolean checkAnswer(boolean answerGiven){
        //returns true if answerGiven
        return isAnswerTrue == answerGiven;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public boolean isAnswerTrue() {
        return isAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        isAnswerTrue = answerTrue;
    }
}
