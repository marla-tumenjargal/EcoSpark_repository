import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuizService {
    private List<Quiz> quizzes;

    public QuizService() {
        this.quizzes = new ArrayList<>();
        // Load quizzes from a data source (e.g., database, file)
        loadQuizzes();
    }

    private void loadQuizzes() {
        // Example quiz
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("What is the capital of France?", Arrays.asList("Paris", "London", "Berlin", "Madrid"), "Paris"));
        questions.add(new Question("Which planet is known as the Red Planet?", Arrays.asList("Earth", "Mars", "Jupiter", "Saturn"), "Mars"));
        quizzes.add(new Quiz(questions));
    }

    public Quiz getQuiz(int index) {
        if (index >= 0 && index < quizzes.size()) {
            return quizzes.get(index);
        }
        return null;
    }
}