package application.stages;

import application.Helper;
import application.stages.components.ModMenuBar;
import lwjgui.geometry.Orientation;
import lwjgui.scene.Scene;
import lwjgui.scene.control.*;
import lwjgui.scene.layout.HBox;
import lwjgui.scene.layout.Pane;
import lwjgui.scene.layout.VBox;

public class StageEditor implements SceneSource {

    private final Helper helper;

    public StageEditor(Helper helper) {
        this.helper = helper;
    }

    @Override
    public void reloadSession(Helper helper, boolean hide) {
    }

    @Override
    public Scene getScene() {

        VBox root = new VBox();
        {
            root.setSpacing(1);

            //Меню
            ModMenuBar menuBar = new ModMenuBar(this, helper);
            menuBar.setPrefWidth(25);
            root.getChildren().add(menuBar);

            //Тулбар
            ToolBar toolUp = new ToolBar();
            {
                toolUp.setOrientation(Orientation.HORIZONTAL);
                toolUp.setPrefHeight(25);

                Label labelTools = new Label("Инструменты");
                Label labelEditor = new Label("Редактор");
                Label labelStructure = new Label("Структура");

                labelTools.setPrefWidth(190);
                labelEditor.setPrefWidth(685);

                toolUp.getItems().addAll(labelTools, labelEditor, labelStructure);
            }

            root.getChildren().add(toolUp);

            HBox hroot = new HBox();
            {
                //Левое меню
                ScrollPane leftScroll = new ScrollPane();
                {
                    leftScroll.setPrefSize(200, 650);

                    TreeView<String> leftBox = new TreeView<>();
                    {
                        TreeItem<String> instruments = new TreeItem<>("Инструменты");
                        TreeItem<String> primitives = new TreeItem<>("Примитивы");

                        leftBox.getItems().add(instruments);
                        leftBox.getItems().add(primitives);
                    }

                    leftScroll.setContent(leftBox);
                }

                //Панель редактора
                Pane editorPane = new Pane();
                editorPane.setPrefSize(700, 650);

                //Правое меню
                ScrollPane rightScroll = new ScrollPane();
                {
                    rightScroll.setPrefSize(200, 650);

                    TreeView<String> rightBox = new TreeView<>();
                    {
                        TreeItem<String> modelStructure = new TreeItem<>("Структура модели");

                        rightBox.getItems().add(modelStructure);
                    }

                    rightScroll.setContent(rightBox);
                }


                hroot.getChildren().addAll(leftScroll, editorPane, rightScroll);
            }

            root.getChildren().add(hroot);
        }

        //Настройка окно
        return new Scene(root, 1100, 700);
    }

    @Override
    public String getTitle() {
        return "Редактор моделей";
    }
}