import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;

public class EnhancedGameThemeCalculator extends JFrame implements ActionListener {
    private JTextPane textPane;
    private double angka1, angka2, hasil;
    private int operator;
    private boolean isOperatorClicked;
    private int mouseX, mouseY;

    public EnhancedGameThemeCalculator() {
        setTitle("Enhanced Game Theme Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setUndecorated(true);

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int height = getHeight();
                int width = getWidth();
                Color color1 = new Color(45, 45, 45);
                Color color2 = new Color(0, 0, 0);
                GradientPaint gradient = new GradientPaint(0, 0, color1, width, height, color2);
                g2d.setPaint(gradient);

                int cornerRadius = 30;
                g2d.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);

                // Tambahkan watermark di tengah atas
                g2d.setColor(new Color(255, 255, 255, 100));
                g2d.setFont(new Font("Arial", Font.BOLD, 12));
                String watermark = "Kalkulator by Rafi Adam";
                int watermarkWidth = g2d.getFontMetrics().stringWidth(watermark);
                int watermarkX = (width - watermarkWidth) / 2;
                g2d.drawString(watermark, watermarkX, 15);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));

        textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setFont(new Font("Press Start 2P", Font.PLAIN, 30));
        textPane.setBackground(new Color(0, 0, 0, 180));
        textPane.setForeground(new Color(255, 255, 255));
        textPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(scrollPane, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));
        buttonPanel.setOpaque(false);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "C", "0", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(this);
            customizeButton(button);
            buttonPanel.add(button);
        }

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        resultPanel.setOpaque(false);
        mainPanel.add(resultPanel, BorderLayout.SOUTH);

        // Tambahkan panel baru untuk watermark dan tombol ubah warna
        JPanel watermarkColorPanel = new JPanel();
        watermarkColorPanel.setLayout(new BorderLayout());
        watermarkColorPanel.setOpaque(false);
        resultPanel.add(watermarkColorPanel, BorderLayout.NORTH);

        // Tambahkan watermark di tengah atas
        JLabel watermarkLabel = new JLabel("Kalkulator by Rafi Adam");
        watermarkLabel.setFont(new Font("Arial", Font.BOLD, 12));
        watermarkLabel.setForeground(new Color(255, 255, 255, 100));
        watermarkColorPanel.add(watermarkLabel, BorderLayout.NORTH);

        // Tambahkan tombol ubah warna pertama
        JButton buttonColor = createColorButton("Ubah Warna");
        buttonColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(null, "Pilih Warna", Color.BLACK);
                animateColorChange(color);
            }
        });
        watermarkColorPanel.add(buttonColor, BorderLayout.WEST);

        // Tambahkan tombol ubah warna kedua
        JButton buttonOperasiColor = createColorButton("Ubah Operasi");
        buttonOperasiColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(null, "Pilih Warna", Color.BLACK);
                customizeButtonColors(buttonPanel, color);
            }
        });
        watermarkColorPanel.add(buttonOperasiColor, BorderLayout.EAST);

        JButton maximizeButton = createIconButton("Maximize");
        maximizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getExtendedState() != JFrame.MAXIMIZED_BOTH) {
                    setExtendedState(JFrame.MAXIMIZED_BOTH);
                } else {
                    setExtendedState(JFrame.NORMAL);
                }
            }
        });

        JButton closeButton = createIconButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animateExit();
            }
        });

        // Tambahkan mouse listener untuk menggeser aplikasi
        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        mainPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int deltaX = e.getX() - mouseX;
                int deltaY = e.getY() - mouseY;

                setLocation(getLocation().x + deltaX, getLocation().y + deltaY);

                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        mainPanel.add(maximizeButton, BorderLayout.EAST);
        mainPanel.add(closeButton, BorderLayout.EAST);

        add(mainPanel);

        // Menangani perubahan ukuran window untuk mempertahankan bentuk bulat
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
            }
        });

        setVisible(true);
    }

    private JButton createColorButton(String label) {
        JButton button = new JButton(label);
        button.setFont(new Font("Press Start 2P", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setForeground(new Color(255, 255, 255));
        button.setOpaque(false);

        // Tambahkan baris berikut untuk mengatur warna latar belakang tanpa transparansi
        button.setBackground(new Color(0, 0, 0, 180));

        return button;
    }

    private JButton createIconButton(String iconName) {
        JButton button = new JButton(iconName);
        button.setFont(new Font("Press Start 2P", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setForeground(new Color(255, 255, 255));
        button.setOpaque(false);
        return button;
    }

    private void customizeButton(JButton button) {
        button.setBackground(new Color(0, 0, 0, 180));
        button.setForeground(new Color(255, 255, 255));
        button.setFont(new Font("Press Start 2P", Font.BOLD, 16));
        button.setFocusPainted(false);

        button.getModel().addChangeListener(e -> {
            ButtonModel model = (ButtonModel) e.getSource();
            if (model.isPressed()) {
                button.setBackground(new Color(0, 0, 0, 150));
            } else {
                button.setBackground(new Color(0, 0, 0, 180));
            }
        });
    }

    private void customizeButtonColors(JPanel buttonPanel, Color color) {
        for (Component component : buttonPanel.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.setBackground(color);
            }
        }
    }

    private void animateColorChange(Color targetColor) {
        Timer timer = new Timer(10, new ActionListener() {
            float[] currentColorComponents = getContentPane().getBackground().getRGBComponents(null);
            float[] targetColorComponents = targetColor.getRGBComponents(null);
            float[] tempColorComponents = new float[currentColorComponents.length];

            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < currentColorComponents.length; i++) {
                    if (currentColorComponents[i] < targetColorComponents[i]) {
                        tempColorComponents[i] = Math.min(currentColorComponents[i] + 0.01f, targetColorComponents[i]);
                    } else {
                        tempColorComponents[i] = Math.max(currentColorComponents[i] - 0.01f, targetColorComponents[i]);
                    }
                }

                Color newColor = new Color(tempColorComponents[0], tempColorComponents[1], tempColorComponents[2], tempColorComponents[3]);
                getContentPane().setBackground(newColor);

                if (Arrays.equals(tempColorComponents, targetColorComponents)) {
                    ((Timer) e.getSource()).stop();
                }
            }
        });

        timer.start();
    }


    private void animateExit() {
        Timer timer = new Timer(20, null);
        timer.addActionListener(new ActionListener() {
            private float scale = 1.0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                scale -= 0.05f;
                if (scale > 0.1) {
                    setScale(scale);
                } else {
                    System.exit(0);
                }
            }
        });
        timer.start();
    }

    private void setScale(float scale) {
        ((JComponent) getContentPane()).setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, (int) (255 * (1 - scale))), 8));
        ((JComponent) getContentPane()).revalidate();
        ((JComponent) getContentPane()).repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        switch (action) {
            case "=":
                if (!isOperatorClicked) {
                    angka2 = Double.parseDouble(textPane.getText());
                    hasil = hitung(angka1, angka2, operator);
                    textPane.setText(String.valueOf(hasil));
                    isOperatorClicked = true;
                }
                break;
            case "C":
                textPane.setText("");
                isOperatorClicked = false;
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                if (!isOperatorClicked) {
                    angka1 = Double.parseDouble(textPane.getText());
                    operator = getOperator(action);
                    textPane.setText("");
                    isOperatorClicked = true;
                }
                break;
            default:
                textPane.setText(textPane.getText() + action);
                isOperatorClicked = false;
                break;
        }

        // Perbarui animasi warna setiap kali tombol ditekan
        animateColorChange(Color.BLACK);
    }

    private int getOperator(String operatorSymbol) {
        switch (operatorSymbol) {
            case "+":
                return 1;
            case "-":
                return 2;
            case "*":
                return 3;
            case "/":
                return 4;
            default:
                return 0;
        }
    }

    private double hitung(double angka1, double angka2, int operator) {
        switch (operator) {
            case 1:
                return angka1 + angka2;
            case 2:
                return angka1 - angka2;
            case 3:
                return angka1 * angka2;
            case 4:
                if (angka2 != 0)
                    return angka1 / angka2;
                else
                    return 0;
            default:
                return 0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EnhancedGameThemeCalculator();
        });
    }
}
