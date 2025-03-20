import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

class QuizBackend {
    private static final String[][] quizData = {
            {"What is the primary cause of climate change?", "Deforestation", "Burning fossil fuels", "Agriculture", "Volcanic eruptions", "Burning fossil fuels"},
            {"Which greenhouse gas is most responsible for global warming?", "Oxygen", "Methane", "Carbon dioxide", "Nitrogen", "Carbon dioxide"},
            {"What percentage of climate scientists agree that climate change is human-caused?", "50%", "75%", "97%", "100%", "97%"}
    };

    private Queue<String[]> questionQueue;
    private int score = 0;
    private Map<String, Integer> answerFrequencies;

    public QuizBackend() {
        questionQueue = new LinkedList<>();
        answerFrequencies = new HashMap<>();
        for (String[] q : quizData) {
            questionQueue.offer(q);
        }
    }

    public String[] getCurrentQuestion() {
        return questionQueue.peek();
    }

    public boolean checkAnswer(String selectedAnswer) {
        if (questionQueue.isEmpty()) return false;

        String[] currentQuestion = questionQueue.poll();
        String correctAnswer = currentQuestion[5];

        answerFrequencies.put(selectedAnswer, answerFrequencies.getOrDefault(selectedAnswer, 0) + 1);

        if (selectedAnswer.equals(correctAnswer)) {
            score++;
            return true;
        }
        return false;
    }

    public boolean hasMoreQuestions() {
        return !questionQueue.isEmpty();
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return quizData.length;
    }

    public Map<String, Integer> getAnswerFrequencies() {
        return answerFrequencies;
    }
}