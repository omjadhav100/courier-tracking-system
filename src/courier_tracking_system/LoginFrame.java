package courier_tracking_system;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class LoginFrame extends JFrame {
    JTextField userField;
    JPasswordField passField;

    public LoginFrame() {
        setTitle("Login");
        setSize(300, 300);
        getContentPane().setLayout(null);

        JLabel l1 = new JLabel("Username:");
        l1.setBounds(30, 30, 80, 25);
        getContentPane().add(l1);

        userField = new JTextField();
        userField.setBounds(120, 30, 120, 25);
        getContentPane().add(userField);

        JLabel l2 = new JLabel("Password:");
        l2.setBounds(30, 70, 80, 25);
        getContentPane().add(l2);

        passField = new JPasswordField();
        passField.setBounds(120, 70, 120, 25);
        getContentPane().add(passField);

        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(90, 119, 100, 30);
        getContentPane().add(loginBtn);

        loginBtn.addActionListener(e -> login());
        
        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(90, 170, 100, 30);
        getContentPane().add(registerBtn);

        registerBtn.addActionListener(e -> new RegisterFrame());

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    void login() {
        String u = userField.getText();
        String p = new String(passField.getPassword());

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, u);
            ps.setString(2, p);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                if (u.equals("admin")) {
                    new AdminFrame();
                } else {
                    new UserFrame(u);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Login");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}