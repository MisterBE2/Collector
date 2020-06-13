package po.collector.ui.main;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import po.collector.classes.Collection;
import po.collector.classes.Media;
import po.collector.classes.Tag;

public class MainController {
    private String defaultMediaURL = "C:\\PO";
    @FXML
    private Tab tab_collections;

    @FXML
    private Tab tab_tags;

    @FXML
    private Tab tab_Files;

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
        System.out.println("init");
        try (Stream<Path> walk = Files.walk(Paths.get(this.defaultMediaURL))) {

            List<String> result = walk.map(Path::toString).filter(f -> f.endsWith(".jpg")).collect(Collectors.toList());

            result.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }

        text_field_search.setText("test ttt");
    }

    @FXML
    void button_add_on_action(ActionEvent event) {

    }

    @FXML
    void button_settings_on_action(ActionEvent event) {

    }

    @FXML
    void tab_on_select(ActionEvent event) {

    }


    @FXML
    void text_field_search_on_key_release(KeyEvent event) {

    }

}
