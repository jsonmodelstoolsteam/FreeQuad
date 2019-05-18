package application.editor;

import application.editor.datamodel.ExtendedJsonModel;
import application.editor.datamodel.Quad;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.tuple.Pair;

public class EditorState {
    public CameraPos cameraPos;
    public ExtendedJsonModel model;
    public ImmutableList<Pair<Lens, Quad>> selection;
    public Tool currentTool;


    public EditorState(CameraPos cameraPos, ExtendedJsonModel model, Tool currentTool) {
        this.cameraPos = cameraPos;
        this.model = model;
        this.currentTool = currentTool;
    }
}
