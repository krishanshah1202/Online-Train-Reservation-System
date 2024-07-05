import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class BookingsApp extends JFrame implements ActionListener {
    private JTextArea bookingsArea;
    private JComboBox<String> dayComboBox, monthComboBox, yearComboBox;
    private JComboBox<String> fromComboBox, toComboBox, classComboBox, quotaComboBox;
    private JButton addButton, viewButton;
    private ArrayList<String> bookings;

    public BookingsApp() {
        setTitle("Book Ticket");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(new BorderLayout());

        bookings = new ArrayList<>();

        // Initialize components
        JPanel addPanel = new JPanel(new GridLayout(9, 2, 5, 5)); // Increased to accommodate date components

        // Create combo boxes for day, month, and year
        dayComboBox = new JComboBox<>(generateNumericArray(1, 31));
        monthComboBox = new JComboBox<>(generateNumericArray(1, 12));
        yearComboBox = new JComboBox<>(generateNumericArray(2024, 2030)); // Adjust year range as needed
        
        String[] statesAndUT = {
            "Andaman and Nicobar Islands", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar",
            "Chandigarh", "Chhattisgarh", "Dadra and Nagar Haveli", "Daman and Diu", "Delhi", "Goa",
            "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka",
            "Kerala", "Ladakh", "Lakshadweep", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya",
            "Mizoram", "Nagaland", "Odisha", "Puducherry", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu",
            "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal"
        };
        fromComboBox = new JComboBox<>(statesAndUT);
        toComboBox = new JComboBox<>(statesAndUT);

        String[] classes = {"Economy", "Business", "First Class"};
        classComboBox = new JComboBox<>(classes);

        String[] quotas = {"General", "Ladies", "Senior Citizen", "Disabled"};
        quotaComboBox = new JComboBox<>(quotas);

        addButton = new JButton("Add Booking");
        viewButton = new JButton("View Bookings");
        bookingsArea = new JTextArea(15, 40);
        JScrollPane scrollPane = new JScrollPane(bookingsArea);
        bookingsArea.setEditable(false);

        // Add components to the add panel
        addPanel.add(new JLabel("Date:"));
        addPanel.add(createDateComboBoxPanel()); // Add panel containing day, month, and year combo boxes
        addPanel.add(new JLabel("From:"));
        addPanel.add(fromComboBox);
        addPanel.add(new JLabel("To:"));
        addPanel.add(toComboBox);
        addPanel.add(new JLabel("Class:"));
        addPanel.add(classComboBox);
        addPanel.add(new JLabel("Quota:"));
        addPanel.add(quotaComboBox);
        addPanel.add(new JLabel()); // Empty label for spacing
        addPanel.add(addButton);
        addPanel.add(new JLabel()); // Empty label for spacing
        addPanel.add(viewButton);

        // Add components to the frame
        add(addPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(this);
        viewButton.addActionListener(this);

        setVisible(true);
    }

    // Action performed when buttons are clicked
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            // Add Booking
            String date = (String) dayComboBox.getSelectedItem() + "/" +
                          (String) monthComboBox.getSelectedItem() + "/" +
                          (String) yearComboBox.getSelectedItem();
            String from = (String) fromComboBox.getSelectedItem();
            String to = (String) toComboBox.getSelectedItem();
            String selectedClass = (String) classComboBox.getSelectedItem();
            String selectedQuota = (String) quotaComboBox.getSelectedItem();

            String booking = String.format("Date: %s, From: %s, To: %s, Class: %s, Quota: %s",
                                            date, from, to, selectedClass, selectedQuota);
            addBooking(booking);
        } else if (e.getSource() == viewButton) {
            // View Bookings
            viewBookings();
        }
    }

    // Method to generate an array of numeric strings within a range
    private String[] generateNumericArray(int start, int end) {
        String[] array = new String[end - start + 1];
        for (int i = start; i <= end; i++) {
            array[i - start] = String.valueOf(i);
        }
        return array;
    }

    // Method to create a panel containing day, month, and year combo boxes
    private JPanel createDateComboBoxPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        panel.add(dayComboBox);
        panel.add(monthComboBox);
        panel.add(yearComboBox);

        return panel;
    }

    // Method to add a new booking
    public void addBooking(String booking) {
        bookings.add(booking);
        updateBookingsArea();
    }

    // Method to update bookings text area
    private void updateBookingsArea() {
        StringBuilder bookingsText = new StringBuilder();
        for (String booking : bookings) {
            bookingsText.append(booking).append("\n");
        }
        bookingsArea.setText(bookingsText.toString());
    }

    // Method to view bookings
    public void viewBookings() {
        JOptionPane.showMessageDialog(this, new JScrollPane(new JTextArea(bookings.toString(), 20, 40)),
                "Bookings", JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookingsApp());
    }
}
