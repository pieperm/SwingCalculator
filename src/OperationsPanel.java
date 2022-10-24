import javax.swing.*;
import java.awt.*;

public class OperationsPanel extends JPanel {

    private JButton addButton;
    private JButton subtractButton;
    private JButton multiplyButton;
    private JButton divideButton;
    private Operation selectedOperation;

    public OperationsPanel() {
        super();
        createContent();
        createLayout();
    }

    private void createContent() {
        addButton = new JButton("+");
        subtractButton = new JButton("-");
        multiplyButton = new JButton("*");
        divideButton = new JButton("÷");
    }

    private void createLayout() {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gridConstraints = new GridBagConstraints();
        gridConstraints.fill = GridBagConstraints.HORIZONTAL;

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.gridwidth = 1;
        this.add(addButton, gridConstraints);

        gridConstraints.gridx = 1;
        gridConstraints.gridy = 0;
        gridConstraints.gridwidth = 1;
        this.add(subtractButton, gridConstraints);

        gridConstraints.gridx = 2;
        gridConstraints.gridy = 0;
        gridConstraints.gridwidth = 1;
        this.add(multiplyButton, gridConstraints);

        gridConstraints.gridx = 3;
        gridConstraints.gridy = 0;
        gridConstraints.gridwidth = 1;
        this.add(divideButton, gridConstraints);
    }

}
