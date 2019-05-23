package application;

import application.stages.SceneSource;
import application.stages.StageEditor;
import application.stages.StageStart;
import lwjgui.LWJGUIApplication;
import lwjgui.gl.GenericShader;
import lwjgui.gl.Renderer;
import lwjgui.scene.Context;
import lwjgui.scene.Window;
import lwjgui.scene.control.CheckBox;
import lwjgui.scene.control.Slider;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.system.MemoryStack.*;

public class Main extends LWJGUIApplication {
    public static Main instance;
    public static final int WIDTH = 320;
    public static final int HEIGHT = 240;

    public Helper helper;

    private static CheckBox spinBox;
    private static Slider slider;
    private static double rotation;
    Window window;


    private void initStage() {
        helper = new Helper();
        helper.createStage(new StageStart(helper), "Start");
        helper.createStage(new StageEditor(helper), "Editor");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(String[] args, Window window) {
        instance = this;
        this.window = window;

        // Render OpenGL Scene
        //window.setRenderingCallback(new RenderingCallbackTest());

        initStage();

        show(helper.getStage("Editor"));
        window.setPosition(100, 100);
        window.show();
    }


    public void show(SceneSource stage) {
        window.setScene(stage.getScene());
        window.setTitle(stage.getTitle());
        window.setIcon(stage.getIcon());
    }

    static class RenderingCallbackTest implements Renderer {
        private GenericShader shader;
        private int vao;
        private int vbo;

        public RenderingCallbackTest() {
            // Test shader
            shader = new GenericShader(); // Will load a testing vert/frag quad shader

            // Setup geometry
            int vertSize = 3; // vec3 in shader
            int texSize = 2; // vec2 in shader
            int colorSize = 4; // vec4 in shader
            int size = vertSize + texSize + colorSize; // Stride length
            int verts = 3; // Number of vertices
            int bytes = Float.BYTES; // Bytes per element (float)

            stackPush();
            {
                // Initial vertex data
                FloatBuffer buffer = stackMallocFloat(verts * size);
                buffer.put(-0.5f).put(+0.5f).put(0.0f);        // Vert 1 position
                buffer.put(new float[]{0.0f, 0.0f});        // Vert 1 texture
                buffer.put(new float[]{1.0f, 0.0f, 0.0f, 1.0f}); // Vert 1 color

                buffer.put(+0.5f).put(+0.5f).put(0.0f);        // Vert 2 position
                buffer.put(new float[]{0.0f, 0.0f});        // Vert 2 texture
                buffer.put(new float[]{0.0f, 1.0f, 0.0f, 1.0f}); // Vert 2 color

                buffer.put(+0.0f).put(-0.5f).put(0.0f);        // Vert 3 position
                buffer.put(new float[]{0.0f, 0.0f});        // Vert 3 texture
                buffer.put(new float[]{0.0f, 0.0f, 1.0f, 1.0f}); // Vert 3 color
                buffer.flip();

                // Generate buffers
                vbo = glGenBuffers();
                vao = glGenVertexArrays();

                // Upload Vertex Buffer
                glBindBuffer(GL_ARRAY_BUFFER, vbo);
                glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

                // Set attributes (automatically stored to currently bound VAO)
                glBindVertexArray(vao);
                glEnableVertexAttribArray(0); // layout 0 shader
                glEnableVertexAttribArray(1); // layout 1 shader
                glEnableVertexAttribArray(2); // layout 2 shader
                int vertOffset = 0;
                glVertexAttribPointer(0, vertSize, GL_FLOAT, false, size * bytes, vertOffset);
                int texOffset = vertSize * bytes;
                glVertexAttribPointer(1, texSize, GL_FLOAT, false, size * bytes, texOffset);
                int colorOffset = texOffset + texSize * bytes;
                glVertexAttribPointer(2, colorSize, GL_FLOAT, false, size * bytes, colorOffset);

                // Unbind
                glBindBuffer(GL_ARRAY_BUFFER, 0);
                glBindVertexArray(0);
            }
            stackPop();
        }

        @Override
        public void render(Context context) {
            // Clear to black
            glClearColor(0, 0, 0, 1);
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

            if (spinBox != null && spinBox.isChecked()) {
                rotation += 1.0e-3d;
                if (rotation > Math.PI)
                    rotation = -Math.PI;

                slider.setValue(Math.toDegrees(rotation));
            }

            // Bind shader for drawing
            shader.bind();
            shader.projectOrtho(-0.6f, -0.6f, 1.2f, 1.2f);
            shader.setWorldMatrix(new Matrix4f().rotateY((float) rotation));

            // Disable culling (just in case)
            GL11.glDisable(GL11.GL_CULL_FACE);

            // Render geometry
            glBindVertexArray(vao);
            glDrawArrays(GL_TRIANGLES, 0, 3);
        }
    }
}