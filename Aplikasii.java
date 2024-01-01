
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Aplikasii extends JFrame implements ActionListener {
    private JTextField textField;
    private double angka1, angka2, hasil;
    private int operator;

    public Aplikasii() {
        setTitle("Kalkulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setEditable(false);
        add(textField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4));

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "C", "0", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        switch (action) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                textField.setText(textField.getText() + action);
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                angka1 = Double.parseDouble(textField.getText());
                switch (action) {
                    case "+":
                        operator = 1;
                        break;
                    case "-":
                        operator = 2;
                        break;
                    case "*":
                        operator = 3;
                        break;
                    case "/":
                        operator = 4;
                        break;
                }
                textField.setText("");
                break;
            case "=":
                angka2 = Double.parseDouble(textField.getText());
                switch (operator) {
                    case 1:
                        hasil = angka1 + angka2;
                        break;
                    case 2:
                        hasil = angka1 - angka2;
                        break;
                    case 3:
                        hasil = angka1 * angka2;
                        break;
                    case 4:
                        if (angka2 != 0)
                            hasil = angka1 / angka2;
                        else
                            textField.setText("Error");
                        break;
                }
                textField.setText(String.valueOf(hasil));
                break;
            case "C":
                textField.setText("");
                break;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Aplikasii();
            }
        });
    }
}
