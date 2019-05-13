package application.run;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ProgressPane extends VBox {

    private Label label = new Label("������������, ����� � �������:");
    private Label label2 = new Label(Integer.toString(0));
    private ProgressBar bar = new ProgressBar();

    public ProgressPane() {
        label.setFont(new Font(15));
        label2.setFont(new Font(13));

        getChildren().addAll(label, label2, bar);
        setSpacing(4);
        setAlignment(Pos.CENTER);
    }

    public Label getTitle() {
        return label;
    }

    public Label getUndertitle() {
        return label2;
    }

    public ProgressBar getBar() {
        return bar;
    }
}