package application.stages;

import application.Helper;
import application.stages.components.ModIconImage;
import application.stages.components.ModMenuBar;
import application.stages.components.ModScrollPane;
import javafx.embed.swing.SwingNode;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class StageEditor implements InitComponentV2 {
    @Override
    public boolean onShow(Helper helper, Object... params) {
        return true;
    }

    @Override
    public void reloadSession(Helper helper, boolean hide) {
    }

    @Override
    public Scene getScene(Helper helper) {


        //Визуальное расположение панелей
        VBox root = new VBox();
        //Меню
        ModMenuBar menuBar = new ModMenuBar(this, helper);
        //Тулбар
        ToolBar toolUp = new ToolBar();
        toolUp.setOrientation(Orientation.HORIZONTAL);
        toolUp.setFocusTraversable(false);
        toolUp.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        Label labelTools = new Label("Инструменты");
        Separator sepToolUp1 = new Separator(Orientation.VERTICAL);
        Label labelEditor = new Label("Редактор");
        Separator sepToolUp2 = new Separator(Orientation.VERTICAL);
        Label labelStructure = new Label("Структура");
        //Основная панель редактора
        BorderPane primaryBox = new BorderPane();
        ModScrollPane leftScroll = new ModScrollPane();
        leftScroll.setFitToWidth(true);
        //Левое меню
        VBox leftBox = new VBox();
        TitledPane paneTools = new TitledPane();
        paneTools.setText("Инструменты");
        TitledPane panePrimitives = new TitledPane();
        panePrimitives.setText("Примитивы");
        //Панель редактора
        Pane editorPane = new Pane();
        SwingNode swingNode = new SwingNode();
        editorPane.getChildren().add(swingNode);

        //Тут, возможно, панель редактора
        ModScrollPane rightScroll = new ModScrollPane();
        rightScroll.setFitToWidth(true);
        //Правое меню
        VBox rightBox = new VBox();
        TitledPane paneStructure = new TitledPane();
        paneStructure.setText("Структура модели");
        //Тут, возможно, панель работы с моделью (структура данных)

        //Установка размеров компонентов
        menuBar.setPrefWidth(25);
        toolUp.setPrefHeight(25);
        labelTools.setPrefWidth(190);
        labelEditor.setPrefWidth(685);
        leftBox.setPrefSize(200, 650);
        editorPane.setPrefSize(700, 650);
        rightBox.setPrefSize(200, 650);


        //Визуальное добавление панелей
        root.getChildren().addAll(menuBar, toolUp, primaryBox);
        //2
        toolUp.getItems().addAll(labelTools, sepToolUp1, labelEditor, sepToolUp2, labelStructure);
        //3
        primaryBox.setLeft(leftScroll);
        leftScroll.setContent(leftBox);
        leftBox.getChildren().addAll(paneTools, panePrimitives);
        primaryBox.setCenter(editorPane);

        primaryBox.setRight(rightScroll);
        rightScroll.setContent(rightBox);
        rightBox.getChildren().add(paneStructure);

        //Настройка окно
        return new Scene(root, 1100, 700);
    }

    @Override
    public String getTitle() {
        return "Редактор моделей";
    }

    @Override
    public ModIconImage getIcon(Helper helper) {
        return new ModIconImage(helper, "icon2.png");
    }
}