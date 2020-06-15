package po.collector.classes.db.entities;

import po.collector.classes.db.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TagEntity extends Database {
    private int id;
    private String value;

    public TagEntity() {
    }

    public TagEntity(int id, String value) {
        setId(id);
        setValue(value);
    }

    private void getEntityById(int id) {
        Connection con = super.startDbConn();
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT VALUE FROM TAGS WHERE ID = " + id);
            while (rs.next()) {
                setValue(rs.getString("VALUE"));
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

    private void getEntityByValue(String value) {
        Connection con = super.startDbConn();
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT ID FROM TAGS WHERE VALUE = '" + getValue() + "'");
            while (rs.next()) {
                setId(rs.getInt("ID"));
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

    public void setId(int id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getValue() {
        return value == null ? "" : value;
    }

    public void load() {
        if (id != 0) {
            getEntityById(getId());
        } else {
            getEntityByValue(getValue());
        }
    }

    public boolean save() {
        Connection con = super.startDbConn();
        String sql;
        if (getValue() != "") {
            sql = "SELECT * FROM TAGS WHERE VALUE = '" + getValue() + "'";
            boolean isInDb = false;

            try {
                Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery(sql);
                isInDb = super.getResultSetCount(rs) > 0;
                rs.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (getId() == 0 && !isInDb) {
                sql = "INSERT INTO TAGS(VALUE) VALUES('" + getValue() + "')";
                super.execute(sql);
                this.load();
            } else {
                sql = "UPDATE TAGS SET VALUE='" + getValue() + "' WHERE ID = " + getId();
                super.execute(sql);
            }
        }
        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean delete() {
        return super.delete(getId(), "TAGS");
    }
}