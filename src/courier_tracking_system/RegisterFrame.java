package courier_tracking_system;

import javax.swing.*;
import java.sql.*;

public class RegisterFrame extends JFrame {

    JTextField userField;
    JPasswordField passField;

    public RegisterFrame() {
        setTitle("Register");
        setSize(300, 200);
        setLayout(null);

        JLabel l1 = new JLabel("Username:");
        l1.setBounds(30, 30, 80, 25);
        add(l1);

        userField = new JTextField();
        userField.setBounds(120, 30, 120, 25);
        add(userField);

        JLabel l2 = new JLabel("Password:");
        l2.setBounds(30, 70, 80, 25);
        add(l2);

        passField = new JPasswordField();
        passField.setBounds(120, 70, 120, 25);
        add(passField);

        JButton regBtn = new JButton("Register");
        regBtn.setBounds(90, 110, 100, 30);
        add(regBtn);

        regBtn.addActionListener(e -> registerUser());

        setVisible(true);
    }

    void registerUser() {
        String username = userField.getText();
        String password = new String(passField.getPassword());

        try {
            Connection con = DBConnection.getConnection();

            String query = "INSERT INTO users(username, password) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "User Registered Successfully!");

            dispose(); // close register window

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}