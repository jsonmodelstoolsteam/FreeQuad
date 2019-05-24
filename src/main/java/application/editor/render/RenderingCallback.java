package application.editor.render;

import application.editor.datamodel.Quad;
import application.editor.datamodel.QuadGroup;
import application.stages.StageEditor;
import lwjgui.gl.Renderer;
import lwjgui.scene.Context;
import lwjgui.scene.layout.OpenGLPane;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class RenderingCallback implements Renderer {
    private final OpenGLPane editorPane;

    public RenderingCallback(OpenGLPane editorPane) {

        this.editorPane = editorPane;
    }

    @Override
    public void render(Context context) {

        // Clear to black
        glClearColor(1, 1, 1, 1);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

        GL11.glDisable(GL11.GL_CULL_FACE);

        // Render geometry
        glBegin(GL_QUADS);
        {
            render(StageEditor.state.model.rootGroup);
        }
        glEnd();
    }

    private void render(QuadGroup model) {
        model.groups.forEach(this::render);
        model.quads.forEach(this::render);
    }

    private void render(Quad quad) {
        quad.vertices.forEach(v -> {
            glColor3ub((byte) v.color.getRed(), (byte) v.color.getGreen(), (byte) v.color.getBlue());
            glVertex3f(v.x, v.y, v.z);
        });
    }
}
