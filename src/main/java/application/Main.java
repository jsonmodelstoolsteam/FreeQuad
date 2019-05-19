package application;

import application.run.ProgressPane;
import application.run.TaskManager;
import application.stages.*;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;

public class Main {

    static int weight;
    static int height;
    private Helper helper;

    private void initStage() {
        helper.createStage(new StageStart(), "Start");
        helper.createStage(new StageChangeModel(), "ChangeModel");
        helper.createStage(new StageFromJava(), "FromJava");
        helper.createStage(new StageEditor(), "Editor");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                initAndShowGUI();
            }
        });
    }

    private static JFrame frame;

    public static JFrame frame() {
        if (frame == null)
            frame = new JFrame("Swing and JavaFX");
        return frame;
    }

    private static JFXPanel fxPanel;

    static JFXPanel fxPanel() {
        if (fxPanel == null)
            fxPanel = new JFXPanel();
        return fxPanel;
    }

    private static void initAndShowGUI() {
        JFrame frame = frame();
        frame.add(fxPanel());
        frame.setSize(300, 200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Platform.runLater(() -> initFX(fxPanel()));
    }

    private static void initFX(JFXPanel fxPanel) {
        Main main = new Main();

        //Приложение сможет работать в фоне
        Platform.setImplicitExit(false);

        //Самый главный контент:)
        main.helper = new Helper();

        //Менеджер задач
        main.helper.setTaskManager(new TaskManager(new ProgressPane()));

        //Регистрация окон
        main.initStage();

        //Замена главного окна
        SceneSource primaryStage = main.helper.reloadPrimaryStage("Editor");

        Scene scene = primaryStage.getScene(main.helper);
        setScene(scene, primaryStage.getTitle());

        Canvas c = new Canvas();
        frame.add(c);
        frame.setVisible(true);
        try {
            Display.setParent(c);
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    public static void setScene(Scene scene, String title) {
        fxPanel.setScene(scene);
        frame().setSize((int) scene.getWidth(), (int) scene.getHeight());
        frame().setTitle(title);
    }
}