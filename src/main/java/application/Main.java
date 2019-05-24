package application;

import application.stages.SceneSource;
import application.stages.StageEditor;
import application.stages.StageStart;
import lwjgui.LWJGUIApplication;
import lwjgui.scene.Window;
import lwjgui.scene.control.CheckBox;
import lwjgui.scene.control.Slider;
import org.lwjgl.glfw.GLFW;

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
        LWJGUIApplication.ModernOpenGL = false;
        launch(args);
    }

    @Override
    public void start(String[] args, Window window) {
        instance = this;
        this.window = window;

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

    public void exit() {
        GLFW.glfwSetWindowShouldClose(window.getContext().getWindowHandle(), true);
    }
}