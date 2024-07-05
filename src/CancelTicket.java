import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class CancelTicket extends JFrame implements ActionListener {
    private JLabel pnrLabel;
    private JTextField pnrField;
    private JButton searchPnrButton;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/TrainReservationSystem";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Krishan@1998"; // Replace with your MySQL password

    public CancelTicket() {
        setTitle("Cancel Ticket");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        pnrLabel = new JLabel("Enter PNR:");
        pnrField = new JTextField(10);
        searchPnrButton = new JButton("Search");
        
        searchPnrButton.addActionListener(this);
        
        add(pnrLabel);
        add(pnrField);
        add(searchPnrButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchPnrButton) {
            String pnr = pnrField.getText();
            if (pnr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a PNR number.");
            } else {
                searchBooking(pnr);
            }
        }
    }

    private void searchBooking(String pnr) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Bookings WHERE pnr = ?")) {
            ps.setString(1, pnr);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to cancel this booking?", "Confirm Cancellation", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    cancelBooking(pnr);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No booking found with the given PNR.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    private void cancelBooking(String pnr) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement ps = conn.prepareStatement("DELETE FROM Bookings WHERE pnr = ?")) {
            ps.setString(1, pnr);
            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Booking cancelled successfully.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to cancel booking.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CancelTicket::new);
    }
}
