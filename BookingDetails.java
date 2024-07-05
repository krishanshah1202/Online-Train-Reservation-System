import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BookingDetails extends JFrame implements ActionListener {
    private JLabel pnrLabel;
    private JTextField pnrField;
    private JButton searchButton;
    private JTextArea bookingDetailsArea;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/TrainReservationSystem";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Krishan@1998"; // Replace with your MySQL password

    public BookingDetails() {
        setTitle("Booking Details");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout());
        pnrLabel = new JLabel("Enter PNR:");
        pnrField = new JTextField(10);
        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        searchPanel.add(pnrLabel);
        searchPanel.add(pnrField);
        searchPanel.add(searchButton);

        bookingDetailsArea = new JTextArea();
        bookingDetailsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(bookingDetailsArea);

        add(searchPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            String pnr = pnrField.getText();
            if (pnr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a PNR number.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                fetchBookingDetails(pnr);
            }
        }
    }

    private void fetchBookingDetails(String pnr) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Bookings WHERE pnr = ?");
        ) {
            preparedStatement.setString(1, pnr);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    String gender = resultSet.getString("gender");
                    String uid = resultSet.getString("uid");
                    Date departureDate = resultSet.getDate("departure_date");
                    String travelClass = resultSet.getString("class");
                    String quota = resultSet.getString("quota");
                    String source = resultSet.getString("source");
                    String destination = resultSet.getString("destination");

                    StringBuilder bookingDetails = new StringBuilder();
                    bookingDetails.append("PNR: ").append(pnr)
                            .append("\nName: ").append(name)
                            .append("\nAge: ").append(age)
                            .append("\nGender: ").append(gender)
                            .append("\nUID: ").append(uid)
                            .append("\nDeparture Date: ").append(departureDate)
                            .append("\nClass: ").append(travelClass)
                            .append("\nQuota: ").append(quota)
                            .append("\nFrom: ").append(source)
                            .append("\nTo: ").append(destination);

                    bookingDetailsArea.setText(bookingDetails.toString());
                } else {
                    JOptionPane.showMessageDialog(this, "No booking found with the given PNR.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to fetch booking details: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BookingDetails::new);
    }
}
