package com.madeeasy.service.impl;

import com.madeeasy.color.Color;
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

//    @Override
//    public Question getNextQuestion() {
//        if (currentQuestionIndex < questions.size()) {
//            return questions.get(currentQuestionIndex++);
//        }
//        return null;
//    }
//
//    @Override
//    public Question getCurrentQuestion() {
//        if (currentQuestionIndex < questions.size()) {
//            return questions.get(currentQuestionIndex);
//        }
//        return null;
//    }
//
//    @Override
//    public void nextQuestion() {
//        if (currentQuestionIndex <= questions.size() - 1) {
//            currentQuestionIndex++;
//        }else {
//            currentQuestionIndex++;
//        }
//    }
//
//    @Override
//    public void previousQuestion() {
//        if (currentQuestionIndex > 0) {
//            currentQuestionIndex--;
//        }else {
//            currentQuestionIndex = questions.size();
//        }
//    }

    //---------------------------------------------------------



    @Override
    public Question getNextQuestion() {
        if (currentQuestionIndex < questions.size() - 1) {
            return questions.get(++currentQuestionIndex);
        } else {
            currentQuestionIndex = 0; // Wrap around to the first question
            return questions.get(currentQuestionIndex);
        }
    }

    @Override
    public Question getCurrentQuestion() {
        if (currentQuestionIndex >= 0 && currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex);
        }
        return null;
    }

    @Override
    public void nextQuestion() {
        if (currentQuestionIndex <= questions.size() - 1) {
            currentQuestionIndex++;
        } else {
            currentQuestionIndex = 0; // Wrap around to the first question
        }
    }

    @Override
    public void previousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
        } else {
            currentQuestionIndex = questions.size() - 1; // Wrap around to the last question
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
        System.out.println(); // Empty line for separation
        System.out.println(); // Empty line for separation
        System.out.println(); // Empty line for separation
        results.append("-------------------- Results :- -------------------\n");

        // Iterate through questions with an index for question numbering
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String userAnswer = userResponses.get(question.getQuestion());
            boolean isCorrect = question.getCorrectAnswer().equals(userAnswer);

            int questionNumber = i + 1; // Calculate question number (1-based index)

            results.append("Question ").append(questionNumber).append(" : ").append(question.getQuestion()).append("\n");

            // Append "Your Answer : " with appropriate color
            results.append(isCorrect ? Color.BOLD_GREEN : Color.BOLD_RED)
                    .append("Your Answer : ")
                    .append(userAnswer)
                    .append(Color.RESET); // Reset color after the answer

            results.append("\n");
            // Append "Correct Answer : " with green color
            results.append(Color.BOLD_GREEN)
                    .append("Correct Answer : ")
                    .append(question.getCorrectAnswer())
                    .append(Color.RESET); // Reset color after the correct answer

            results.append("\n");
            results.append("Explanation : ").append(question.getExplanation()).append("\n");
            results.append("Points : ");

            // Append points in purple color
            results.append(Color.BOLD_PURPLE)
                    .append(isCorrect ? question.getPoints() : 0)
                    .append(Color.RESET); // Reset color after the points

            results.append("\n\n");
        }

        results.append("Total Score : ");

        // Append total score in purple color
        results.append(Color.BOLD_PURPLE)
                .append(calculateScore())
                .append(Color.RESET);  // Reset color after the total score

        results.append("\n");
        return results.toString();
    }


}
