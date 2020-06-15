package po.collector.ui.main;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import po.collector.classes.media.MediaView;

public class TabElement extends VBox {
    private Label name = new Label();
    private Label subname = new Label();
    private Object source;
    private String sourceType;

    private String defColor = "eff0f1";
    private String hoverColor = "bfc0c1";


    public TabElement(String name, Object source, String sourceType, boolean enableHover){
        super();
        super.setFillWidth(true);
        super.setSpacing(5);
        super.getChildren().add(getNameLabel());
        //super.getChildren().add(getSubnameLabel());
        super.getChildren().add(new Separator());
        super.setPadding(new Insets(10));
        setBgColor(defColor);

        getSubnameLabel().setTextFill(Color.GRAY);
        setName(name);
        //setSubname(subname);
        setSource(source);
        setSourceType(sourceType);
        getNameLabel().setWrapText(true);
        getSubnameLabel().setWrapText(true);

        if(enableHover){
            super.addEventFilter(MouseEvent.MOUSE_ENTERED, mouseEntered);
            super.addEventFilter(MouseEvent.MOUSE_EXITED, mouseLeave);
        }

    }

    public Label getSubnameLabel() {
        return subname;
    }

    public Label getNameLabel() {
        return name;
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setSubname(String subname) {
        this.subname.setText(subname);
    }

    public void setSource(Object source) {
        this.source = source;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public Object getSource() {
        return source;
    }

    public String getSourceType() {
        return sourceType;
    }

    private void setBgColor(String hex){
        super.setStyle("-fx-background-color: #" + hex);
    }

    EventHandler<MouseEvent> mouseEntered = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            setBgColor(hoverColor);
        }
    };

    EventHandler<MouseEvent> mouseLeave = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            setBgColor(defColor);
        }
    };
}
