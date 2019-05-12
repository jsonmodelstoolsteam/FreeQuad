package application.stages.components;

import application.Helper;
import javafx.scene.image.Image;

public class ModIconImage extends Image {

	public ModIconImage(Helper helper, String name) {
		super(helper.getRes(name));
	}
}