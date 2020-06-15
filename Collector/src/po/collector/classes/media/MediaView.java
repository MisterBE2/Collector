package po.collector.classes.media;

import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import po.collector.classes.db.entities.FileEntity;

public class MediaView extends ImageView {
    private FileEntity media;

    public MediaView(FileEntity media){
        super(new Image(media.getPath(), 240, 135, false, true, true));
        this.setCache(true);
        this.setCacheHint(CacheHint.SPEED);
        this.media = media;
    }

    public FileEntity getMedia() {
        return media;
    }
}
