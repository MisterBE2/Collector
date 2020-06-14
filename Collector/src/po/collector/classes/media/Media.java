package po.collector.classes.media;
import javafx.scene.image.Image;
import po.collector.classes.Tag;
import java.util.ArrayList;
import java.util.Arrays;

public class Media extends Image {
    private String resURL;
    private ArrayList<Tag> tags = new ArrayList<>();

    public Media(String resURL) {
        super(resURL, 240, 135, false, true, true);
        this.resURL = resURL;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public void addTag(Tag[] tags) {
        this.tags.addAll(Arrays.asList(tags));
    }

    public void removeTag(Tag tag) {
        this.tags.removeIf(t -> t.getValue().equals(tag.getValue()));
    }

    public boolean hasTag(Tag tag) {
        for (Tag t : this.tags) {
            if (t.getValue() == tag.getValue()) {
                return true;
            }
        }
        return false;
    }

    public String getResUrl() {
        return this.resURL;
    }
}