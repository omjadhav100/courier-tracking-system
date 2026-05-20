package courier_tracking_system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class AdminFrame extends JFrame {

    JTable table;
    DefaultTableModel model;

    public AdminFrame() {
        setTitle("Admin Panel");
        setSize(500, 400);
        setLayout(null);

        JButton viewBtn = new JButton("View Couriers");
        viewBtn.setBounds(150, 20, 200, 30);
        add(viewBtn);

        JButton updateBtn = new JButton("Update Status");
        updateBtn.setBounds(150, 60, 200, 30);
        add(updateBtn);
        
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBounds(380, 20, 100, 30);
        add(logoutBtn);

        // Table setup
        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Sender");
        model.addColumn("Receiver");
        model.addColumn("Status");

        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(50, 110, 380, 200);
        add(sp);

        viewBtn.addActionListener(e -> loadTable());
        updateBtn.addActionListener(e -> updateStatus());
        logoutBtn.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        setVisible(true);
    }

    // Load data into table
    void loadTable() {
        model.setRowCount(0);

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT * FROM couriers";
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("sender"),
                    rs.getString("receiver"),
                    rs.getString("status")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Update status
    void updateStatus() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row!");
            return;
        }

        int id = (int) model.getValueAt(row, 0);

        String status = JOptionPane.showInputDialog("Enter Status:");

        try {
            Connection con = DBConnection.getConnection();

            String query = "UPDATE couriers SET status=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, status);
            ps.setInt(2, id);

            ps.executeUpdate();

            loadTable();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}