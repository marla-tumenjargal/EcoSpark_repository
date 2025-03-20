import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class ClimateChangeQuiz {
    private static QuizBackend backend = new QuizBackend();
    private static JFrame frame;
    private static JLabel questionLabel;
    private static JButton[] optionButtons;
    private static JPanel mainPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClimateChangeQuiz::createQuizPanel);
    }

    private static void createQuizPanel() {
        frame = new JFrame("Climate Change Quiz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        frame.add(mainPanel, BorderLayout.CENTER);

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(questionLabel);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        optionButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JButton();
            optionButtons[i].addActionListener(new AnswerListener());
            buttonPanel.add(optionButtons[i]);
        }
        mainPanel.add(buttonPanel);

        loadQuestion();
        frame.setVisible(true);
    }

    private static void loadQuestion() {
        if (backend.hasMoreQuestions()) {
            String[] qData = backend.getCurrentQuestion();
            questionLabel.setText(qData[0]);
            for (int i = 0; i < 4; i++) {
                optionButtons[i].setText(qData[i + 1]);
            }
        } else {
            showResults();
        }
    }

    private static void showResults() {
        JOptionPane.showMessageDialog(frame, "Quiz Over! Your Score: " + backend.getScore() + " / " + backend.getTotalQuestions());

        StringBuilder stats = new StringBuilder("Answer Frequencies:\n");
        for (Map.Entry<String, Integer> entry : backend.getAnswerFrequencies().entrySet()) {
            stats.append(entry.getKey()).append(": ").append(entry.getValue()).append(" times\n");
        }

        JOptionPane.showMessageDialog(frame, stats.toString());
        frame.dispose();
    }

    private static class AnswerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            backend.checkAnswer(clickedButton.getText());
            loadQuestion();
        }
    }
}
