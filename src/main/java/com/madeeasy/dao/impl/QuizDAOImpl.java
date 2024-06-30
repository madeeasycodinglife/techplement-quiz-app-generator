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
        Quiz existingQuiz = getQuizById(updatedQuiz.getQuizId());

        if (existingQuiz != null) {
            int index = this.quizzes.indexOf(existingQuiz);
            this.quizzes.set(index, updatedQuiz); // Update the existing quiz
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
