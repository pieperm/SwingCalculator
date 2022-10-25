import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    private JPanel contentPanel;
    private JTextField firstNumberTextField;
    private JTextField secondNumberTextField;
    private OperationsPanel operationsPanel;
    private JButton equalsButton;
    private JLabel resultLabel;

    public App() {
        this.setSize(400, 300);
        this.setTitle("Calculator");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JFrame.setDefaultLookAndFeelDecorated(true);
        createAppContent();
    }

    private void createAppContent() {
        firstNumberTextField = new JTextField(20);
        secondNumberTextField = new JTextField(20);
        operationsPanel = new OperationsPanel();
        equalsButton = new JButton("=");
        resultLabel = new JLabel("0");

        contentPanel = new JPanel();
        createAppLayout();
        this.add(contentPanel);
        createListeners();
    }

    private void createAppLayout() {
        GridBagLayout layout = new GridBagLayout();
        contentPanel.setLayout(layout);
        GridBagConstraints gridConstraints = new GridBagConstraints();

        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.gridwidth = 2;
        contentPanel.add(firstNumberTextField, gridConstraints);

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 1;
        gridConstraints.gridwidth = 2;
        contentPanel.add(operationsPanel, gridConstraints);

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 2;
        gridConstraints.gridwidth = 2;
        contentPanel.add(secondNumberTextField, gridConstraints);

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 3;
        gridConstraints.gridwidth = 1;
        contentPanel.add(equalsButton, gridConstraints);

        gridConstraints.gridx = 1;
        gridConstraints.gridy = 3;
        gridConstraints.gridwidth = 2;
        contentPanel.add(resultLabel, gridConstraints);
    }

    private void createListeners() {
        equalsButton.addActionListener((event) -> {
            //TODO what if I'm already on the EDT?
            // if (EventQueue.isDispatchThread()) {...}
            // HINT: notice that the button appears pressed in and never pops back out...it looks stuck...
            if (EventQueue.isDispatchThread()) {
                calculate();
            }
        });
    }

    private void displayError(String message, Object... args) {
        SwingUtilities.invokeLater(() -> {
            resultLabel.setForeground(Color.RED);
            resultLabel.setText(String.format(message, args));
        });
    }

    private void calculate() {
        Operation selectedOperation = operationsPanel.getSelectedOperation();

        resultLabel.setForeground(Color.GRAY);
        resultLabel.setText("...");

        // artificial delay to simulate a longer calculation
        SwingUtilities.invokeLater(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        double firstNumber;
        try {
            firstNumber = Double.parseDouble(firstNumberTextField.getText());
        } catch (NumberFormatException e) {
            SwingUtilities.invokeLater(() -> {
                resultLabel.setForeground(Color.RED);
                resultLabel.setText("Numerator is not a number");
            });
            return;
        }

        double secondNumber;
        try {
            secondNumber = Double.parseDouble(secondNumberTextField.getText());
        } catch (NumberFormatException e) {
            SwingUtilities.invokeLater(() -> {
                resultLabel.setForeground(Color.RED);
                resultLabel.setText("Denominator is not a number");
            });
            return;
        }

        double result;
        switch (selectedOperation) {
            case ADD -> result = firstNumber + secondNumber;
            case SUBTRACT -> result = firstNumber - secondNumber;
            case MULTIPLY -> result = firstNumber * secondNumber;
            case DIVIDE -> {
                if (secondNumber == 0d) {
                    displayError("Cannot divide by 0");
                    return;
                }
                result = firstNumber / secondNumber;
            }
            default -> {
                displayError("Invalid operation %s", selectedOperation);
                return;
            }
        }

        SwingUtilities.invokeLater(() -> {
            resultLabel.setForeground(Color.BLACK);
            resultLabel.setText(String.valueOf(result));
        });
    }

    public static void main(String[] args) {
        System.out.println("Running Swing application...");
        App app = new App();
        SwingUtilities.invokeLater(() -> app.setVisible(true));
    }

}