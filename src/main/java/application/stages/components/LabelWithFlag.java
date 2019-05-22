package application.stages.components;

import lwjgui.scene.control.CheckBox;
import lwjgui.scene.control.Label;

public class LabelWithFlag extends CheckBox {

    public LabelWithFlag(String string) {
        super(string);
        setOnAction(null);
    }

    public void revert() {
        setChecked(!isChecked());
    }
}