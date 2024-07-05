import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Random;

public class BookTicket extends JFrame implements ActionListener {
    // Components
    private JLabel trainNoLabel, trainNameLabel, fromLabel, toLabel, classLabel, quotaLabel, dateLabel, nameLabel, ageLabel, genderLabel, uidLabel;
    private JTextField trainNoField, trainNameField, nameField, ageField, uidField;
    private JComboBox<String> fromComboBox, toComboBox, classComboBox, quotaComboBox, dateComboBox, monthComboBox, yearComboBox;
    private JRadioButton maleRadioButton, femaleRadioButton, otherRadioButton;
    private ButtonGroup genderGroup;
    private JButton searchButton, insertButton;

    // States and Union Territories
    private static final String[] STATES_UT = {
            "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh",
            "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand",
            "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur",
            "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab",
            "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura",
            "Uttar Pradesh", "Uttarakhand", "West Bengal",
            "Andaman and Nicobar Islands", "Chandigarh", "Dadra and Nagar Haveli and Daman and Diu",
            "Lakshadweep", "Delhi", "Puducherry", "Ladakh", "Jammu and Kashmir","Pune","Mumbai","Hyderabad","Chennai","Ahmedabad","Bangalore","Kolkata","New Delhi"
    };

    // Months
    private static final String[] MONTHS = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };

    // Years
    private static final String[] YEARS = { "2024", "2025", "2026" };

    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/TrainReservationSystem";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Krishan@1998"; // Replace with your MySQL password

    public BookTicket() {
        setTitle("Book Ticket");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout()); // Use border layout for top, center, bottom organization

        // Panel for top components
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        trainNoLabel = new JLabel("Train No:");
        trainNoField = new JTextField(10);
        searchButton = new JButton("Search");
        trainNameLabel = new JLabel("Train Name:");
        trainNameField = new JTextField(20);
        trainNameField.setEditable(false); // Train name is auto-filled
        topPanel.add(trainNoLabel);
        topPanel.add(trainNoField);
        topPanel.add(searchButton); // Add search button next to train number
        topPanel.add(trainNameLabel);
        topPanel.add(trainNameField);

        add(topPanel, BorderLayout.NORTH); // Add top panel to the top of the frame

        // Panel for center components
        JPanel centerPanel = new JPanel(new GridLayout(8, 2, 10, 10)); // Grid layout with 8 rows and 2 columns with 10-pixel gaps
        fromLabel = new JLabel("From:");
        fromComboBox = new JComboBox<>(STATES_UT);
        toLabel = new JLabel("To:");
        toComboBox = new JComboBox<>(STATES_UT);
        classLabel = new JLabel("Class:");
        classComboBox = new JComboBox<>(new String[] { "General", "Sleeper", "Third AC", "Second AC","First AC" });
        quotaLabel = new JLabel("Quota:");
        quotaComboBox = new JComboBox<>(new String[] { "Regular", "Ladies", "Senior Citizen" });
        dateLabel = new JLabel("Departure Date:");
        dateComboBox = new JComboBox<>(generateDates(31));
        monthComboBox = new JComboBox<>(MONTHS);
        yearComboBox = new JComboBox<>(YEARS);
        nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        ageLabel = new JLabel("Age:");
        ageField = new JTextField();
        genderLabel = new JLabel("Gender:");
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        maleRadioButton = new JRadioButton("Male");
        femaleRadioButton = new JRadioButton("Female");
        otherRadioButton = new JRadioButton("Other");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        genderGroup.add(otherRadioButton);
        genderPanel.add(maleRadioButton);
        genderPanel.add(femaleRadioButton);
        genderPanel.add(otherRadioButton);
        uidLabel = new JLabel("UID:");
        uidField = new JTextField();

        centerPanel.add(fromLabel);
        JPanel fromToPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        fromToPanel.add(fromComboBox);
        fromToPanel.add(toLabel);
        fromToPanel.add(toComboBox);
        centerPanel.add(fromToPanel); // Add From and To in the same row
        centerPanel.add(classLabel);
        centerPanel.add(classComboBox);
        centerPanel.add(quotaLabel);
        centerPanel.add(quotaComboBox);
        centerPanel.add(dateLabel);
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.add(dateComboBox);
        datePanel.add(monthComboBox);
        datePanel.add(yearComboBox);
        centerPanel.add(datePanel);
        centerPanel.add(nameLabel);
        centerPanel.add(nameField);
        centerPanel.add(ageLabel);
        centerPanel.add(ageField);
        centerPanel.add(genderLabel);
        centerPanel.add(genderPanel);
        centerPanel.add(uidLabel);
        centerPanel.add(uidField);

        add(centerPanel, BorderLayout.CENTER); // Add center panel to the center of the frame

        // Panel for bottom buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        insertButton = new JButton("Insert");
        bottomPanel.add(insertButton);

        add(bottomPanel, BorderLayout.SOUTH); // Add bottom panel to the bottom of the frame

        // Add action listeners
        searchButton.addActionListener(this);
        insertButton.addActionListener(this);

        setVisible(true);
    }

    // Action performed when buttons are clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            // Search for available trains
            searchTrains();
        } else if (e.getSource() == insertButton) {
            // Insert booking details
            insertBooking();
        }
    }

    // Method to search for trains in the database
    private void searchTrains() {
        String trainNo = trainNoField.getText();
        if (trainNo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a train number.");
            return;
        }

        try (
            Connection conn = DriverManager.getConnection(JDBC_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement ps = conn.prepareStatement("SELECT train_name, from_location, to_location FROM Trains WHERE train_no = ?");
        ) {
            ps.setInt(1, Integer.parseInt(trainNo));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                trainNameField.setText(rs.getString("train_name"));
                fromComboBox.setSelectedItem(rs.getString("from_location"));
                toComboBox.setSelectedItem(rs.getString("to_location"));
            } else {
                JOptionPane.showMessageDialog(this, "Train not found.");
                trainNameField.setText("");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    // Method to insert booking details into the database
    private void insertBooking() {
        String trainNo = trainNoField.getText();
        String name = nameField.getText();
        String age = ageField.getText();
        String gender = maleRadioButton.isSelected() ? "Male" : femaleRadioButton.isSelected() ? "Female" : otherRadioButton.isSelected() ? "Other" : "";
        String uid = uidField.getText();
        String departureDate = yearComboBox.getSelectedItem() + "-" + (monthComboBox.getSelectedIndex() + 1) + "-" + dateComboBox.getSelectedItem();
        String travelClass = (String) classComboBox.getSelectedItem();
        String quota = (String) quotaComboBox.getSelectedItem();
        String source = (String) fromComboBox.getSelectedItem();
        String destination = (String) toComboBox.getSelectedItem();

        if (trainNo.isEmpty() || name.isEmpty() || age.isEmpty() || gender.isEmpty() || uid.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all the fields.");
            return;
        }

        String pnr = generatePNR();

        try (
            Connection conn = DriverManager.getConnection(JDBC_URL, DB_USERNAME, DB_PASSWORD);
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Bookings (pnr, train_no, name, age, gender, uid, departure_date, class, quota, source, destination) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        ) {
            ps.setString(1, pnr);
            ps.setInt(2, Integer.parseInt(trainNo));
            ps.setString(3, name);
            ps.setInt(4, Integer.parseInt(age));
            ps.setString(5, gender);
            ps.setString(6, uid);
            ps.setDate(7, Date.valueOf(departureDate));
            ps.setString(8, travelClass);
            ps.setString(9, quota);
            ps.setString(10, source);
            ps.setString(11, destination);

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Booking inserted successfully. PNR: " + pnr);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    // Method to generate a 10-digit PNR number
    private String generatePNR() {
        Random random = new Random();
        StringBuilder pnr = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            pnr.append(random.nextInt(10));
        }
        return pnr.toString();
    }

    // Method to generate date options for the combo box
    private String[] generateDates(int maxDays) {
        String[] dates = new String[maxDays];
        for (int i = 0; i < maxDays; i++) {
            dates[i] = Integer.toString(i + 1);
        }
        return dates;
    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(BookTicket::new);
    }
}
