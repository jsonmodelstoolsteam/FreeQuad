package application.stages.components;

import application.Helper;
import application.Main;
import application.stages.SceneSource;
import lwjgui.scene.control.*;
import org.lwjgl.glfw.GLFW;

import static application.LangUtil.translateToLocal;

public class ModMenuBar extends MenuBar {

    public ModMenuBar(SceneSource root, Helper helper) {

        Menu fileMenu = new Menu(translateToLocal("menu.program"));
        {
            MenuItem newSes = new MenuItem(translateToLocal("menu.program.reload"));
            newSes.setOnAction(event -> helper.reloadAllSessions());

            MenuItem newItem = new MenuItem(translateToLocal("menu.program.newproject"));
            newItem.setOnAction(event -> root.reloadSession(helper, false));

            MenuItem newItemM = new MenuItem(translateToLocal("menu.program.closeproject"));
            newItemM.setOnAction(event -> {
                root.reloadSession(helper, true);
                helper.showStage("Start");
            });

            MenuItem menu = new MenuItem(translateToLocal("menu.program.mainmenu"));
            menu.setOnAction(event -> helper.showStage("Start"));

            MenuItem exitItem = new MenuItem(translateToLocal("menu.program.exit"));
            exitItem.setOnAction(event -> Main.instance.exit());

            SeparatorMenuItem sepMenu = new SeparatorMenuItem();


            fileMenu.getItems().addAll(newItem, menu, sepMenu, newItemM, newSes, exitItem);
        }

        Menu editMenu = new Menu(translateToLocal("menu.editor"));

        Menu helpMenu = new Menu(translateToLocal("menu.help"));
        {
            MenuItem about = new MenuItem(translateToLocal("menu.help.about"));

            helpMenu.getItems().add(about);
        }

        getItems().addAll(fileMenu, editMenu, helpMenu);
    }
}