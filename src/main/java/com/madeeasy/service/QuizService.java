package com.madeeasy.service;

import com.madeeasy.model.Question;
import com.madeeasy.model.Quiz;

import java.util.List;
import java.util.Scanner;

public interface QuizService {
    // Method to create a new quiz
    Quiz createQuiz(String quizName, String description);

    // Method to add a question to a quiz
    void addQuestionsToQuizByQuizId(String quizId);

    // Method to remove a question from a quiz
    void removeQuestionFromQuiz(String quizId, String questionId);

    // Method to get all quizzes
    List<Quiz> getAllQuizzes();

    // Method to get a specific quiz by its ID
    Quiz getQuizById(String quizId);

    // Method to update the details of a quiz
    void updateQuiz(String quizId, String newQuizName, String newDescription, Scanner scanner);


    // Method to start a quiz and return the first question
    Question startQuiz();


    // Method to get all questions from a specific quiz
    List<Question> getQuestionsByQuizId(String quizId);
}
