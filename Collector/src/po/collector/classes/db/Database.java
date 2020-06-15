package po.collector.classes.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private Connection con;

    public Database()
    {
        try {
            Class.forName("org.h2.Driver");
            this.con = DriverManager.getConnection("jdbc:h2:"+ System.getProperty("user.dir") + "\\src\\collectordb", "admin", "1234");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        try {
            this.con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
