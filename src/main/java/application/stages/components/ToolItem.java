package application.stages.components;

import application.LangUtil;
import application.editor.EditorState;
import application.editor.tools.Tool;
import lwjgui.scene.control.TreeItem;
import lwjgui.scene.image.Image;
import lwjgui.scene.image.ImageView;

public class ToolItem extends TreeItem<String> {
    public ToolItem(String label, EditorState state) {
        super(LangUtil.translateToLocal("tool." + label), new ImageView(new Image("freequad/tools/" + label + ".png")));
        setOnMouseClicked(e ->
        {
            try {
                state.currentTool = (Tool) Class.forName("application.editor.tools." + label.substring(0, 1).toUpperCase() + label.substring(1)).newInstance();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }
}
