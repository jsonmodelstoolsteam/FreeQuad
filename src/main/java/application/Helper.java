package application;

import application.functional.ButtonFunction;
import application.run.TaskManager;
import application.stages.InitComponentV2;
import application.stages.SceneSource;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

    private Map<String, SceneSource> listStage;
    private String selectedStage;
    private String lastStage;

    public final Charset charset;
    public final Font standardFont;
    public final Font standardFontTitle;
    public final Font bigFont;
    private TaskManager taskManager;

    public Helper() {
        charset = StandardCharsets.UTF_8;
        standardFont = new Font(12.5);
        standardFontTitle = new Font(13);
        bigFont = new Font(14);
        lastStage = "";
        selectedStage = "";
        listStage = new LinkedHashMap<>();
        taskManager = null;
    }

    //Не используется:)
    public static void restartApplication() {
        try {
            final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
            File currentJar = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());

            if (!currentJar.getName().endsWith(".jar")) return;

            final ArrayList<String> command = new ArrayList<>();
            command.add(javaBin);
            command.add("-jar");
            command.add(currentJar.getPath());

            final ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();
            System.exit(0);
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    public InputStream getRes(String name) {
        return Helper.class.getResourceAsStream("/" + name);
    }

    public List<String> readFile(Path file) {
        List<String> list = new ArrayList<>();
        try {
            list = Files.readAllLines(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void markDirtyText(File file, TextArea area) {
        List<String> list = readFile(file.toPath());
        area.clear();
        for (String s : list) {
            area.appendText(s + "\n");
        }
    }

    public void initError(String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("Ошибка");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.showAndWait();
    }

    public void initConformationDialog(String header, String content, ButtonFunction funcOK, ButtonFunction funcNO) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initStyle(StageStyle.UTILITY);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setDefaultButton(false);
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setDefaultButton(true);
        alert.setTitle("Согласие");
        alert.setHeaderText(header);
        alert.setContentText(content);

        if (alert.showAndWait().get() == ButtonType.OK) funcOK.onClick();
        else if (funcNO != null) funcNO.onClick();
    }

    public String initTextFieldDialog(String title, String content, String labelContent) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(title);
        dialog.setHeaderText(content);
        dialog.setContentText(labelContent);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
        dialog.getEditor().textProperty().addListener(listener -> {
            if (!dialog.getEditor().getText().equals(""))
                dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
            else dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
        });
        Optional<String> result = dialog.showAndWait();
        return result.isPresent() ? result.get() : null;
    }

    public File initDirect(File directory, String title) {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(title);
        directoryChooser.setInitialDirectory(directory);
        File file = directoryChooser.showDialog(null);
        return file;
    }

    public File initFileOpen(File directory, String title, String extension) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.setInitialDirectory(directory);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Рабочие файлы", extension));
        File file = fileChooser.showOpenDialog(null);
        return file;
    }

    public void reloadAllSessions() {
        for (SceneSource s : listStage.values()) {
            ((InitComponentV2) s).reloadSession(this, true);
        }
        lastStage = listStage.entrySet().iterator().next().getKey();
        selectedStage = lastStage;
        showStage(selectedStage);
    }

    public SceneSource getStage(String name) {
        return listStage.get(name);
    }

    public SceneSource getSelectedStage() {
        return getStage(selectedStage);
    }

    public SceneSource createStage(InitComponentV2 stageComp, String name) {
        listStage.put(name, stageComp);
        return stageComp;
    }

    public SceneSource reloadPrimaryStage(String name) {
        if (listStage.isEmpty() && listStage.size() < 2) return null;
        SceneSource stage = listStage.get(name);
        boolean load = ((InitComponentV2) stage).onShow(this);
        if (load) {
            lastStage = selectedStage;
            selectedStage = name;
        }
        return stage;
    }

    public SceneSource showStage(String name, Object... params) {
        if (listStage.size() < 2) return null;
        SceneSource stage = listStage.get(name);
        boolean load = ((InitComponentV2) stage).onShow(this, params);
        if (load) {
            Main.setScene(stage.getScene(this), stage.getTitle());
            lastStage = selectedStage;
            selectedStage = name;
        }
        return stage;
    }

    public SceneSource showLastStage() {
        SceneSource stage = null;
        if (lastStage != selectedStage) stage = showStage(getLastStage());
        return stage;
    }

    public String getLastStage() {
        return lastStage;
    }

    public void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    public TaskManager getTaskManager() {
        return taskManager;
    }


    public boolean isChildFocused(Parent parent) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            if (node.isFocused()) return true;
            else if (node instanceof Parent && isChildFocused((Parent) node)) return true;
        }
        return false;
    }

    public String replaceTimes(Pattern pattern, String replacement, String input, int times) {
        Matcher matcher = pattern.matcher(input);
        StringBuffer out = new StringBuffer();

        for (int i = 0; i < times && matcher.find(); i++) matcher.appendReplacement(out, replacement);

        return matcher.appendTail(out).toString();
    }
}