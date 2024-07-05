import javax.swing.*;

public class Helpline extends JFrame {
    public Helpline() {
        setTitle("Contact for Assistance/Complaints");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("<html>Contact Information:<br>Phone: 123-456-7890<br>Email: support@trainreservationsystem.com</html>", SwingConstants.CENTER);
        add(label);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Helpline::new);
    }
}
