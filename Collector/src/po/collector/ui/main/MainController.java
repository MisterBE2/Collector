package po.collector.ui.main;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import po.collector.classes.Collection;
import po.collector.classes.Tag;
import po.collector.classes.files.Files;
import po.collector.classes.media.Media;

public class MainController {
    private String defaultMediaURL = "C:\\PO";

    private ArrayList<Collection> collections = new ArrayList<>();
    private ArrayList<Tag> tags = new ArrayList<>();
    private ArrayList<Media> media = new ArrayList<>();

    @FXML
    private Tab tab_collections;

    @FXML
    private Tab tab_tags;

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
    void initialize() {
        if (tab_collections.isSelected()) {
            button_add.setText("Add Collection");
        }

        Runnable r = new Runnable() {
            public void run() {
                media.addAll(Files.loadMedia(defaultMediaURL));
                for (Media m : media) {
                    System.out.println(m.getResUrl());
                }
            }
        };

        new Thread(r).start();
    }

    @FXML
    void button_add_on_action(ActionEvent event) {

    }

    @FXML
    void button_settings_on_action(ActionEvent event) {

    }

    @FXML
    void tab_on_select(Event event) {

        if(button_add != null) {
            if (tab_collections.isSelected()) {
                button_add.setText("Add Collection");
            } else if (tab_files.isSelected()) {
                button_add.setText("Add Source Folder");
            } else if (tab_tags.isSelected()) {
                button_add.setText("Add Tag");
            }
        }
    }


    @FXML
    void text_field_search_on_key_release(KeyEvent event) {

    }

}
