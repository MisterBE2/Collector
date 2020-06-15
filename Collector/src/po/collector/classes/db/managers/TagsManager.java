package po.collector.classes.db.managers;

import po.collector.classes.db.Database;
import po.collector.classes.db.entities.TagAssociationEntity;
import po.collector.classes.db.entities.TagEntity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TagsManager extends Database {
    public List<TagEntity> getTags() {
        List<TagEntity> tags = new ArrayList<>();
        Connection con = super.startDbConn();
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT ID, VALUE FROM TAGS");
            while (rs.next()) {
                TagEntity de = new TagEntity(rs.getInt("ID"), rs.getString("VALUE"));
                tags.add(de);
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
        return tags;
    }

    public List<TagEntity> getTags(List<TagAssociationEntity> tagAssociationEntities) {
        List<TagEntity> tags = new ArrayList<>();
        Connection con = super.startDbConn();
        for (TagAssociationEntity tae : tagAssociationEntities) {
            if (tae.getIdTag() > 0) {
                try {
                    Statement statement = con.createStatement();
                    ResultSet rs = statement.executeQuery("SELECT ID, VALUE FROM TAGS WHERE ID = " + tae.getIdTag());
                    while (rs.next()) {
                        TagEntity de = new TagEntity(rs.getInt("ID"), rs.getString("VALUE"));
                        tags.add(de);
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
        return tags;
    }
}