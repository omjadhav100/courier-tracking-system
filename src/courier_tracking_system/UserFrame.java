package courier_tracking_system;

import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class UserFrame extends JFrame {
    String username;

    public UserFrame(String user) {
        username = user;

        setTitle("User Panel");
        setSize(400, 300);
        getContentPane().setLayout(null);

        JButton bookBtn = new JButton("Book Courier");
        bookBtn.setBounds(100, 50, 200, 30);
        getContentPane().add(bookBtn);

        JButton trackBtn = new JButton("Track Courier");
        trackBtn.setBounds(100, 100, 200, 30);
        getContentPane().add(trackBtn);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(100, 200, 200, 30);
        getContentPane().add(logoutBtn);
        
        JButton mycourier = new JButton("My Courier");
        mycourier.setBounds(100, 150, 200, 30);
        mycourier.addActionListener(e -> showMyCouriers());
        getContentPane().add(mycourier);

        bookBtn.addActionListener(e -> bookCourier());
        trackBtn.addActionListener(e -> trackCourier());
        logoutBtn.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        setVisible(true);
    }
    
    

    void bookCourier() {
        String sender = JOptionPane.showInputDialog("Sender Name:");
        String receiver = JOptionPane.showInputDialog("Receiver Name:");

        try {
            Connection con = DBConnection.getConnection();

         // Inside bookCourier()
            String query = "INSERT INTO couriers(sender, receiver, status, booked_by) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, sender);
            ps.setString(2, receiver);
            ps.setString(3, "Booked");
            ps.setString(4, this.username); 

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Courier Booked!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void trackCourier() {
        String input = JOptionPane.showInputDialog("Enter Tracking ID:");

        try {
            int id = Integer.parseInt(input);

            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM couriers WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this,
                    "Status: " + rs.getString("status"));
            } else {
                JOptionPane.showMessageDialog(this, "Not Found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    void showMyCouriers() {
        try {
            Connection con = DBConnection.getConnection();
            
            String query = "SELECT id, sender, receiver FROM couriers WHERE booked_by = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, this.username);
            ResultSet rs = ps.executeQuery();

            // Table setup
            String[] columns = {"ID", "Sender", "Receiver"};
            Object[][] data = new Object[100][4]; // Placeholder size
            int i = 0;

            while (rs.next()) {
                data[i][0] = rs.getInt("id");
                data[i][1] = rs.getString("sender");
                data[i][2] = rs.getString("receiver");
                i++;
            }

            // Shrink data array to actual size
            Object[][] finalData = new Object[i][4];
            System.arraycopy(data, 0, finalData, 0, i);

            JTable table = new JTable(finalData, columns);
            JScrollPane scrollPane = new JScrollPane(table);
            
            JOptionPane.showMessageDialog(this, scrollPane, "My Booked Couriers", JOptionPane.PLAIN_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data: " + e.getMessage());
        }
    }
}