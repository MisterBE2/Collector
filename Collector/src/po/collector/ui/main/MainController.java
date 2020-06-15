package po.collector.ui.main;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.CacheHint;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import po.collector.classes.Collection;
import po.collector.classes.Tag;
import po.collector.classes.files.Files;
import po.collector.classes.media.MediaView;


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
    private ScrollPane canvas;

    @FXML
    private AnchorPane canvas2;

    @FXML
    private Label label_test;

    @FXML
    void initialize() {
        if (tab_collections.isSelected()) {
            button_add.setText("Add Collection");
        }

        List<HBox> hb = new ArrayList<>();
        VBox vb = new VBox();
        vb.setSpacing(5);
        vb.setFillWidth(true);

        try {
            media.addAll(Files.loadMedia(defaultMediaURL));

            for(int i = 0; i<media.size(); i++){
                if(i%4 == 0){
                    HBox tmphb = new HBox();
                    tmphb.setSpacing(5);

                    hb.add(tmphb);
                }

                MediaView im = new MediaView(media.get(i));
                im.addEventFilter(MouseEvent.MOUSE_CLICKED, mediaViewClick);
                im.addEventFilter(MouseEvent.MOUSE_ENTERED, mediaViewEntered);
                im.addEventFilter(MouseEvent.MOUSE_EXITED, mediaViewLeaved);

                hb.get(hb.size()-1).getChildren().add(im);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        vb.getChildren().addAll(hb);
        canvas.setContent(vb);

    }

    @FXML
    void button_add_on_action(ActionEvent event) {

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

    }

    EventHandler<MouseEvent> mediaViewClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            MediaView mv = ((MediaView) e.getSource());
            label_test.setText("Clicked: " + mv.getMedia().getResUrl());
        }
    };

    EventHandler<MouseEvent> mediaViewEntered = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            ImageView im = ((ImageView) e.getSource());
            im.setOpacity(0.5);
        }
    };

    EventHandler<MouseEvent> mediaViewLeaved = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            ImageView im = ((ImageView) e.getSource());
            im.setOpacity(1);
        }
    };

}