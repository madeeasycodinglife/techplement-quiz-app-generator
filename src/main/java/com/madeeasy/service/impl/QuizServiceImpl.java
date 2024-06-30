package com.madeeasy.service.impl;

import com.madeeasy.color.Color;
import com.madeeasy.dao.impl.QuizDAOImpl;
import com.madeeasy.model.Question;
import com.madeeasy.model.Quiz;
import com.madeeasy.service.QuizService;
import com.madeeasy.service.QuizSession;

import java.util.*;
import java.util.stream.Collectors;

public class QuizServiceImpl implements QuizService {

    private final List<Quiz> quizzes = new ArrayList<>();
    private final List<Question> questions = new ArrayList<>();
    private final List<Question> quizQuestionLists = new ArrayList<>();
    private Map<String, QuizSession> activeSessions = new HashMap<>();
    private final QuizDAOImpl quizDAO = new QuizDAOImpl();

    // completed
    @Override
    public Quiz createQuiz(String quizName, String description) {
        Quiz fountQuiz = this.quizDAO.getAllQuizzes()
                .stream()
                .filter(quiz -> quiz.getQuizName().equalsIgnoreCase(quizName))
                .findFirst()
                .orElse(null);

        if (fountQuiz != null) {
            System.out.println("Quiz with name " + quizName + " already exists\n\n\n");
            return fountQuiz;
        }

        Quiz quiz = Quiz.builder()
                .quizId(createRandomQuizId())
                .quizName(quizName)
                .description(description)
                .questions(new ArrayList<>())
                .build();
        List<Question> questionList = addQuestionsToQuiz(quiz);
        quiz.setQuestions(questionList);
        this.quizDAO.createQuiz(quiz);
        return quiz;
    }

