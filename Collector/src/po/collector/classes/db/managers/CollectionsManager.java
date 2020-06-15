package po.collector.classes.db.managers;

import po.collector.classes.db.Database;
import po.collector.classes.db.entities.CollectionAssociationEntity;
import po.collector.classes.db.entities.CollectionEntity;
import po.collector.classes.db.entities.FileEntity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CollectionsManager extends Database {
    public List<CollectionEntity> getAllCollections(){
        List<CollectionEntity> col = new ArrayList<>();

        Connection con = super.startDbConn();

        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT ID, VALUE, DESCRIPTION FROM COLLECTIONS");

            while (rs.next()) {
                CollectionEntity de = new CollectionEntity(rs.getInt("ID"), rs.getString("VALUE"), rs.getString("DESCRIPTION"));
                de.load();
                col.add(de);
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

        return col;
    }

    public List<FileEntity> getCollections(List<CollectionAssociationEntity> collectionAssociationEntities){
        List<FileEntity> col = new ArrayList<>();

        Connection con = super.startDbConn();

        for (CollectionAssociationEntity cae: collectionAssociationEntities) {

            if(cae.getIdMedia() > 0){
                try {
                    Statement statement = con.createStatement();
                    ResultSet rs = statement.executeQuery("SELECT ID, PATH, NAME FROM FILES WHERE ID = " + cae.getIdMedia());

                    while (rs.next()) {
                        FileEntity fe = new FileEntity(rs.getInt("ID"), rs.getString("NAME"), rs.getString("PATH"));
                        col.add(fe);
                    }

                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return col;
    }

    public List<FileEntity> getCollections(List<FileEntity> allFiles, int collectionId){
        List<FileEntity> col = new ArrayList<>();

        Connection con = super.startDbConn();

        for (FileEntity fe: allFiles) {

            if(fe.getId() > 0) {
                try {
                    Statement statement = con.createStatement();
                    ResultSet rs = statement.executeQuery("SELECT * FROM COLLECTIONSASSOCIATIONS WHERE FILEID = " + fe.getId() + " AND COLLECTIONID = " + collectionId);

                    if(super.getResultSetCount(rs) > 0){
                        col.add(fe);
                    }

                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        try {
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return col;
    }
}
