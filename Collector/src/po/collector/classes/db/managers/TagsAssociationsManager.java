package po.collector.classes.db.managers;

import po.collector.classes.db.Database;
import po.collector.classes.db.entities.FileEntity;
import po.collector.classes.db.entities.TagAssociationEntity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TagsAssociationsManager extends Database {
    public List<TagAssociationEntity> getTagAssociations() {
        List<TagAssociationEntity> tagAss = new ArrayList<>();
        Connection con = super.startDbConn();
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT ID, TAGID, FILEID FROM TAGSASSOCIATIONS");
            while (rs.next()) {
                TagAssociationEntity de = new TagAssociationEntity(rs.getInt("ID"), rs.getInt("TAGID"), rs.getInt("FILEID"));
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

    public List<TagAssociationEntity> getTagAssociations(FileEntity fileEntity) {
        List<TagAssociationEntity> tagAss = new ArrayList<>();
        if (fileEntity.getId() > 0) {
            Connection con = super.startDbConn();
            try {
                Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery("SELECT ID, TAGID, FILEID FROM TAGSASSOCIATIONS WHERE FILEID = " + fileEntity.getId());
                while (rs.next()) {
                    TagAssociationEntity de = new TagAssociationEntity(rs.getInt("ID"), rs.getInt("TAGID"), rs.getInt("FILEID"));
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
        }
        return tagAss;
    }
}