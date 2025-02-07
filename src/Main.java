import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main implements ActionListener {
    private JFrame frame;
    private JTextField textField;
    private JButton[] numberButtons = new JButton[10];
    private JButton addButton, subButton, mulButton, divButton, equButton, clrButton, modButton, dotButton;
    private JPanel panel;

    private double num1, num2, result;
    private char operator;
    private boolean isOperatorPressed = false;

    public Main() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 550);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(30, 30, 30));

        textField = new JTextField();
        textField.setBounds(20, 25, 300, 60);
        textField.setEditable(false);
        textField.setBackground(new Color(50, 50, 50));
        textField.setForeground(Color.WHITE);
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        textField.setFont(new Font("Arial", Font.BOLD, 30));
        frame.add(textField);

        clrButton = new JButton("AC");
        modButton = new JButton("%");
        divButton = new JButton("/");
        mulButton = new JButton("×");
        subButton = new JButton("-");
        addButton = new JButton("+");
        equButton = new JButton("=");
        dotButton = new JButton(".");

        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
        }

        JButton[] functionButtons = {clrButton, modButton, divButton, mulButton, subButton, addButton, equButton, dotButton};
        for (JButton button : functionButtons) {
            button.addActionListener(this);
        }

        panel = new JPanel();
        panel.setBounds(20, 100, 300, 400);
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(clrButton, gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 2;
        panel.add(modButton, gbc);
        gbc.gridx = 3;
        panel.add(divButton, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        panel.add(numberButtons[7], gbc);
        gbc.gridx = 1;
        panel.add(numberButtons[8], gbc);
        gbc.gridx = 2;
        panel.add(numberButtons[9], gbc);
        gbc.gridx = 3;
        panel.add(mulButton, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        panel.add(numberButtons[4], gbc);
        gbc.gridx = 1;
        panel.add(numberButtons[5], gbc);
        gbc.gridx = 2;
        panel.add(numberButtons[6], gbc);
        gbc.gridx = 3;
        panel.add(subButton, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        panel.add(numberButtons[1], gbc);
        gbc.gridx = 1;
        panel.add(numberButtons[2], gbc);
        gbc.gridx = 2;
        panel.add(numberButtons[3], gbc);
        gbc.gridx = 3;
        panel.add(addButton, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        panel.add(numberButtons[0], gbc);
        gbc.gridwidth = 1;
        gbc.gridx = 2;
        panel.add(dotButton, gbc);
        gbc.gridx = 3;
        panel.add(equButton, gbc);

        frame.add(panel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                if (isOperatorPressed) {
                    textField.setText("");
                    isOperatorPressed = false;
                }
                textField.setText(textField.getText() + i);
            }
        }
        if (e.getSource() == clrButton) {
            textField.setText("");
        } else if (e.getSource() == addButton || e.getSource() == subButton || e.getSource() == mulButton || e.getSource() == divButton || e.getSource() == modButton) {
            num1 = Double.parseDouble(textField.getText());
            operator = e.getActionCommand().charAt(0);
            isOperatorPressed = true;
        } else if (e.getSource() == equButton) {
            num2 = Double.parseDouble(textField.getText());
            switch (operator) {
                case '+': result = num1 + num2; break;
                case '-': result = num1 - num2; break;
                case '×': result = num1 * num2; break;
                case '/': result = num1 / num2; break;
                case '%': result = num1 % num2; break;
            }
            textField.setText(String.valueOf(result));
        } else if (e.getSource() == dotButton) {
            if (!textField.getText().contains(".")) {
                textField.setText(textField.getText() + ".");
            }
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}