package application.editor;

import application.editor.datamodel.ExtendedJsonModel;
import application.editor.datamodel.quad.Quad;
import application.editor.datamodel.quad.Vertex;
import application.editor.tools.Tool;
import com.google.common.collect.ImmutableList;

import java.awt.*;

public class EditorState {
    public CameraPos cameraPos;
    public ExtendedJsonModel model;
    public Tool currentTool;


    public EditorState(CameraPos cameraPos, ExtendedJsonModel model, Tool currentTool) {
        this.cameraPos = cameraPos;
        this.model = model;
        this.currentTool = currentTool;
    }

    public EditorState() {
        cameraPos = new CameraPos(0, 0, 0, 0, 0, 0);
        model = new ExtendedJsonModel();
        addQuad(new Quad(ImmutableList.of(
                new Vertex(0, 0, 0, Color.MAGENTA),
                new Vertex(0, 1, 0, Color.MAGENTA),
                new Vertex(1, 1, 0, Color.WHITE),
                new Vertex(1, 0, 0, Color.MAGENTA)
        ), 0, null));
        currentTool = new application.editor.tools.Quad();
    }

    private void addQuad(Quad quad) {
        model.root.quads.add(quad);
    }
}
