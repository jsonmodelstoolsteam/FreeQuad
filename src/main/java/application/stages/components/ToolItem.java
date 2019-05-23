package application.stages.components;

import application.LangUtil;
import lwjgui.scene.control.TreeItem;
import lwjgui.scene.image.Image;
import lwjgui.scene.image.ImageView;

public class ToolItem extends TreeItem<String> {
    public ToolItem(String label) {
        super(LangUtil.translateToLocal("tool." + label), new ImageView(new Image("freequad/tools/" + label + ".png")));
    }
}
