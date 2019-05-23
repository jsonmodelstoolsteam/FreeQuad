package application.stages.components;

import application.LangUtil;
import lwjgui.scene.control.Label;

public class UnlocalisedLabel extends Label {
    public UnlocalisedLabel(String label) {
        super(LangUtil.translateToLocal(label));
    }
}
