import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Signup extends JFrame implements ActionListener {
    // Components
    private JLabel nameLabel, dobLabel, genderLabel, mobileLabel, usernameLabel, passwordLabel;
    private JTextField nameField, mobileField, usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> dayComboBox, monthComboBox, yearComboBox, genderComboBox;
    private JButton signupButton, loginButton; // Added login button

    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/TrainReservationSystem";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Krishan@1998";

    public Signup() {
        setTitle("Signup Page");
        setSize(700, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new GridLayout(9, 2, 5, 5)); // Grid layout with 9 rows and 2 columns with 5-pixel gaps

        // Initialize components
        nameLabel = new JLabel("Name:");
        dobLabel = new JLabel("Date of Birth:");
        genderLabel = new JLabel("Gender:");
        mobileLabel = new JLabel("Mobile No:");
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");

        nameField = new JTextField();
        mobileField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();

        String[] genders = {"Male", "Female", "Other"};
        genderComboBox = new JComboBox<>(genders);

        dayComboBox = new JComboBox<>(generateNumericArray(1, 31));
        monthComboBox = new JComboBox<>(new String[]{
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        });
        yearComboBox = new JComboBox<>(generateNumericArray(1900, 2024)); // Years from 1900 to 2024

        signupButton = new JButton("Signup");
        loginButton = new JButton("Go to Login Page"); // Added login button

        // Add components to the frame
        add(nameLabel);
        add(nameField);
        add(dobLabel);
        add(createDateOfBirthPanel()); // Add panel for date of birth
        add(genderLabel);
        add(genderComboBox);
        add(mobileLabel);
        add(mobileField);
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(signupButton);
        add(loginButton); // Added login button
        add(new JLabel()); // Empty label for spacing

        // Add action listeners
        signupButton.addActionListener(this);
        loginButton.addActionListener(this); // Added action listener for login button

        setVisible(true);
    }

    // Action performed when buttons are clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signupButton) {
            // Signup logic
            signupUser();
        } else if (e.getSource() == loginButton) {
            // Redirect to login page
            goToLoginPage();
        }
    }

    // Method to handle user signup
    private void signupUser() {
        String name = nameField.getText();
        String dob = yearComboBox.getSelectedItem() + "-" + monthComboBox.getSelectedItem() + "-" + dayComboBox.getSelectedItem();
        String gender = (String) genderComboBox.getSelectedItem();
        String mobile = mobileField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (name.isEmpty() || mobile.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the MySQL database
            try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
                // Create a SQL statement
                String insertQuery = "INSERT INTO Users (name, date_of_birth, gender, mobile_no, username, password) " + // Use lowercase column names
                        "VALUES (?, ?, ?, ?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, dob);
                    preparedStatement.setString(3, gender);
                    preparedStatement.setString(4, mobile);
                    preparedStatement.setString(5, username);
                    preparedStatement.setString(6, password); // Consider hashing the password here

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Signup successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to signup user.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace(); // Print detailed error
            JOptionPane.showMessageDialog(this, "Failed to connect to the database: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to redirect to login page
    private void goToLoginPage() {
        dispose(); // Close the current Signup window
        new Login(); // Open the Login window
    }

    // Method to generate an array of numeric strings within a range
    private String[] generateNumericArray(int start, int end) {
        String[] array = new String[end - start + 1];
        for (int i = start; i <= end; i++) {
            array[i - start] = String.valueOf(i);
        }
        return array;
    }

    // Method to create a panel for the date of birth
    private JPanel createDateOfBirthPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(new JLabel("Day "));
        panel.add(dayComboBox);
        panel.add(new JLabel("  Month"));
        panel.add(monthComboBox);
        panel.add(new JLabel("  Year "));
        panel.add(yearComboBox);
        

        return panel;
    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Signup();
            }
        });
    }
}
