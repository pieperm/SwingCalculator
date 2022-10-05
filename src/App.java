import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    private JPanel contentPanel;
    private JTextField numeratorTextField;
    private JTextField denominatorTextField;
    private JButton divideButton;
    private JLabel quotientLabel;

    public App() {
        this.setSize(400, 300);
        this.setTitle("Calculator");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JFrame.setDefaultLookAndFeelDecorated(true);
        createAppContent();
    }

    private void createAppLayout() {
        GridBagLayout layout = new GridBagLayout();
        contentPanel.setLayout(layout);
        GridBagConstraints gridConstraints = new GridBagConstraints();

        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.gridwidth = 2;
        contentPanel.add(numeratorTextField, gridConstraints);

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 1;
        gridConstraints.gridwidth = 2;
        contentPanel.add(new JLabel("÷"), gridConstraints);

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 2;
        gridConstraints.gridwidth = 2;
        contentPanel.add(denominatorTextField, gridConstraints);

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 3;
        gridConstraints.gridwidth = 1;
        contentPanel.add(divideButton, gridConstraints);

        gridConstraints.gridx = 1;
        gridConstraints.gridy = 3;
        gridConstraints.gridwidth = 2;
        contentPanel.add(quotientLabel, gridConstraints);
    }

    private void createButtonListener() {
        divideButton.addActionListener((event) -> {
            if (numeratorTextField.getText().isBlank() || denominatorTextField.getText().isBlank()) {
                quotientLabel.setText("");
                return;
            }

            double numerator;
            double denominator;
            try {
                numerator = Double.parseDouble(numeratorTextField.getText());
                denominator = Double.parseDouble(denominatorTextField.getText());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                quotientLabel.setForeground(Color.RED);
                quotientLabel.setText("Error");
                return;
            }

            if (denominator == 0) {
                quotientLabel.setForeground(Color.RED);
                quotientLabel.setText("Cannot divide by 0");
                return;
            }

            double quotient = numerator / denominator;

            quotientLabel.setForeground(Color.BLACK);
            quotientLabel.setText(String.valueOf(quotient));
        });
    }

    private void createAppContent() {
        numeratorTextField = new JTextField(20);
        denominatorTextField = new JTextField(20);
        divideButton = new JButton("=");
        quotientLabel = new JLabel("0");

        contentPanel = new JPanel();
        createAppLayout();
        createButtonListener();
        this.add(contentPanel);
    }

    public static void main(String[] args) {
        System.out.println("Running Swing application...");
        App app = new App();
        app.setVisible(true);
    }

}