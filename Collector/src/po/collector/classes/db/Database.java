package po.collector.classes.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database {

    public Database(){
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection startDbConn(){
        try {
            return DriverManager.getConnection("jdbc:h2:"+ System.getProperty("user.dir") + "\\src\\collectordb", "admin", "1234");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean execute(String sql) {
        Connection con = startDbConn();

        try {
            Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public int getResultSetCount(ResultSet set){

        if(set == null) {
            return 0;
        }

        int size = 0;

        try {
            set.last();
            size = set.getRow();
            set.beforeFirst();
        }
        catch(Exception ex) {
            return 0;
        }

        return size;
    }

    public boolean delete(int id, String table){
        Connection con = startDbConn();
        String sql;

        if (id > 0) {
            sql = "DELETE FROM "+ table +" WHERE ID = " + id;
            execute(sql);
        }

        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }
}
