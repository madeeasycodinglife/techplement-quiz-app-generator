package com.madeeasy.dao.impl;

import com.madeeasy.dao.QuizDAO;
import com.madeeasy.model.Quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizDAOImpl implements QuizDAO {

    private final List<Quiz> quizzes = new ArrayList<>();

    @Override
    public void createQuiz(Quiz quiz) {
        this.quizzes.add(quiz);
    }

    @Override
    public void updateQuiz(Quiz updatedQuiz) {
        Quiz quiz = getQuizById(updatedQuiz.getQuizId());

        if (quiz != null) {
            this.quizzes.remove(quiz); // Remove the existing quiz
            this.quizzes.add(updatedQuiz); // Add the updated quiz
        }
    }

    @Override
    public void removeQuestionFromQuiz(String quizId, String questionId) {

    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return this.quizzes;
    }

    @Override
    public Quiz getQuizById(String quizId) {
        return this.quizzes.stream()
                .filter(quiz -> quiz.getQuizId().equals(quizId))
                .findFirst()
                .orElse(null);
    }
}
