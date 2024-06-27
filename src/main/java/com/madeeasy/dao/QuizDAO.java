package com.madeeasy.dao;

import com.madeeasy.model.Question;
import com.madeeasy.model.Quiz;

import java.util.List;

public interface QuizDAO {
    // Method to create a new quiz
    void createQuiz(Quiz quiz);

    public void updateQuiz(Quiz updatedQuiz);

    // Method to remove a question from a quiz
    void removeQuestionFromQuiz(String quizId, String questionId);

    // Method to get all quizzes
    List<Quiz> getAllQuizzes();

    // Method to get a specific quiz by its ID
    Quiz getQuizById(String quizId);

}
