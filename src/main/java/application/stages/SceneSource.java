package application.stages;

import application.Helper;
import application.stages.components.ModIconImage;
import javafx.scene.Scene;

public interface SceneSource {

    Scene getScene(Helper helper);

    String getTitle();

    ModIconImage getIcon(Helper helper);
}
