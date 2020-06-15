package po.collector.classes.files;

import po.collector.classes.db.entities.FileEntity;
import po.collector.classes.media.MediaTypes;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Files {
    public static List<String> getFilesUrls(String dir, String filterType) {
        List<String> result;
        if (filterType.isEmpty()) {
            filterType = MediaTypes.JPG;
        }
        try (Stream<Path> walk = java.nio.file.Files.walk(Paths.get(dir))) {
            String finalFilterType = filterType;
            result = walk.map(Path::toString).filter(f -> f.endsWith(finalFilterType)).collect(Collectors.toList());
            System.out.println("Found files:");
            result.forEach(System.out::println);

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<FileEntity> loadMediaFromFolder(String dir) throws MalformedURLException {
        List<FileEntity> media = new ArrayList<>();
        List<String> urls = Files.getFilesUrls(dir, MediaTypes.JPG);
        assert urls != null;
        for (String u : urls) {
            u =  u.replace("\\", "\\\\");
            System.out.println("loading: " + u);
            media.add(new FileEntity(new File(u).toURI().toString(), true));
        }
        return media;
    }

    public static FileEntity loadMedia(String url) throws MalformedURLException {
        File tmpMedia = new File(url);

        if(tmpMedia.exists())
        {
            url.replace("\\", "\\\\");
            System.out.println("loading: " + url);
            return new FileEntity(tmpMedia.toURI().toString(), true);
        }

        return null;
    }
}