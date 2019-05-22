package application.stages.components;

import application.Helper;
import application.stages.SceneSource;
import lwjgui.scene.control.*;
import org.lwjgl.glfw.GLFW;

public class ModMenuBar extends MenuBar {

    public ModMenuBar(SceneSource root, Helper helper) {

        Menu fileMenu = new Menu("Программа");
        {
            MenuItem newSes = new MenuItem("Перезапустить программу");
            newSes.setOnAction(event -> helper.reloadAllSessions());

            MenuItem newItem = new MenuItem("Новый проект");
            newItem.setOnAction(event -> root.reloadSession(helper, false));

            MenuItem newItemM = new MenuItem("Закрыть проект");
            newItemM.setOnAction(event -> {
                root.reloadSession(helper, true);
                helper.showStage("Start");
            });

            MenuItem menu = new MenuItem("Меню");
            menu.setOnAction(event -> helper.showStage("Start"));

            MenuItem exitItem = new MenuItem("Выход");
            exitItem.setOnAction(event -> GLFW.glfwWindowShouldClose(0));

            SeparatorMenuItem sepMenu = new SeparatorMenuItem();


            fileMenu.getItems().addAll(newItem, menu, sepMenu, newItemM, newSes, exitItem);
        }

        Menu editMenu = new Menu("Редактор");

        Menu helpMenu = new Menu("Помощь");
        {
            MenuItem about = new MenuItem("О программе");

            helpMenu.getItems().add(about);
        }

        getItems().addAll(fileMenu, editMenu, helpMenu);
    }
}