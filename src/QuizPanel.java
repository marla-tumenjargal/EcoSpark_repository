import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class QuizPanel extends JPanel {
    private Quiz quiz;
    private int currentQuestionIndex = 0;
    private JLabel questionLabel;
    private JPanel optionsPanel;
    private JButton nextButton;
    private JLabel resultLabel;

    public QuizPanel(Quiz quiz) {
        this.quiz = quiz;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(20, 40, 40, 40));

        questionLabel = new JLabel();
        questionLabel.setFont(UIConstants.HEADER_FONT);
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setBackground(Color.WHITE);

        nextButton = new JButton("Next");
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextButton.addActionListener(e -> showNextQuestion());

        resultLabel = new JLabel();
        resultLabel.setFont(UIConstants.HEADER_FONT);
        resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(questionLabel);
        add(optionsPanel);
        add(nextButton);
        add(resultLabel);

        showQuestion();
    }

    private void showQuestion() {
        Question question = quiz.getQuestions().get(currentQuestionIndex);
        questionLabel.setText(question.getQuestionText());

        optionsPanel.removeAll();
        for (String option : question.getOptions()) {
            JRadioButton optionButton = new JRadioButton(option);
            optionButton.setActionCommand(option);
            optionButton.setBackground(Color.WHITE);
            optionsPanel.add(optionButton);
        }

        optionsPanel.revalidate();
        optionsPanel.repaint();
    }

    private void showNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < quiz.getQuestions().size()) {
            showQuestion();
        } else {
            resultLabel.setText("Quiz Completed!");
            nextButton.setEnabled(false);
        }
    }
}