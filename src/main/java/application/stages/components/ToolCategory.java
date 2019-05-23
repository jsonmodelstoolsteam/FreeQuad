package application.stages.components;

import lwjgui.scene.control.TreeItem;

public class ToolCategory extends TreeItem<String> {
    public ToolCategory(String label) {
        super(label);
        setExpanded(true);
    }
}
