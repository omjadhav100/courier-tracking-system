package courier_tracking_system;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public static Connection getConnection() {
        Connection con = null;

        try {
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/courier_db",
                "root",
                "Om@123"
            );

            System.out.println("Connected to DB");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }
}