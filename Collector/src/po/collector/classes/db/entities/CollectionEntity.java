package po.collector.classes.db.entities;

import po.collector.classes.db.Database;
import po.collector.classes.db.managers.CollectionsAssociationsManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CollectionEntity extends Database {
    private int id;
    private String value;
    private String description;
    List<CollectionAssociationEntity> assoc = new ArrayList<>();

    public CollectionEntity() {
    }

    public CollectionEntity(int id, String value, String description) {
        setId(id);
        setValue(value);
        setDescription(description);
    }

    public List<CollectionAssociationEntity> getAssoc() {
        return assoc;
    }

    private void getEntityById(int id) {
        Connection con = super.startDbConn();

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT VALUE, DESCRIPTION FROM COLLECTIONS WHERE ID = " + id);

            while (rs.next()) {
                setValue(rs.getString("VALUE"));
                setDescription(rs.getString("DESCRIPTION"));
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
            ResultSet rs = statement.executeQuery("SELECT ID, DESCRIPTION FROM COLLECTIONS WHERE VALUE = '" + getValue() + "'");

            while (rs.next()) {
                setId(rs.getInt("ID"));
                setDescription(rs.getString("DESCRIPTION"));
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
        return value;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean hasMedia(FileEntity fileEntity) {
        for (CollectionAssociationEntity cae : assoc) {
            if (cae.getIdMedia() == fileEntity.getId() && fileEntity.getId() > 0) {
                return true;
            }
        }
        return false;
    }

    public void load() {
        if (id != 0) {
            getEntityById(getId());
        } else {
            getEntityByValue(getValue());
        }

        assoc.clear();
        assoc.addAll(new CollectionsAssociationsManager().getCollectionsAssociations(this));

        System.out.println("Collection: " + getValue() + " has assoc:");
        for (CollectionAssociationEntity cae: assoc) {
            System.out.println("id: " + cae.getId() + ", FILEID: " + cae.getIdMedia());
        }
    }


    public boolean save() {
        Connection con = super.startDbConn();
        String sql;

        if (getValue() != "") {
            sql = "SELECT * FROM COLLECTIONS WHERE VALUE = '" + getValue() + "'";
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
                sql = "INSERT INTO COLLECTIONS(VALUE, DESCRIPTION) VALUES('" + getValue() + "', '" + getDescription() + "')";
                super.execute(sql);
                this.load();

            } else {
                sql = "UPDATE COLLECTIONS SET VALUE='" + getValue() + "', DESCRIPTION='" + getDescription() + "' WHERE ID = " + getId();
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


    public boolean delete(){
        return super.delete(getId(), "COLLECTIONS");
    }
}
