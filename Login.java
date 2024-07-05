import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    private JLabel usernameLabel, passwordLabel, captchaLabel, resultLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField captchaField;
    private JButton loginButton, refreshCaptchaButton;
    private String captcha;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/TrainReservationSystem";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Krishan@1998";

    public Login() {
        setTitle("Login Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        captchaLabel = new JLabel("Captcha:");
        resultLabel = new JLabel();

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        captchaField = new JTextField();

        captcha = generateCaptcha();
        captchaLabel.setText("Captcha: " + captcha);

        loginButton = new JButton("Login");
        refreshCaptchaButton = new JButton("Refresh Captcha");

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(captchaLabel);
        formPanel.add(captchaField);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(refreshCaptchaButton);
        buttonPanel.add(loginButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(resultLabel, BorderLayout.NORTH);

        loginButton.addActionListener(this);
        refreshCaptchaButton.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String enteredCaptcha = captchaField.getText();

            if (enteredCaptcha.equals(captcha)) {
                if (validateLogin(username, password)) {
                    resultLabel.setText("Login Successful!");
                    Session.setLoggedInUsername(username);
                    new Menu();
                    dispose();
                } else {
                    resultLabel.setText("Invalid username or password!");
                }
            } else {
                resultLabel.setText("Invalid captcha!");
            }
        } else if (e.getSource() == refreshCaptchaButton) {
            captcha = generateCaptcha();
            captchaLabel.setText("Captcha: " + captcha);
            captchaField.setText("");
        }
    }

    private boolean validateLogin(String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USERNAME, DB_PASSWORD)) {
                String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        return resultSet.next();
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to connect to the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    private String generateCaptcha() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder captcha = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int index = (int) (Math.random() * characters.length());
            captcha.append(characters.charAt(index));
        }
        return captcha.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}
