package application.stages.components;

import application.LangUtil;
import lwjgui.scene.control.TreeItem;

public class ToolCategory extends TreeItem<String> {
    public ToolCategory(String label) {
        super(LangUtil.translateToLocal("toolcat." + label));
        setExpanded(true);
    }
}
