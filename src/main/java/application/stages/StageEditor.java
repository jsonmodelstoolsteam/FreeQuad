package application.stages;

import application.Helper;
import application.stages.components.ModMenuBar;
import application.stages.components.ToolCategory;
import application.stages.components.ToolItem;
import lwjgui.geometry.Orientation;
import lwjgui.scene.Node;
import lwjgui.scene.Scene;
import lwjgui.scene.control.*;
import lwjgui.scene.layout.BorderPane;
import lwjgui.scene.layout.VBox;

import java.util.function.Consumer;

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

            BorderPane hroot = new BorderPane();
            {
                //Левое меню
                ScrollPane leftScroll = new ScrollPane();
                {
                    leftScroll.setFillToParentHeight(true);
                    leftScroll.setPrefWidth(200);

                    TreeView<String> leftBox = new TreeView<>();
                    {
                        leftBox.setFillToParentHeight(true);
                        TreeItem<String> transforms = new ToolCategory("Преобразования");
                        TreeItem<String> navigate = new ToolCategory("Навигация");
                        TreeItem<String> primitives = new ToolCategory("Примитивы");
                        {
                            TreeItem<String> quad = new ToolItem("quad");

                            primitives.getItems().addAll(quad);
                        }

                        leftBox.getItems().add(primitives);
                        leftBox.getItems().add(transforms);
                        leftBox.getItems().add(navigate);
                    }

                    leftScroll.setContent(leftBox);
                }

                //Правое меню
                ScrollPane rightScroll = new ScrollPane();
                {

                    rightScroll.setFillToParentHeight(true);
                    rightScroll.setPrefWidth(200);

                    TreeView<String> rightBox = new TreeView<>();
                    {
                        rightBox.setFillToParentHeight(true);
                        TreeItem<String> modelStructure = new TreeItem<>("Структура модели");

                        rightBox.getItems().add(modelStructure);
                    }

                    rightScroll.setContent(rightBox);
                }


                hroot.setLeft(vBoxed(toolBared(new Label("Инструменты")), leftScroll));
                hroot.setCenter(apply(vBoxed(toolBared(new Label("Редактор"))), r -> r.setFillToParentWidth(true)));
                hroot.setRight(vBoxed(toolBared(new Label("Структура")), rightScroll));
            }

            root.getChildren().add(hroot);
        }

        //Настройка окно
        return new Scene(root, 1100, 700);
    }

    private <T extends Node> T apply(T node, Consumer<T> f) {
        f.accept(node);
        return node;
    }

    private ToolBar toolBared(Label... label) {
        ToolBar r = new ToolBar();
        r.getItems().addAll(label);
        r.setFillToParentWidth(true);
        r.setOrientation(Orientation.HORIZONTAL);
        return r;
    }

    private VBox vBoxed(Node... a) {
        VBox r = new VBox();
        r.getChildren().addAll(a);
        r.setFillToParentHeight(true);
        return r;
    }

    @Override
    public String getTitle() {
        return "Редактор моделей";
    }
}