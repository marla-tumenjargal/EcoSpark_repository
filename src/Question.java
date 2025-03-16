import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

// Question class for quiz questions
class Question {
    private String questionText;
    private List<String> options;
    private int correctOptionIndex;

    public Question(String questionText, List<String> options, int correctOptionIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }

    // Display question with options
    public void displayQuestion() {
        System.out.println("\n" + questionText);
        for (int i = 0; i < options.size(); i++) {
            System.out.println((char)('A' + i) + ". " + options.get(i));
        }
    }

    // Check if the answer is correct
    public boolean isCorrect(char answer) {
        int index = answer - 'A';
        return index == correctOptionIndex;
    }
}

// Response class to store user's answers
class Response {
    private Question question;
    private char userAnswer;
    private boolean correct;

    public Response(Question question, char userAnswer) {
        this.question = question;
        this.userAnswer = userAnswer;
        this.correct = question.isCorrect(userAnswer);
    }

    public Question getQuestion() {
        return question;
    }

    public char getUserAnswer() {
        return userAnswer;
    }

    public boolean isCorrect() {
        return correct;
    }
}

// QuizResult class to store results of a quiz
class QuizResult {
    private List<Response> responses;
    private int score;
    private Date completionDate;

    public QuizResult() {
        this.responses = new ArrayList<>();
        this.score = 0;
        this.completionDate = new Date();
    }

    public void addResponse(Response response) {
        responses.add(response);
        if (response.isCorrect()) {
            score++;
        }
    }

    public List<Response> getResponses() {
        return responses;
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return responses.size();
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    // Display quiz results
    public void displayResults() {
        System.out.println("\n--- Quiz Results ---");
        System.out.println("Date: " + completionDate);
        System.out.println("Score: " + score + "/" + responses.size() + " (" +
                (int)((score * 100.0) / responses.size()) + "%)");

        System.out.println("\nQuestion Review:");
        for (int i = 0; i < responses.size(); i++) {
            Response response = responses.get(i);
            System.out.println((i + 1) + ". " + response.getQuestion().getQuestionText());
            System.out.println("   Your answer: " + response.getUserAnswer() +
                    " - " + (response.isCorrect() ? "Correct" : "Incorrect"));
            if (!response.isCorrect()) {
                System.out.println("   Correct answer: " +
                        (char)('A' + response.getQuestion().getCorrectOptionIndex()));
            }
        }
        System.out.println("------------------");
    }
}