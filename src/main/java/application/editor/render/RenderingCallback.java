package application.editor.render;

import application.editor.CameraPos;
import application.editor.EditorState;
import application.editor.datamodel.ModelEntry;
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

        glScaled(0.5, 0.5, 0.5);

        CameraPos cameraPos = editorState.cameraPos;
        glRotated(cameraPos.getPitch(), 1, 0, 0);
        glRotated(cameraPos.getYaw(), 0, 1, 0);


        renderBasePlane();

        // Render geometry
        glBegin(GL_QUADS);
        {
            renderModel(editorState.model.rootGroup);
        }
        glEnd();
    }


    private void renderBasePlane() {
        glBegin(GL_LINES);
        {

            glColor3f(1, 0, 1);
            for (int i = -10; i <= 10; i++) {
                glVertex3f(-10, i, -1);
                glVertex3f(10, i, -1);
            }
            for (int i = -10; i <= 10; i++) {
                glVertex3f(i, -10, -1);
                glVertex3f(i, 10, -1);
            }
        }
        glEnd();
    }

    private void renderModel(ModelEntry model) {
        if (model instanceof QuadGroup) {
            ((QuadGroup) model).group.values().forEach(this::renderModel);
        } else if (model instanceof Quad)
            renderQuad((Quad) model);
    }

    private void renderQuad(Quad quad) {
        quad.vertices.forEach(v -> {
            glColor3ub((byte) v.color.getRed(), (byte) v.color.getGreen(), (byte) v.color.getBlue());
            glVertex3f(v.x, v.y, v.z);
        });
    }
}
