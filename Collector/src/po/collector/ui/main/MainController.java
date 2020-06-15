package po.collector.ui.main;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import po.collector.classes.db.Database;
import po.collector.classes.db.entities.*;
import po.collector.classes.db.managers.CollectionsManager;
import po.collector.classes.db.managers.DirectoriesManager;
import po.collector.classes.db.managers.TagsManager;
import po.collector.classes.files.Files;
import po.collector.classes.media.MediaView;
import po.collector.classes.media.StatusBarController;

public class MainController {
    private List<DirectoryEntity> dirs = new ArrayList<>();

    private ArrayList<CollectionEntity> collections = new ArrayList<>();
    private ArrayList<TagEntity> tags = new ArrayList<>();
    private ArrayList<FileEntity> media = new ArrayList<>();

    private FileEntity selectedMedia;
    private CollectionEntity selectedCollection;
    private List<FileEntity> currentMedia;

    private String searchCriteria;

    private Database db;

    @FXML
    private Tab tab_collections;

    @FXML
    private Tab tab_files;

    @FXML
    private Button button_add;

    @FXML
    private Button button_settings;

    @FXML
    private TextField text_field_search;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ScrollPane canvas;

    @FXML
    private AnchorPane canvas2;

    @FXML
    private Label label_media_location;

    @FXML
    private Label label_media_name;

    @FXML
    private Label label_status = new StatusBarController();

    @FXML
    private TextArea text_area_tags;

    @FXML
    private Button button_add_tag;

    @FXML
    private Button button_set_name;

    @FXML
    private Button button_add_collection;

    @FXML
    private Pane pane_mics;

    @FXML
    private Label label_collection;

    @FXML
    private Button button_clear_collection;

    @FXML
    private HBox root;

    @FXML
    private VBox vbox_canvas;

    @FXML
    private Label label_tags;

    @FXML
    void initialize() {
        discoverDirectories();
        displayDirectories();

        discoverTags();

        discoverCollections();
        displayCollections();

        loadMediaData();
        displayMedia(media);
    }

    public void discoverTags() {
        TagsManager tm = new TagsManager();
        tags.clear();
        tags.addAll(tm.getTags());

        System.out.println("Discovered " + tags.size() + " tags");
    }

    public void discoverCollections() {
        CollectionsManager cm = new CollectionsManager();
        collections.clear();
        collections.addAll(cm.getAllCollections());

        System.out.println("Discovered " + collections.size() + " collections");
    }

    public void displayCollections() {
        if (collections.size() > 0) {
            VBox vb = setupTabStack(tab_collections);

            for (CollectionEntity c : collections) {

                TabElement te = new TabElement(
                        c.getValue(),
                        c,
                        TabElementTypes.COLLECTION,
                        true
                );

                te.addEventFilter(MouseEvent.MOUSE_CLICKED, tabCollectionsClick);

                vb.getChildren().add(te);
            }
        }
    }

    public void displayDirectories() {
        if (dirs.size() > 0) {
            VBox vb = setupTabStack(tab_files);
            vb.getChildren().clear();

            for (DirectoryEntity d : dirs) {

                TabElement te = new TabElement(
                        d.getPath().replace("\\\\", "\\"),
                        d,
                        TabElementTypes.DIRECTORY,
                        false
                );

                vb.getChildren().add(te);
            }

        }
    }

    private VBox setupTabStack(Tab tab) {
        ScrollPane spDir = new ScrollPane();
        spDir.setFitToWidth(true);

        VBox vbDir = new VBox();
        vbDir.setFillWidth(true);

        spDir.setContent(vbDir);
        tab.setContent(spDir);

        return vbDir;
    }

    public void discoverDirectories() {
        DirectoriesManager dm = new DirectoriesManager();
        dirs.clear();
        dirs.addAll(dm.getAllDirectories());

        System.out.println("Discovered " + dirs.size() + " directories");
    }


