package application.stages;

import application.Helper;
import lwjgui.geometry.Insets;
import lwjgui.paint.Color;
import lwjgui.scene.Scene;
import lwjgui.scene.control.*;
import lwjgui.scene.layout.*;

public class StageStart implements SceneSource {

    private final Helper helper;
    private ComboBox<String> choiceBoxAct;
    private CheckBox check;

    public StageStart(Helper helper) {
        this.helper = helper;
    }

    @Override
    public void reloadSession(Helper helper, boolean hide) {
        check.setChecked(true);
    }

    @Override
    public Scene getScene() {
        VBox root = new VBox();
        String ver1 = "1.12+";
        String act1 = "Изменение моделей";
        String act2 = "Создание моделей из .java";
        String act3 = "Создание моделей";
        String act4 = "Редактор моделей (alpha)";

        Label labelVer = new Label("Выберите версию:\n\n");
        Label labelAct = new Label("Выберите задачу:\n\n");

        ComboBox<String> choiceBoxVer = new ComboBox<>(ver1);

        choiceBoxAct = new ComboBox<>();
        choiceBoxVer.getItems().addAll(act1, act2, act3, act4);

        VBox box1 = new VBox();
        box1.setPadding(new Insets(20, 0, 0, 30));
        box1.getChildren().addAll(labelVer, choiceBoxVer);

        VBox box2 = new VBox();
        box2.setPadding(new Insets(0, 0, 0, 30));

        box2.getChildren().addAll(labelAct, choiceBoxAct);

        HBox boxOk = new HBox();

        check = new CheckBox("Только \".json\"");

        Button OK = new Button("Старт");
        OK.setPrefSize(50, 15);

        OK.setOnMouseReleased(event -> {
            String act = choiceBoxAct.getValue();

            if (act.equals(act1)) helper.showStage("ChangeModel");
            else if (act.equals(act2)) helper.showStage("FromJava");
            else if (act.equals(act3)) helper.showStage("Editor");
            else if (act.equals(act4)) helper.showStage("Editor");
        });

        boxOk.getChildren().addAll(check, OK);
        check.setChecked(true);

        //choiceBoxAct.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> check.setVisible(newValue == act1));

        //choiceBoxAct.getSelectionModel().select(0);

        root.getChildren().addAll(box1, box2, boxOk);
        root.setBackground(Color.LIGHT_BLUE);

        return new Scene(root, 255, 170);
    }

    @Override
    public String getTitle() {
        return "Выбор задач";
    }
}