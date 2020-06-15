package po.collector.classes.db.managers;

import po.collector.classes.db.Database;
import po.collector.classes.db.entities.DirectoryEntity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DirectoriesManager extends Database {

    public List<DirectoryEntity> getAllDirectories(){
        List<DirectoryEntity> dirs = new ArrayList<>();

        Connection con = super.startDbConn();

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT ID, PATH, NAME FROM DIRECTORIES");

            while (rs.next()) {
                DirectoryEntity de = new DirectoryEntity(rs.getInt("ID"), rs.getString("PATH"), rs.getString("NAME"));
                dirs.add(de);
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return dirs;
    }
}
