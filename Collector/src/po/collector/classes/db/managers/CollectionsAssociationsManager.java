package po.collector.classes.db.managers;

import po.collector.classes.db.Database;
import po.collector.classes.db.entities.CollectionAssociationEntity;
import po.collector.classes.db.entities.CollectionEntity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CollectionsAssociationsManager extends Database {
    public List<CollectionAssociationEntity> getCollectionsAssociations() {
        List<CollectionAssociationEntity> tagAss = new ArrayList<>();
        Connection con = super.startDbConn();
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT ID, COLLECTIONID, FILEID FROM COLLECTIONSASSOCIATIONS");

            while (rs.next()) {
                CollectionAssociationEntity de = new CollectionAssociationEntity(rs.getInt("ID"), rs.getInt("COLLECTIONID"), rs.getInt("FILEID"));
                tagAss.add(de);
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
        return tagAss;
    }

    public List<CollectionAssociationEntity> getCollectionsAssociations(CollectionEntity collectionEntity) {
        List<CollectionAssociationEntity> colAss = new ArrayList<>();
        if (collectionEntity.getId() > 0) {
            Connection con = super.startDbConn();
            try {
                Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery("SELECT ID, COLLECTIONID, FILEID FROM COLLECTIONSASSOCIATIONS WHERE COLLECTIONID = " + collectionEntity.getId());
                while (rs.next()) {
                    CollectionAssociationEntity de = new CollectionAssociationEntity(rs.getInt("ID"), rs.getInt("COLLECTIONID"), rs.getInt("FILEID"));
                    colAss.add(de);
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
        return colAss;
    }
}