package po.collector.classes;

import java.util.ArrayList;
import java.util.Arrays;

public class Collection {
    private String name;
    private ArrayList<Media> media = new ArrayList<>();

    public Collection(String name){
        this.name = name;
    }

    public void addMedia(Media media){
        this.media.add(media);
    }

    public void addMedia(Media[] media){
        this.media.addAll(Arrays.asList(media));
    }

    public void removeMedia(Media media){
        this.media.removeIf(m -> m.getResUrl().equals(media.getResUrl()));
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Media> getMedia() {
        return this.media;
    }
}