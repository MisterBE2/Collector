package po.collector.classes.media;

import po.collector.classes.Tag;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;

public class Media {
    private String resURL;
    private BufferedImage img;
    private ArrayList<Tag> tags = new ArrayList<>();

    public Media(String resURL){
        this.resURL = resURL;

        try {
            this.img = ImageIO.read(new File(this.resURL));
        } catch (IOException e) {
            this.img = null;
        }
    }

    public void addTag(Tag tag){
        this.tags.add(tag);
    }

    public void addTag(Tag[] tags){
        this.tags.addAll(Arrays.asList(tags));
    }

    public void removeTag(Tag tag){
        this.tags.removeIf(t -> t.getValue().equals(tag.getValue()));
    }

    public boolean hasTag(Tag tag){
        for (Tag t : this.tags) {
            if(t.getValue() == tag.getValue()) {
                return true;
            }
        }

        return false;
    }

    public String getResUrl() {
        return this.resURL;
    }

    public BufferedImage getImage() {
        return this.img;
    }
}
