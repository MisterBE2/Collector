package po.collector.classes.db.entities;
import po.collector.classes.db.Database;
import java.sql.Connection;
import java.sql.SQLException;
public class CollectionAssociationEntity extends Database {
    private int id;
    private int collectionId;
    private int idMedia;
    public CollectionAssociationEntity(int id, int collectionId, int idMedia){
        setId(id);
        setCollectionId(collectionId);
        setIdMedia(idMedia);
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }
    public void setIdMedia(int idMedia) {
        this.idMedia = idMedia;
    }
    public int getId() {
        return id;
    }
    public int getCollectionId() {
        return collectionId;
    }
    public int getIdMedia() {
        return idMedia;
    }
    public boolean save() {
        Connection con = super.startDbConn();
        String sql;
        if (getId() == 0) {
            sql = "INSERT INTO COLLECTIONSASSOCIATIONS(COLLECTIONID, FILEID) VALUES('" + getCollectionId() + "', '" + getIdMedia() + "')";
            System.out.println("Col ins: " + sql);
            super.execute(sql);
        } else {
            sql = "UPDATE COLLECTIONSASSOCIATIONS SET COLLECTIONID='" + getCollectionId() + "', FILEID='" + getIdMedia() + "' WHERE ID = " + getId();
            System.out.println("Col upd: " + sql);
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
        return super.delete(getId(), "COLLECTIONSASSOCIATIONS");
    }
}