package application.editor.render;

import application.editor.EditorState;
import application.editor.datamodel.quad.Quad;
import application.editor.datamodel.quadgroup.QuadGroup;
import lwjgui.gl.Renderer;
import lwjgui.scene.Context;
import lwjgui.scene.layout.OpenGLPane;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class RenderingCallback implements Renderer {
    private final OpenGLPane editorPane;
    private final EditorState editorState;

    public RenderingCallback(OpenGLPane editorPane, EditorState editorState) {

        this.editorPane = editorPane;
        this.editorState = editorState;
    }

    @Override
    public void render(Context context) {
        float aspect = (float) (editorPane.getWidth() / editorPane.getHeight());
        GL11.glLoadIdentity();
        GL11.glOrtho(-aspect, aspect, -1, 1, -1, 1);

        // Clear to black
        glClearColor(1, 1, 1, 1);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

        GL11.glDisable(GL11.GL_CULL_FACE);

        // Render geometry
        glBegin(GL_QUADS);
        {
            render(editorState.model.root);
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
