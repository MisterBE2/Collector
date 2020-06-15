package po.collector.classes.db.entities;

import po.collector.classes.db.Database;
import po.collector.classes.db.managers.TagsAssociationsManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FileEntity extends Database {
    private int id;
    private String path;
    private String name;
    private List<TagAssociationEntity> assoc = new ArrayList<>();

    public FileEntity() {
    }

    public FileEntity(String path, boolean saveonLoad) {
        setPath(path);

        if (saveonLoad) {
            save();
            load();
        }
    }

    public FileEntity(int id, String name, String path) {
        setId(id);
        setPath(path);
        setName(name);
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

    public boolean hasTag(TagEntity tag) {
        for (TagAssociationEntity tae : assoc) {
            if (tae.getIdTag() == tag.getId()) {
                return true;
            }
        }
        return false;
    }

    public List<TagAssociationEntity> getAssoc() {
        return assoc;
    }

    private void getEntityById(int id) {
        Connection con = super.startDbConn();

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT PATH, NAME FROM FILES WHERE ID = " + getId());

            while (rs.next()) {
                setPath(rs.getString("PATH"));
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

    private void getEntityByPath(String url) {
        Connection con = super.startDbConn();

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT ID, NAME FROM FILES WHERE PATH = '" + getPath() + "'");

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
            sql = "INSERT INTO FILES(PATH, NAME) VALUES('" + getPath() + "', '" + getName() + "')";

            System.out.println("FileEntity INS: " + sql);

            super.execute(sql);
        } else {
            sql = "UPDATE FILES SET PATH='" + getPath() + "', NAME='" + getName() + "' WHERE ID = " + getId();

            System.out.println("FileEntity UPD: " + sql);

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
        if (getId() > 0) {
            getEntityById(getId());
        } else {
            getEntityByPath(getPath());
        }

        assoc.clear();
        assoc.addAll(new TagsAssociationsManager().getTagAssociations(this));

        System.out.println("Tags for media " + getName() + ": ");

        for (TagAssociationEntity tae : getAssoc()) {
            System.out.println("ID: " + tae.getId() + ", TAGID: " + tae.getIdTag());
        }
    }

    public boolean delete() {
        return super.delete(getId(), "FILES");
    }
}

