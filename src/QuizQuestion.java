import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

class QuizQuestion {
    private String question;
    private List<String> options;
    private String correctAnswer;
    private String category;
    private int difficulty; // 1-3 (easy to hard)
    private List<String> originalOptions; // Keep original order for reference

    public QuizQuestion(String question, List<String> options, String correctAnswer, String category, int difficulty) {
        this.question = question;
        this.options = new ArrayList<>(options);
        this.originalOptions = new ArrayList<>(options);
        this.correctAnswer = correctAnswer;
        this.category = category;
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public boolean isCorrect(String answer) {
        return correctAnswer.equals(answer);
    }

    public String getCategory() {
        return category;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void shuffleOptions() {
        Collections.shuffle(options);
    }

    // Reset options to original order
    public void resetOptions() {
        options = new ArrayList<>(originalOptions);
    }

    // For avoiding duplicates in sets
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuizQuestion that = (QuizQuestion) o;
        return question.equals(that.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question);
    }
}