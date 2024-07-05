import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartupPage extends JFrame implements ActionListener {
    private JButton signupButton, loginButton;

    public StartupPage() {
        setTitle("Online Reservation System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());

        // Welcome note
        JLabel welcomeLabel = new JLabel("<html><div style='text-align: center;'>"
                + "<h1>Welcome to the Online Reservation System</h1>"
                + "<p>Use this system to book and cancel train tickets easily.</p>"
                + "<p>You can signup if you don't have an account or login if you already have one.</p>"
                + "<p>After logging in, you can book tickets, view booking details, and cancel tickets.</p>"
                + "</div></html>", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 20, 0)); // 1 row, 2 columns, 20px horizontal gap
        buttonPanel.setBackground(new Color(224, 255, 255)); // Light cyan background

        // Signup and login buttons
        signupButton = new JButton("Signup");
        signupButton.setFont(new Font("Arial", Font.BOLD, 16));
        signupButton.setBackground(new Color(135, 206, 235)); // Sky blue background
        signupButton.setForeground(Color.WHITE); // White text

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(70, 130, 180)); // Steel blue background
        loginButton.setForeground(Color.WHITE); // White text

        buttonPanel.add(signupButton);
        buttonPanel.add(loginButton);

        // Adding components to the frame
        add(welcomeLabel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        signupButton.addActionListener(this);
        loginButton.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signupButton) {
            dispose();
            new Signup();
        } else if (e.getSource() == loginButton) {
            dispose();
            new Login();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StartupPage::new);
    }
}
