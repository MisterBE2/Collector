package po.collector.classes.files;

import po.collector.classes.media.Media;
import po.collector.classes.media.MediaTypes;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Files {
    public static String[] getFilesUrls(String dir, String filterType) {
        List<String> result;
        if (filterType.isEmpty()) {
            filterType = MediaTypes.JPG;
        }
        try (Stream<Path> walk = java.nio.file.Files.walk(Paths.get(dir))) {
            String finalFilterType = filterType;
            result = walk.map(Path::toString).filter(f -> f.endsWith(finalFilterType)).collect(Collectors.toList());
            return (String[]) result.toArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Media[] loadMedia(String dir) {
        List<Media> media = new ArrayList<>();
        String[] urls = Files.getFilesUrls(dir, "");
        assert urls != null;
        for (String u : urls) {
            media.add(new Media(u));
        }
        return (Media[]) media.toArray();
    }
}