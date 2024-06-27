package com.madeeasy.service;

import com.madeeasy.model.Question;

public interface QuizSession {
    public Question getNextQuestion();

    public Question getCurrentQuestion();

    public void nextQuestion();

    public void previousQuestion();

    public void submitAnswer(String questionId, String answer);

    public int calculateScore();

    public String getResults();
}
