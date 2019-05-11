package application.stages.components;

import java.io.File;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;

public class ModListCell extends ListCell<File> {

	private CheckBox extensions;

	public ModListCell(CheckBox extensions) {
		this.extensions = extensions;
	}

	public CheckBox isExtensions() {
		return extensions;
	}

	public void setExtensions(CheckBox extensions) {
		this.extensions = extensions;
	}

	@Override
	protected void updateItem(File name, boolean empty) {
		super.updateItem(name, empty);

		if (empty || name == null) setText(null);
		else {
			if (!extensions.isSelected()) {
				setText(name.getName().substring(0, name.getName().lastIndexOf(".")));
			} else setText(name.getName());
		}
	}
}