package com.madeeasy;

import com.madeeasy.model.Quiz;
import com.madeeasy.service.impl.QuizServiceImpl;

import java.util.List;
import java.util.Scanner;

public class TechPlementInternshipApplication {

    public static void main(String[] args) {
        QuizServiceImpl quizService = new QuizServiceImpl();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            printHeader();
            System.out.println("Quiz Management System");
            System.out.println("1. Create a Quiz");
            System.out.println("2. Add Questions to a Quiz");
            System.out.println("3. Start a Quiz");
            System.out.println("4. Update Quiz");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        System.out.print("Enter Quiz Name: ");
                        String quizName = scanner.nextLine();
                        System.out.print("Enter Quiz Description: ");
                        String quizDescription = scanner.nextLine();
                        Quiz quiz = quizService.createQuiz(quizName, quizDescription);
                        break;

                    case 2:
                        List<Quiz> allQuizzes = quizService.getAllQuizzes();
                        if (allQuizzes.isEmpty()) {
                            System.out.println("No Quizzes found. Please create a quiz first.");
                            break;
                        }
                        System.out.println("Available Quizzes: ");
                        for (Quiz q : allQuizzes) {
                            System.out.println("Quiz ID: " + q.getQuizId() + ", Quiz Name: " + q.getQuizName());
                        }
                        System.out.print("Enter Quiz ID to add questions to: ");
                        String quizId = scanner.nextLine();
                        quizService.addQuestionsToQuizByQuizId(quizId);
                        break;

                    case 3:
                        List<Quiz> allAvailableQuizzes = quizService.getAllQuizzes();
                        if (allAvailableQuizzes.isEmpty()) {
                            System.out.println("No Quizzes found. Please create a quiz first.");
                            break;
                        }
                        quizService.startQuiz();
                        break;

                    case 4:
                        List<Quiz> allQuizzesToBeUpdated = quizService.getAllQuizzes();
                        if (allQuizzesToBeUpdated.isEmpty()) {
                            System.out.println("No Quizzes found. Please create a quiz first.");
                            break;
                        }
                        System.out.println("Available Quizzes: ");
                        for (Quiz q : allQuizzesToBeUpdated) {
                            System.out.println("Quiz ID: " + q.getQuizId() + ", Quiz Name: " + q.getQuizName());
                        }

                        System.out.print("Enter Quiz ID to update: ");
                        String updateQuizId = scanner.nextLine();
                        System.out.print("Enter new Quiz Name (leave blank to keep current): ");
                        String newQuizName = scanner.nextLine();
                        System.out.print("Enter new Quiz Description (leave blank to keep current): ");
                        String newDescription = scanner.nextLine();
                        quizService.updateQuiz(updateQuizId, newQuizName, newDescription, scanner);
                        break;
                    case 5:
                        exit = true;
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Please enter a number corresponding to your choice.");
            }
        }

        scanner.close();
        System.out.println("Exiting Quiz Management System. Goodbye!");
    }

    private static void printHeader() {
        System.out.println("*******************************************************************************");
        System.out.println("*                                                                             *");
        System.out.println("*                           @@@@@@@@@@@@@@@@@@@@@@@@                          *");
        System.out.println("*                        @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                       *");
        System.out.println("*                     @@@@@@@@@@@@@@@@     @@@@@@@@@@@@@@@                    *");
        System.out.println("*                  @@@@@@@@@@@@@@             @@@@@@@@@@@@@@                 *");
        System.out.println("*               @@@@@@@@@@@@@@                   @@@@@@@@@@@@@@              *");
        System.out.println("*            @@@@@@@@@@@@@@@      Techplement      @@@@@@@@@@@@@@            *");
        System.out.println("*         @@@@@@@@@@@@@@@@@@                       @@@@@@@@@@@@@@@@@@        *");
        System.out.println("*      @@@@@@@@@@@@@@@@@@@@@   Quiz App Generator   @@@@@@@@@@@@@@@@@@@      *");
        System.out.println("*    @@@@@@@@@@@@@@@@@@@@@@                           @@@@@@@@@@@@@@@@@@@    *");
        System.out.println("*      @@@@@@@@@@@@@@@@@@@@      Internship         @@@@@@@@@@@@@@@@@@@      *");
        System.out.println("*         @@@@@@@@@@@@@@@@@@                       @@@@@@@@@@@@@@@@@@        *");
        System.out.println("*            @@@@@@@@@@@@@@@                     @@@@@@@@@@@@@@@@@           *");
        System.out.println("*               @@@@@@@@@@@@@@                 @@@@@@@@@@@@@@@@@              *");
        System.out.println("*                  @@@@@@@@@@@@@@@          @@@@@@@@@@@@@@@@@                 *");
        System.out.println("*                     @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                   *");
        System.out.println("*                        @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                       *");
        System.out.println("*                           @@@@@@@@@@@@@@@@@@@@@@@@                          *");
        System.out.println("*                                                                             *");
        System.out.println("*******************************************************************************");
    }
}
