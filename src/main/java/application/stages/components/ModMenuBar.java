package application.stages.components;

import application.Helper;
import application.stages.InitComponentV2;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ModMenuBar extends MenuBar {

	public ModMenuBar(InitComponentV2 root, Helper helper) {

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
        exitItem.setOnAction(event -> ((Stage)root).fireEvent(new WindowEvent((Stage)root, 
        		WindowEvent.WINDOW_CLOSE_REQUEST)));

        SeparatorMenuItem sepMenu = new SeparatorMenuItem();
        
        MenuItem about = new MenuItem("О программе");

		Menu fileMenu = new Menu("Программа");
		Menu editMenu = new Menu("Редактор");
		editMenu.setDisable(true);
		Menu helpMenu = new Menu("Помощь");

		fileMenu.getItems().addAll(newItem, menu, sepMenu, newItemM, newSes, exitItem);	
		helpMenu.getItems().add(about);

		this.getMenus().addAll(fileMenu, editMenu, helpMenu);
	}	
}