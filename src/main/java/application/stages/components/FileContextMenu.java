package application.stages.components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class FileContextMenu extends ContextMenu {

    public FileContextMenu(EventHandler<ActionEvent> func2, EventHandler<ActionEvent> func1) {
        MenuItem del = new MenuItem("Удалить");
        del.setOnAction(func1);
        MenuItem show = new MenuItem("Отрыть в системном редакторе");
        show.setOnAction(func2);
        getItems().addAll(del, show);
    }
}