import javax.swing.*;
import java.awt.*;

public class OperationsPanel extends JPanel {

    private JButton addButton;
    private JButton subtractButton;
    private JButton multiplyButton;
    private JButton divideButton;
    private ButtonGroup buttonGroup;
    private Operation selectedOperation;

    public OperationsPanel() {
        super();
        createContent();
        createLayout();
        createListeners();
    }

    private void createContent() {
        addButton = new JButton("+");
        subtractButton = new JButton("-");
        multiplyButton = new JButton("*");
        divideButton = new JButton("÷");
        buttonGroup = new ButtonGroup();

        buttonGroup.add(addButton);
        buttonGroup.add(subtractButton);
        buttonGroup.add(multiplyButton);
        buttonGroup.add(divideButton);
    }

    private void createLayout() {
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

    private void createOperationListener(JButton button, Operation operation) {
        button.addActionListener((event) -> {
            this.selectedOperation = operation;
            buttonGroup.setSelected(button.getModel(), true);
        });
    }

    private void createListeners() {
        createOperationListener(addButton, Operation.ADD);
        createOperationListener(subtractButton, Operation.SUBTRACT);
        createOperationListener(multiplyButton, Operation.MULTIPLY);
        createOperationListener(divideButton, Operation.DIVIDE);
    }

    public Operation getSelectedOperation() {
        return selectedOperation;
    }

}
