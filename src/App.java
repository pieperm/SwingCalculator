import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class App extends JFrame {

    private JPanel contentPanel;
    private JTextField firstNumberTextField;
    private JTextField secondNumberTextField;
    private OperationsPanel operationsPanel;
    private JButton equalsButton;
    private JLabel resultLabel;
    private JButton clearButton;

    public App() {
        this.setSize(500, 300);
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
        clearButton = new JButton("CLEAR");

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
        gridConstraints.gridwidth = 1;
        contentPanel.add(clearButton, gridConstraints);

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 1;
        gridConstraints.gridwidth = 2;
        contentPanel.add(firstNumberTextField, gridConstraints);

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 2;
        gridConstraints.gridwidth = 2;
        contentPanel.add(operationsPanel, gridConstraints);

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 3;
        gridConstraints.gridwidth = 2;
        contentPanel.add(secondNumberTextField, gridConstraints);

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 4;
        gridConstraints.gridwidth = 1;
        contentPanel.add(equalsButton, gridConstraints);

        gridConstraints.gridx = 1;
        gridConstraints.gridy = 4;
        gridConstraints.gridwidth = 2;
        contentPanel.add(resultLabel, gridConstraints);
    }

    private void replaceSymbols(JTextField textField) {
        Map<String, String> symbolMap = new HashMap<>();
        symbolMap.put("pi", "π");
        symbolMap.put("infinity", "∞");

        String symbolString = textField.getText().toLowerCase();

        for (String symbolText : symbolMap.keySet()) {
            String symbol = symbolMap.get(symbolText);
            symbolString = symbolString.replaceAll(symbolText, symbol);
        }

        final String textWithSymbols = symbolString;
        if (!textField.getText().toLowerCase().equals(textWithSymbols)) {
            SwingUtilities.invokeLater(() -> textField.setText(textWithSymbols));
        }
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

        clearButton.addActionListener((event) -> SwingUtilities.invokeLater(() -> {
            firstNumberTextField.setText("");
            secondNumberTextField.setText("");
            operationsPanel.clearSelectedOperation();
            resultLabel.setText("");
        }));

        firstNumberTextField.getDocument().addDocumentListener((InputChangeListener) e -> {
            replaceSymbols(firstNumberTextField);
        });

        secondNumberTextField.getDocument().addDocumentListener((InputChangeListener) e -> {
            replaceSymbols(secondNumberTextField);
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
            displayError("First input is invalid");
            return;
        }

        double secondNumber;
        try {
            secondNumber = Double.parseDouble(secondNumberTextField.getText());
        } catch (NumberFormatException e) {
            displayError("Second input is invalid");
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
            case EXPONENT -> result = Math.pow(firstNumber, secondNumber);
            default -> {
                displayError("No operation selected");
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