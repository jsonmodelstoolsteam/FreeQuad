package application.stages;

import java.io.File;

import application.Helper;
import lwjgui.scene.Scene;

public interface SceneSource {

    public void reloadSession(Helper helper, boolean hide);

    Scene getScene();

    String getTitle();

    default File[] getIcon() {
        return new File[]{new File("icon2.png")};
    }
}