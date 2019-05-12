package application.stages;

import application.Helper;
import application.stages.components.ModIconImage;
import application.stages.components.ModMenuBar;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StageEditor extends Stage implements InitComponentV2 {

    public StageEditor(Helper helper) {

        //Визуальное расположение панелей
        VBox root = new VBox();
        //Меню
        ModMenuBar menuBar = new ModMenuBar(this, helper);
        BorderPane primaryBox = new BorderPane();
        //Левое меню
        VBox leftBox = new VBox();
        //Тут, возможно, панель инструментов
        //Панел редактора
        Pane editorPane = new Pane();
        //Тут, возможно, панель редактора
        //Правое меню
        VBox rightBox = new VBox();
        //Тут, возможно, панель работы с файлами

        //Визуальное добавление панелей
        root.getChildren().addAll(menuBar, primaryBox);
        primaryBox.setLeft(leftBox);
        primaryBox.setCenter(editorPane);
        primaryBox.setRight(rightBox);

        //Настройка окна
        setScene(new Scene(root, 1100, 700));
        setResizable(false);
        setTitle("Редактор моделей");
        getIcons().add(new ModIconImage(helper, "icon2.png"));
    }

    @Override
    public boolean onShow(Helper helper, Object... params) {return true;}

    @Override
    public void reloadSession(Helper helper, boolean hide) {}
}