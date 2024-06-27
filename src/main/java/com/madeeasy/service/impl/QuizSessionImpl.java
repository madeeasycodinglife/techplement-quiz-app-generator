package com.madeeasy.service.impl;

import com.madeeasy.model.Question;
import com.madeeasy.service.QuizSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class QuizSessionImpl implements QuizSession {
    private final String quizId;
    private final List<Question> questions;
    private final Map<String, String> userResponses;
    private int currentQuestionIndex;

    public QuizSessionImpl(String quizId, List<Question> questions) {
        this.quizId = quizId;
        this.questions = questions;
        this.userResponses = new HashMap<>();
        this.currentQuestionIndex = 0;
    }

    @Override
    public Question getNextQuestion() {
        if (currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex++);
        }
        return null;
    }

    @Override
    public Question getCurrentQuestion() {
        if (currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex);
        }
        return null;
    }

    @Override
    public void nextQuestion() {
        if (currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++;
        }
    }

    @Override
    public void previousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
        }
    }

    @Override
    public void submitAnswer(String questionId, String answer) {
        userResponses.put(questionId, answer);
    }

    @Override
    public int calculateScore() {
        int score = 0;
        for (Question question : questions) {
            String userAnswer = userResponses.get(question.getQuestion());
            if (question.getCorrectAnswer().equals(userAnswer)) {
                score += question.getPoints();
            }
        }
        return score;
    }

    @Override
    public String getResults() {
        StringBuilder results = new StringBuilder();
        results.append("-------------------- Results :- -------------------\n");
        for (Question question : questions) {
            String userAnswer = userResponses.get(question.getQuestion());
            results.append("Question : ").append(question.getQuestion()).append("\n");
            results.append("Your Answer : ").append(userAnswer).append("\n");
            results.append("Correct Answer : ").append(question.getCorrectAnswer()).append("\n");
            results.append("Explanation : ").append(question.getExplanation()).append("\n");
            results.append("Points : ").append(question.getCorrectAnswer().equals(userAnswer) ? question.getPoints() : 0).append("\n\n");
        }
        results.append("Total Score : ").append(calculateScore()).append("\n");
        return results.toString();
    }
}
