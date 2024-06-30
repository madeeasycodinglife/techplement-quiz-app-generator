package com.madeeasy;

import com.madeeasy.color.Color;
import com.madeeasy.model.Quiz;
import com.madeeasy.service.impl.QuizServiceImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class TechPlementInternshipApplication {

    public static void main(String[] args) {
        QuizServiceImpl quizService = new QuizServiceImpl();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            printHeader();
            System.out.println(Color.BOLD_BLUE + "╔═══════════════════════════════════════╗" + Color.RESET);

            // Title line with centered text
            System.out.println(Color.BOLD_BLUE + "║" + Color.BOLD_YELLOW + "        Quiz Management System         " + Color.BOLD_BLUE + "║" + Color.RESET);

            // Separator line
            System.out.println(Color.BOLD_BLUE + "╠═══════════════════════════════════════╣" + Color.RESET);

            // Options with different colors
            System.out.println(Color.BOLD_BLUE + "║" + Color.BOLD_CYAN + " 1. Create a Quiz                      " + Color.BOLD_BLUE + "║" + Color.RESET);
            System.out.println(Color.BOLD_BLUE + "║" + Color.BOLD_PURPLE + " 2. Add Questions to a Quiz            " + Color.BOLD_BLUE + "║" + Color.RESET);
            System.out.println(Color.BOLD_BLUE + "║" + Color.BOLD_GREEN + " 3. Start a Quiz                       " + Color.BOLD_BLUE + "║" + Color.RESET);
            System.out.println(Color.BOLD_BLUE + "║" + Color.BOLD_WHITE + " 4. Update Quiz                        " + Color.BOLD_BLUE + "║" + Color.RESET);
            System.out.println(Color.BOLD_BLUE + "║" + Color.BOLD_CYAN + " 5. Get All Questions                  " + Color.BOLD_BLUE + "║" + Color.RESET);
            System.out.println(Color.BOLD_BLUE + "║" + Color.BOLD_RED + " 6. Exit                               " + Color.BOLD_BLUE + "║" + Color.RESET);

            // Bottom border
            System.out.println(Color.BOLD_BLUE + "╚═══════════════════════════════════════╝" + Color.RESET);

            // Prompt for user input with bold yellow color
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
                        List<Quiz> allQuizzesToBePrintedOut = quizService.getAllQuizzes();
                        if (allQuizzesToBePrintedOut.isEmpty()) {
                            System.out.println("No Quizzes found. Please create a quiz first.");
                            break;
                        }
                        System.out.println("Available Quizzes: ");
                        for (Quiz q : allQuizzesToBePrintedOut) {
                            System.out.println("Quiz ID: " + q.getQuizId() + ", Quiz Name: " + q.getQuizName());
                        }

                        System.out.print("Enter Quiz ID to search : ");
                        String quizIdToBeSearched = scanner.nextLine();
                        quizService.printQuizQuestionsWithAnswers(quizIdToBeSearched);
                        break;
                    case 6:
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
        // Load banner.txt using ClassLoader
        InputStream inputStream = TechPlementInternshipApplication.class.getResourceAsStream("/banner.txt");
        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                int colorIndex = 0;
                while ((line = reader.readLine()) != null) {
                    // Print each line with a different color
                    switch (colorIndex % 6) { // Example: 6 different colors
                        case 0:
                            System.out.println("\u001B[31m" + line); // Red
                            break;
                        case 1:
                            System.out.println("\u001B[32m" + line); // Green
                            break;
                        case 2:
                            System.out.println("\u001B[33m" + line); // Yellow
                            break;
                        case 3:
                            System.out.println("\u001B[34m" + line); // Blue
                            break;
                        case 4:
                            System.out.println("\u001B[35m" + line); // Purple
                            break;
                        case 5:
                            System.out.println("\u001B[36m" + line); // Cyan
                            break;
                        default:
                            System.out.println(line); // Default color
                            break;
                    }
                    colorIndex++;
                }
                // Reset color after printing
                System.out.print("\u001B[0m");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("banner.txt not found.");
        }
    }
}