    public void loadMediaData() {

        vbox_canvas.setDisable(true);
        media.clear();

        for (DirectoryEntity d : dirs) {

            try {
                media.addAll(Files.loadMediaFromFolder(d.getPath(), false));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (FileEntity m : media) {
                    m.load();
                }

                vbox_canvas.setDisable(false);
            }
        }).start();

    }

    private void displayMedia(List<FileEntity> media) {

        canvas.setContent(null);

        if (media.size() > 0) {
            selectedMedia = media.get(0);
            updateMetadata(media.get(0));

            List<HBox> hb = new ArrayList<>();
            VBox vb = new VBox();
            vb.setSpacing(5);
            vb.setFillWidth(true);

            try {
                for (int i = 0; i < media.size(); i++) {
                    if (i % 4 == 0) {
                        HBox tmphb = new HBox();
                        tmphb.setSpacing(5);

                        hb.add(tmphb);
                    }

                    MediaView im = new MediaView(media.get(i));
                    im.addEventFilter(MouseEvent.MOUSE_CLICKED, mediaViewClick);
                    im.addEventFilter(MouseEvent.MOUSE_ENTERED, mediaViewEntered);
                    im.addEventFilter(MouseEvent.MOUSE_EXITED, mediaViewLeave);

                    hb.get(hb.size() - 1).getChildren().add(im);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            currentMedia = media;
            vb.getChildren().addAll(hb);
            canvas.setContent(vb);
        }
    }

    @FXML
    void button_add_on_action(ActionEvent event) {
        Button bt = ((Button) event.getSource());

        if (bt.getText().toLowerCase().contains("coll")) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Collection");
            dialog.setHeaderText("Create new collection");
            dialog.setContentText("Collection:");

            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                CollectionEntity ce = new CollectionEntity(0, result.get(), "");
                ce.save();

                discoverCollections();
                displayCollections();
            }
        } else {
            final DirectoryChooser directoryChooser = new DirectoryChooser();

            Stage s = ((Stage) root.getScene().getWindow());
            File selectedDirectory = directoryChooser.showDialog(s);

            if (selectedDirectory != null) {
                String path = selectedDirectory.getAbsolutePath().replace("\\", "\\\\");
                DirectoryEntity de = new DirectoryEntity(0, path, "");
                de.save();

                try {
                    Files.loadMediaFromFolder(de.getPath(), true);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                discoverDirectories();
                displayDirectories();

                loadMediaData();
                displayMedia(media);
            }
        }
    }

    @FXML
    void button_settings_on_action(ActionEvent event) {

    }

    @FXML
    void tab_on_select(Event event) {

        if (button_add != null) {
            Tab tab = ((Tab) event.getSource());
            button_add.setText("Add " + tab.getText());
        }

    }


    @FXML
    void text_field_search_on_key_release(KeyEvent event) {

        if (event.getCode() == KeyCode.ENTER) {
            if (searchCriteria.length() == 0) {
                displayMedia(media);
                return;
            }


            LinkedHashSet<FileEntity> tmpMedia = new LinkedHashSet<>();

            for (FileEntity fe : media) {
                if (fe.getName().toLowerCase().contains(searchCriteria) || fe.getPath().toLowerCase().contains(searchCriteria)) {
                    tmpMedia.add(fe);
                }
            }

            for (TagEntity te : tags) {
                if (te.getValue().toLowerCase().contains(searchCriteria)) {
                    for (FileEntity fe : media) {
                        if (fe.hasTag(te)) {
                            tmpMedia.add(fe);
                        }
                    }
                }
            }

            for (CollectionEntity ce : collections) {
                if (ce.getValue().toLowerCase().contains(searchCriteria)) {
                    for (FileEntity fe : media) {
                        if (ce.hasMedia(fe)) {
                            tmpMedia.add(fe);
                        }
                    }
                }
            }

            displayMedia(new ArrayList<>(tmpMedia));

        } else {
            TextField tf = ((TextField) event.getSource());
            searchCriteria = tf.getText().toLowerCase();

            if (searchCriteria.length() == 0) {
                displayMedia(media);

            }
        }
    }

    @FXML
    void button_add_tag_on_click(MouseEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Tag");
        dialog.setHeaderText("Input tag");
        dialog.setContentText("Tag:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            TagEntity tmp = new TagEntity(0, result.get().toLowerCase().replace(" ", ""));

            if (!selectedMedia.hasTag(tmp)) {
                tmp.save();
                tmp.load();

                TagAssociationEntity tae = new TagAssociationEntity(0, tmp.getId(), selectedMedia.getId());

                tae.save();

                discoverTags();

                selectedMedia.load();
                updateMetadata(selectedMedia);
            }
        }
    }

    @FXML
    void button_add_to_collection_on_click(MouseEvent event) {
        List<String> choices = new ArrayList<>();

        for (CollectionEntity c : collections) {
            choices.add(c.getValue());
        }

        String defaultChoice = "";

        if (choices.size() > 0) {
            defaultChoice = choices.get(0);
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(defaultChoice, choices);
        dialog.setTitle("Collection selector");
        dialog.setHeaderText("Select collection from list below");
        dialog.setContentText("Collection:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {

            for (CollectionEntity c : collections) {
                if (c.getValue().equals(result.get())) {
                    CollectionAssociationEntity cae = new CollectionAssociationEntity(0, c.getId(), selectedMedia.getId());

                    cae.save();
                    c.load();

                    break;
                }
            }

        }
    }

    @FXML
    void button_set_name_on_click(MouseEvent event) {
        TextInputDialog dialog = new TextInputDialog(selectedMedia.getName());
        dialog.setTitle("Media rename");
        dialog.setHeaderText("Input media name");
        dialog.setContentText("Media name:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            selectedMedia.setName(result.get());
            selectedMedia.save();
            selectedMedia.load();
            updateMetadata(selectedMedia);
        }
    }

    EventHandler<MouseEvent> mediaViewClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            MediaView mv = ((MediaView) e.getSource());
            selectedMedia = mv.getMedia();
            updateMetadata(mv.getMedia());
        }
    };

    EventHandler<MouseEvent> mediaViewEntered = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            ImageView im = ((ImageView) e.getSource());
            im.setOpacity(0.5);
        }
    };

    EventHandler<MouseEvent> mediaViewLeave = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            ImageView im = ((ImageView) e.getSource());
            im.setOpacity(1);
        }
    };

    EventHandler<MouseEvent> tabCollectionsClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            TabElement mv = ((TabElement) e.getSource());
            CollectionEntity ce = ((CollectionEntity) mv.getSource());
            List<FileEntity> col = new ArrayList<>();

            for (FileEntity fe : media) {
                if (ce.hasMedia(fe)) {
                    col.add(fe);
                }
            }

            selectedCollection = ce;
            label_collection.setText(ce.getValue());
            displayMedia(col);
        }
    };

    @FXML
    void button_clear_collection_clicked(MouseEvent event) {
        selectedCollection = null;
        label_collection.setText("No collection selected");
        displayMedia(media);
    }

    private void updateMetadata(FileEntity m) {
        label_media_location.setText(m.getPath());
        label_media_name.setText(m.getName());

        label_tags.setText("");

        for (TagEntity te : tags) {

            if (selectedMedia.hasTag(te)) {
                if (label_tags.getText().length() == 0) {
                    label_tags.setText(te.getValue());
                } else {
                    label_tags.setText(label_tags.getText() + ", " + te.getValue());
                }
            }
        }
    }
}
