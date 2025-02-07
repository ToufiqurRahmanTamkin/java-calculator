import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class Main implements ActionListener {
    private JFrame frame;
    private JTextField textField;
    private JButton[] numberButtons = new JButton[10];
    private JButton addButton, subButton, mulButton, divButton, equButton, clrButton, modButton, dotButton;
    private JPanel panel;

    public Main() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 600);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.BLACK);

        textField = new JTextField();
        textField.setBounds(20, 25, 340, 80);
        textField.setEditable(false);
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.WHITE);
        textField.setHorizontalAlignment(SwingConstants.RIGHT);
        textField.setFont(new Font("Arial", Font.BOLD, 40));
        frame.add(textField);

        clrButton = new JButton("AC");
        modButton = new JButton("%");
        divButton = new JButton("/");
        mulButton = new JButton("×");
        subButton = new JButton("-");
        addButton = new JButton("+");
        equButton = new JButton("=");
        dotButton = new JButton(".");

        JButton[] functionButtons = {clrButton, modButton, divButton, mulButton, subButton, addButton, equButton, dotButton};
        for (JButton button : functionButtons) {
            button.addActionListener(this);
            button.setBackground(new Color(255, 159, 10)); // Apple-style orange
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 30));
        }

        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(this);
            numberButtons[i].setBackground(new Color(51, 51, 51)); // Apple-style gray
            numberButtons[i].setForeground(Color.WHITE);
            numberButtons[i].setFont(new Font("Arial", Font.BOLD, 30));
        }

        panel = new JPanel();
        panel.setBounds(20, 120, 340, 420);
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.BLACK);
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
        String currentText = textField.getText();
        for (int i = 0; i < 10; i++) {
            if (e.getSource() == numberButtons[i]) {
                textField.setText(currentText + i);
            }
        }
        if (e.getSource() == clrButton) {
            textField.setText("");
        } else if (e.getSource() == addButton || e.getSource() == subButton ||
                e.getSource() == mulButton || e.getSource() == divButton ||
                e.getSource() == modButton) {
            if (!currentText.isEmpty() && "+-×/%".contains(currentText.substring(currentText.length() - 1))) {
                textField.setText(currentText.substring(0, currentText.length() - 1) + e.getActionCommand());
            } else {
                textField.setText(currentText + e.getActionCommand());
            }
        } else if (e.getSource() == equButton) {
            try {
                double result = evaluateExpression(currentText);
                textField.setText(String.valueOf(result));
            } catch (Exception ex) {
                textField.setText("Error");
            }
        } else if (e.getSource() == dotButton) {
            if (!currentText.contains(".")) {
                textField.setText(currentText + ".");
            }
        }
    }

    private double evaluateExpression(String expression) {
        expression = expression.replace("×", "*");

        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                StringBuilder num = new StringBuilder();
                while (i < expression.length() &&
                        (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    num.append(expression.charAt(i));
                    i++;
                }
                i--;
                numbers.push(Double.parseDouble(num.toString()));
            } else if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%') {
                while (!operators.empty() && hasPrecedence(c, operators.peek())) {
                    numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
                }
                operators.push(c);
            }
        }

        while (!operators.empty()) {
            numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
        }

        return numbers.pop();
    }

    private boolean hasPrecedence(char op1, char op2) {
        return (op1 != '*' && op1 != '/' && op1 != '%') || (op2 != '+' && op2 != '-');
    }

    private double applyOperation(char operator, double b, double a) {
        return switch (operator) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> {
                if (b == 0) throw new ArithmeticException("Division by zero");
                yield a / b;
            }
            case '%' -> a % b;
            default -> 0;
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}