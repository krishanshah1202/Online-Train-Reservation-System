import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame implements ActionListener {
    private JButton bookTicketButton, cancelTicketButton, bookingDetailsButton, contactButton, logoutButton;

    public Menu() {
        setTitle("Menu");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 1)); // Use GridLayout for 2 rows and 1 column

        // Create panel for the top buttons (2x2 grid)
        JPanel topButtonPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        // Initialize buttons
        bookTicketButton = new JButton("Book Ticket");
        cancelTicketButton = new JButton("Cancel Ticket");
        bookingDetailsButton = new JButton("Booking Details");
        contactButton = new JButton("Contact for Assistance/Complaints");

        // Add action listeners
        bookTicketButton.addActionListener(this);
        cancelTicketButton.addActionListener(this);
        bookingDetailsButton.addActionListener(this);
        contactButton.addActionListener(this);

        // Add buttons to the top panel
        topButtonPanel.add(bookTicketButton);
        topButtonPanel.add(cancelTicketButton);
        topButtonPanel.add(bookingDetailsButton);
        topButtonPanel.add(contactButton);

        // Create panel for the logout button (full width)
        JPanel bottomButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        // Initialize logout button
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);

        // Add logout button to the bottom panel
        bottomButtonPanel.add(logoutButton);

        // Add top and bottom panels to the frame
        add(topButtonPanel);
        add(bottomButtonPanel);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bookTicketButton) {
            // Open the BookTicket window
            new BookTicket();
        } else if (e.getSource() == cancelTicketButton) {
            // Open the CancelTicket window
            new CancelTicket();
        } else if (e.getSource() == bookingDetailsButton) {
            // Open the BookingDetails window
            new BookingDetails();
        } else if (e.getSource() == contactButton) {
            // Open the Helpline window
            new Helpline();
        } else if (e.getSource() == logoutButton) {
            // Redirect to the login page
            dispose();
            new Login();
        }
    }

    public static void main(String[] args) {
        // Run the application
        SwingUtilities.invokeLater(Menu::new);
    }
}
