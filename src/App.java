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

    private void calculate() {
        quotientLabel.setForeground(Color.GRAY);
        quotientLabel.setText("...");

        // artificial delay to simulate a longer calculation
        SwingUtilities.invokeLater(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        double numerator;
        double denominator;
        try {
            numerator = Double.parseDouble(numeratorTextField.getText());
            denominator = Double.parseDouble(denominatorTextField.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(() -> {
                quotientLabel.setForeground(Color.RED);
                //TODO which field has the error?  what is nature of error?
                quotientLabel.setText("Error");
            });
            return;
        }

        //TODO this compares the double variable denominator to the integer literal zero, which is
        //  promoted to double 0.0.  We can go ahead and make the literal a double as follows:
        // if (denominator == 0d) //explicit double
        // -or-
        // if (denominator == 0.0) //implicit double
        if (denominator == 0) {
            SwingUtilities.invokeLater(() -> {
                quotientLabel.setForeground(Color.RED);
                quotientLabel.setText("Cannot divide by 0");
            });
            return;
        }

        //TODO how should we handle if user types in "NaN" for either value?  "Infinity" or "-Infinity"?
        // if (Double.isNaN(foo)) {...}
        // if (Double.isInfinite(foo)) {...}
        double quotient = numerator / denominator;

        SwingUtilities.invokeLater(() -> {
            quotientLabel.setForeground(Color.BLACK);
            quotientLabel.setText(String.valueOf(quotient));
        });
    }

    private void createButtonListener() {
        divideButton.addActionListener((event) -> {
            if (numeratorTextField.getText().isBlank() || denominatorTextField.getText().isBlank()) {
                SwingUtilities.invokeLater(() -> quotientLabel.setText(""));
                return;
            }

            //TODO what if I'm already on the EDT?
            // if (EventQueue.isDispatchThread()) {...}
            // HINT: notice that the button appears pressed in and never pops back out...it looks stuck...
            calculate();
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
        SwingUtilities.invokeLater(() -> app.setVisible(true));
    }

}