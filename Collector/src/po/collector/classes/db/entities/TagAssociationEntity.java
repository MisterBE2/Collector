package po.collector.classes.db.entities;

import po.collector.classes.db.Database;

import java.sql.Connection;
import java.sql.SQLException;

public class TagAssociationEntity extends Database {
    private int id;
    private int idTag;
    private int idMedia;

    public TagAssociationEntity(int id, int idTag, int idMedia){
        setId(id);
        setIdTag(idTag);
        setIdMedia(idMedia);
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdTag(int idTag) {
        this.idTag = idTag;
    }

    public void setIdMedia(int idMedia) {
        this.idMedia = idMedia;
    }

    public int getId() {
        return id;
    }

    public int getIdTag() {
        return idTag;
    }

    public int getIdMedia() {
        return idMedia;
    }

    public boolean save() {
        Connection con = super.startDbConn();
        String sql;

        if (getId() == 0) {
            sql = "INSERT INTO TAGSASSOCIATIONS(TAGID, FILEID) VALUES('" + getIdTag() + "', '" + getIdMedia() + "')";
            super.execute(sql);

        } else {
            sql = "UPDATE TAGSASSOCIATIONS SET TAGID='" + getIdTag() + "', FILEID='" + getIdMedia() + "' WHERE ID = " + getId();
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


    public boolean delete(){
        return super.delete(getId(), "TAGSASSOCIATIONS");
    }
}