    // completed
    public List<Question> addQuestionsToQuiz(Quiz quiz) {

        Scanner scanner = new Scanner(System.in);
        String continueAddingQuestions;

        do {

            // Enter difficulty level
            Integer difficultyLevel = null;
            while (difficultyLevel == null) {
                System.out.print("Enter difficulty level (1,2,3,4,5): ");
                String input = scanner.nextLine();
                try {
                    difficultyLevel = Integer.parseInt(input);
                    if (difficultyLevel < 1 || difficultyLevel > 5) {
                        System.out.println("Invalid difficulty level. Please enter a number between 1 and 5.");
                        difficultyLevel = null;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number between 1 and 5.");
                }
            }


            System.out.print("Enter the question text: ");
            String questionText = scanner.nextLine();

            List<String> options = new ArrayList<>();
            char optionLabel = 'a';
            System.out.println("Enter the options for the question (up to 4 options):");
            while (options.size() < 4) {
                System.out.printf("%c. ", optionLabel);
                String option = scanner.nextLine();
                if (!option.isEmpty()) {
                    options.add(option);
                    optionLabel++;
                }
            }

            System.out.print("Enter the correct answer (a, b, c, or d): ");
            String correctAnswer = scanner.nextLine();

            System.out.print("Enter the explanation: ");
            String explanation = scanner.nextLine();

            System.out.print("Enter the point value for the question: ");
            int points = Integer.parseInt(scanner.nextLine());

            Question question = Question.builder()
                    .difficultyLevel(difficultyLevel)
                    .question(questionText)
                    .correctAnswer(correctAnswer)
                    .explanation(explanation)
                    .points(points)
                    .options(options)
                    .build();

            this.questions.add(question);

            System.out.print("Do you want to add another question? (yes/no) : ");
            continueAddingQuestions = scanner.nextLine();

        } while (continueAddingQuestions.equalsIgnoreCase("yes"));
        return this.questions;
    }

    @Override
    public void addQuestionsToQuizByQuizId(String quizId) {
        Quiz foundQuizById = getQuizById(quizId);
        if (foundQuizById == null) {
            System.out.println("Quiz not found. Please create a quiz first.");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        String continueAddingQuestions;
        List<Question> questionList = new ArrayList<>();

        do {

            System.out.print("Enter the difficulty level of the question: ");
            int difficultyLevel = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter the question text: ");
            String questionText = scanner.nextLine();

            List<String> options = new ArrayList<>();
            char optionLabel = 'a';
            System.out.println("Enter the options for the question (up to 4 options): ");
            while (options.size() < 4) {
                System.out.printf("%c. ", optionLabel);
                String option = scanner.nextLine();
                if (!option.isEmpty()) {
                    options.add(option);
                    optionLabel++;
                }
            }

            System.out.print("Enter the correct answer (a, b, c, or d): ");
            String correctAnswer = scanner.nextLine();

            System.out.print("Enter the explanation: ");
            String explanation = scanner.nextLine();

            System.out.print("Enter the point value for the question: ");
            int points = Integer.parseInt(scanner.nextLine());

            Question question = Question.builder()
                    .difficultyLevel(difficultyLevel)
                    .question(questionText)
                    .correctAnswer(correctAnswer)
                    .explanation(explanation)
                    .points(points)
                    .options(options)
                    .build();

            questionList.add(question);

            System.out.print("Do you want to add another question? (yes/no) : ");
            continueAddingQuestions = scanner.nextLine();

        } while (continueAddingQuestions.equalsIgnoreCase("yes"));
        foundQuizById.setQuestions(questionList);
        this.quizDAO.createQuiz(foundQuizById);
    }

    @Override
    public void removeQuestionFromQuiz(String quizId, String questionId) {
        for (Quiz quiz : quizzes) {
            if (quiz.getQuizId().equals(quizId)) {
                quiz.getQuestions().removeIf(q -> q.getQuestion().equals(questionId));
                break;
            }
        }
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return this.quizDAO.getAllQuizzes();
    }

    @Override
    public Quiz getQuizById(String quizId) {
        return this.quizDAO.getQuizById(quizId);
    }

    @Override
    public void updateQuiz(String quizId, String newQuizName, String newDescription, Scanner scanner) {
        Quiz quizToUpdate = this.quizDAO.getQuizById(quizId);
        if (quizToUpdate == null) {
            System.out.println("Quiz with ID " + quizId + " not found.");
            return;
        }

        // Update quiz name and description
        if (!newQuizName.isEmpty()) {
            quizToUpdate.setQuizName(newQuizName);
        }
        if (!newDescription.isEmpty()) {
            quizToUpdate.setDescription(newDescription);
        }

        // Ask if user wants to update specific questions
        System.out.print("Do you want to update specific questions? (yes/no): ");
        String updateQuestions = scanner.nextLine();

        if (updateQuestions.equalsIgnoreCase("yes")) {
            List<Question> questions = quizToUpdate.getQuestions();
            for (int i = 0; i < questions.size(); i++) {
                Question question = questions.get(i);
                System.out.println((i + 1) + ". " + question.getQuestion());
            }

            boolean updating = true;
            while (updating) {
                System.out.print("Enter the question number to update (or 'done' to finish): ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("done")) {
                    updating = false;
                    continue;
                }

                int questionNumber;
                try {
                    questionNumber = Integer.parseInt(input);
                    if (questionNumber < 1 || questionNumber > questions.size()) {
                        System.out.println("Invalid question number. Please try again.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid question number or 'done' to finish.");
                    continue;
                }

                Question questionToUpdate = questions.get(questionNumber - 1);
                System.out.println("Updating Question " + questionNumber + ": " + questionToUpdate.getQuestion());

                System.out.print("Enter new question text (leave blank to keep current): ");
                String newQuestionText = scanner.nextLine();
                if (!newQuestionText.isEmpty()) {
                    questionToUpdate.setQuestion(newQuestionText);
                }

                System.out.print("Enter new difficulty level (leave blank to keep current): ");
                String newDifficultyLevel = scanner.nextLine();
                if (!newDifficultyLevel.isEmpty()) {
                    questionToUpdate.setDifficultyLevel(Integer.parseInt(newDifficultyLevel));
                }

                List<String> options = new ArrayList<>();
                for (int j = 0; j < questionToUpdate.getOptions().size(); j++) {
                    System.out.printf("Enter new option %c (leave blank to keep current): ", (char) ('a' + j));
                    String newOption = scanner.nextLine();
                    if (!newOption.isEmpty()) {
                        options.add(newOption);
                    } else {
                        options.add(questionToUpdate.getOptions().get(j));
                    }
                }
                questionToUpdate.setOptions(options);

                System.out.print("Enter new correct answer (a, b, c, or d) (leave blank to keep current): ");
                String newCorrectAnswer = scanner.nextLine();
                if (!newCorrectAnswer.isEmpty()) {
                    questionToUpdate.setCorrectAnswer(newCorrectAnswer);
                }

                System.out.print("Enter new explanation (leave blank to keep current): ");
                String newExplanation = scanner.nextLine();
                if (!newExplanation.isEmpty()) {
                    questionToUpdate.setExplanation(newExplanation);
                }

                System.out.print("Enter new point value (leave blank to keep current): ");
                String newPoints = scanner.nextLine();
                if (!newPoints.isEmpty()) {
                    questionToUpdate.setPoints(Integer.parseInt(newPoints));
                }
            }
        }

        this.quizDAO.updateQuiz(quizToUpdate);
        System.out.println("Quiz updated successfully.");
    }

//    @Override
//    public void deleteQuiz(String quizId) {
//        quizzes.removeIf(quiz -> quiz.getQuizId().equals(quizId));
//    }

    @Override
    public Question startQuiz() {
        Scanner scanner = new Scanner(System.in);

        // display all quiz names to be selected by the users or participants
        List<Quiz> allQuizzes = this.quizDAO.getAllQuizzes();

        Set<String> quizNames = allQuizzes.stream()
                .map(Quiz::getQuizName)
                .collect(Collectors.toSet());


        // Choose quiz name
        boolean validQuizName = false;
        String quizName = "";

        while (!validQuizName) {
            System.out.println("Available Quizzes:");
            // choose quiz name
            for (int i = 0; i < quizNames.size(); i++) {
                System.out.println((i + 1) + ". " + quizNames.toArray()[i]);
            }

            System.out.print("Enter Quiz Name: ");
            quizName = scanner.nextLine();

            String finalQuizName = quizName;
            if (quizNames.stream().anyMatch(name -> name.equalsIgnoreCase(finalQuizName))) {
                validQuizName = true;
            } else {
                System.out.println("Invalid quiz name. Please select a valid quiz name from the list.");
            }
        }

        // Enter difficulty level
        Integer difficultyLevel = null;
        while (difficultyLevel == null) {
            System.out.print("Enter difficulty level (1,2,3,4,5): ");
            String input = scanner.nextLine();
            try {
                difficultyLevel = Integer.parseInt(input);
                if (difficultyLevel < 1 || difficultyLevel > 5) {
                    System.out.println("Invalid difficulty level. Please enter a number between 1 and 5.");
                    difficultyLevel = null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number between 1 and 5.");
            }
        }

        // filter by difficulty level and quizName
        Quiz targatedQuiz = null;

        for (Quiz quiz : allQuizzes) {
            boolean flag = quiz.getQuizName().equalsIgnoreCase(quizName);
            if (flag) {
                List<Question> quizQuestions = quiz.getQuestions();
                for (Question question : quizQuestions) {
                    if (flag && question.getDifficultyLevel() == difficultyLevel) {
                        this.quizQuestionLists.add(question);
                        targatedQuiz = quiz;
                    }
                }
                break;
            }
        }

        if (!quizQuestionLists.isEmpty()) {
            this.activeSessions.put(targatedQuiz.getQuizId(), new QuizSessionImpl(targatedQuiz.getQuizId(), quizQuestionLists));
        }

        // Filter quizzes by quizName and difficultyLevel using streams
//        List<Question> selectedQuestion = allQuizzes.stream()
//                .filter(quiz -> quiz.getQuizName().equalsIgnoreCase(quizName))
//                .flatMap(quiz -> quiz.getQuestions().stream())
//                .filter(question -> question.getDifficultyLevel() == difficultyLevel)
//                .toList();

        assert targatedQuiz != null;
        runQuiz(targatedQuiz);
        return null;
    }

    private void runQuiz(Quiz targetedQuiz) {
        Scanner scanner = new Scanner(System.in);
        QuizSession session = activeSessions.get(targetedQuiz.getQuizId());
        if (session == null) {
            System.out.println("Quiz session not found.");
            return;
        }

        while (true) {
            Question currentQuestion = session.getCurrentQuestion();

            if (currentQuestion == null) {
                System.out.println(Color.BOLD_RED + "No more questions available. Are you sure you want to quit ?" + Color.RESET);
                String warnCommand;
                boolean warnValidCommand;

                do {
                    System.out.print("Command (" + Color.BOLD_WHITE + "previous" + Color.RESET + ", " +
                            Color.BOLD_GREEN + "next" + Color.RESET + ", " + Color.RED + "quit" + Color.RESET + "): ");
                    warnCommand = scanner.nextLine().trim().toLowerCase();

                    // Validate command
                    if (warnCommand.equals("next") || warnCommand.equals("previous") || warnCommand.equals("quit")) {
                        warnValidCommand = true;
                    } else {
                        System.out.println("Invalid command. Please enter 'next', 'previous', or 'quit'.");
                        warnValidCommand = false;
                    }
                } while (!warnValidCommand);

                if (warnCommand.equals("quit")) {
                    break;
                } else if (warnCommand.equals("previous")) {
                    session.previousQuestion();
                } else if (warnCommand.equals("next")) {
                    session.nextQuestion();
                }
            } else {
                // Display current question
                System.out.println("Question: " + currentQuestion.getQuestion());
                List<String> options = currentQuestion.getOptions();
                for (int i = 0; i < options.size(); i++) {
                    char optionLabel = (char) ('a' + i);
                    System.out.println(optionLabel + ". " + options.get(i));
                }

                // Get user input for answer
                String userInput;
                boolean validInput;

                do {
                    System.out.print("Your answer (a, b, c, d) : ");
                    userInput = scanner.nextLine().trim().toLowerCase();

                    // Validate user input
                    if (userInput.equals("a") || userInput.equals("b") || userInput.equals("c") || userInput.equals("d")) {
                        validInput = true;
                    } else {
                        System.out.println("Invalid input. Please enter 'a', 'b', 'c', or 'd'.");
                        validInput = false;
                    }
                } while (!validInput);

                // Submit user answer
                session.submitAnswer(currentQuestion.getQuestion(), userInput);

                // Get user command for navigation
                String command;
                boolean validCommand;

                do {
                    System.out.print("Command (" + Color.BOLD_WHITE + "previous" + Color.RESET + ", " +
                            Color.BOLD_GREEN + "next" + Color.RESET + ", " + Color.RED + "quit" + Color.RESET + "): ");
                    command = scanner.nextLine().trim().toLowerCase();

                    // Validate command
                    if (command.equals("next") || command.equals("previous") || command.equals("quit")) {
                        validCommand = true;
                    } else {
                        System.out.println("Invalid command. Please enter 'next', 'previous', or 'quit'.");
                        validCommand = false;
                    }
                } while (!validCommand);

                // Process user command
                if (command.equalsIgnoreCase("next")) {
                    session.nextQuestion();
                } else if (command.equalsIgnoreCase("previous")) {
                    session.previousQuestion();
                } else if (command.equalsIgnoreCase("quit")) {
                    break;
                } else {
                    System.out.println("Invalid command. Please try again.");
                }
            }
        }

        // Display quiz results
        System.out.println("Quiz finished. Here are your results:");
        System.out.println(session.getResults());
    }


    @Override
    public List<Question> getQuestionsByQuizId(String quizId) {
        Quiz quiz = getQuizById(quizId);
        if (quiz != null) {
            return quiz.getQuestions();
        }
        return null;
    }

    @Override
    public void printQuizQuestionsWithAnswers(String quizId) {
        List<Question> foundQuestions = getQuestionsByQuizId(quizId);

        if (foundQuestions == null || foundQuestions.isEmpty()) {
            System.out.println("No questions found for the given quiz ID.");
            return;
        }
        System.out.println();  // Empty line for separation
        System.out.println();  // Empty line for separation
        System.out.println();  // Empty line for separation

        for (int i = 0; i < foundQuestions.size(); i++) {
            Question question = foundQuestions.get(i);

            // Print question number and name in bold cyan
            System.out.println(Color.BOLD_CYAN + "Question " + (i + 1) + " : " + question.getQuestion() + Color.RESET);

            // Print other details
            System.out.println("Correct Answer : " + question.getCorrectAnswer());
            System.out.println("Explanation : " + question.getExplanation());
            System.out.println("Points : " + question.getPoints());
            System.out.println();  // Empty line for separation
        }
    }

    private String createRandomQuizId() {
        return "QUIZ" + UUID.randomUUID().toString();
    }
}
