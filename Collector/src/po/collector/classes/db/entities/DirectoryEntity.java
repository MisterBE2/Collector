package po.collector.classes.db.entities;

import po.collector.classes.db.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DirectoryEntity extends Database {
    private int id;
    private String path;
    private String name;

    public DirectoryEntity(int id) {
        this.id = id;
        getEntityById(id);
    }

    public DirectoryEntity(int id, String path, String name) {
        this.id = id;
        this.path = path;
        this.name = name;
    }

    public String getPath() {
        return path == null ? "" : path;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void getEntityById(int id) {
        Connection con = super.startDbConn();
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT PATH, NAME FROM DIRECTORIES WHERE ID = " + id);
            while (rs.next()) {
                this.path = rs.getString("PATH");
                this.name = rs.getString("NAME");
                System.out.println(this.path);
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
    }

    private void getEntityByPath(String url) {
        Connection con = super.startDbConn();
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT ID, NAME FROM DIRECTORIES WHERE PATH = '" + getPath() + "'");
            while (rs.next()) {
                setId(rs.getInt("ID"));
                setName(rs.getString("NAME"));
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
    }

    public boolean save() {
        Connection con = super.startDbConn();
        String sql;
        if (getId() == 0) {
            sql = "INSERT INTO DIRECTORIES(PATH, NAME) VALUES('" + getPath() + "', '" + getName() + "')";
            super.execute(sql);
        } else {
            sql = "UPDATE DIRECTORIES SET PATH='" + getPath() + "', NAME='" + getName() + "' WHERE ID = " + getId();
            super.execute(sql);
        }
        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public void load() {
        if (id > 0) {
            getEntityById(getId());
        } else {
            getEntityByPath(getPath());
        }
    }

    public boolean delete() {
        return super.delete(getId(), "DICTIONARY");
    }
}