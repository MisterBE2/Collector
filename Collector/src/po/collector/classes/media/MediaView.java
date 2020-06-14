package po.collector.classes.media;

import javafx.scene.CacheHint;
import javafx.scene.image.ImageView;

public class MediaView extends ImageView {
    private Media media;

    public MediaView(Media media) {
        super(media);
        this.setCache(true);
        this.setCacheHint(CacheHint.SPEED);
        this.media = media;
    }

    public Media getMedia() {
        return media;
    }
}