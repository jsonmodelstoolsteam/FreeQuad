package application.stages;

import application.Helper;
import lwjgui.gl.Renderer;
import lwjgui.scene.Scene;

import java.io.File;

public interface SceneSource {

    public void reloadSession(Helper helper, boolean hide);

    Scene getScene();

    String getTitle();

    default File[] getIcon() {
        return new File[]{new File("icon2.png")};
    }
}