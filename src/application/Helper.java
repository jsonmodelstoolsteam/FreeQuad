package application;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.istack.internal.Nullable;

import application.functional.ButtonFunction;
import application.run.TaskManager;
import application.stages.InitComponentV2;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Helper {
	
	private ArrayList<Stage> listStage;
	private int selectedStage;
	private int lastStage;
	
	public final Charset charset;
	public final Font standardFont;
	public final Font standardFontTitle;
	public final Font bigFont;
	private TaskManager taskManager;
	private final HostServices host;
	
	public Helper(HostServices host) {
		this.host = host;
		charset = StandardCharsets.UTF_8;
		standardFont = new Font(12.5);
		standardFontTitle = new Font(13);
		bigFont = new Font(14);
		lastStage = 0;
		selectedStage = 0;
		listStage = new ArrayList<>();
		taskManager = null;
	}
	
	//Не используется:)
	public static void restartApplication() {
		try {
			final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
			File currentJar = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());

			if(!currentJar.getName().endsWith(".jar")) return;

			final ArrayList<String> command = new ArrayList<String>();
			command.add(javaBin);
			command.add("-jar");
			command.add(currentJar.getPath());

			final ProcessBuilder builder = new ProcessBuilder(command);
			builder.start();
			System.exit(0);
		} catch (URISyntaxException | IOException e) { e.printStackTrace(); }
	}
	
	public InputStream getRes(String name) {
		return Helper.class.getResourceAsStream("/" + name);	
	}
	
	public List<String> readFile(Path file) {
		List<String> list = new ArrayList<>();
		try { list = Files.readAllLines(file);
		} catch (IOException e) {e.printStackTrace();}		
		return list;
	}
	
	public void markDirtyText(File file, TextArea area) {
		List<String> list = readFile(file.toPath());
		area.clear();
		for(String s : list) {
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
	
	public void initConformationDialog(String header, String content, ButtonFunction funcOK, @Nullable ButtonFunction funcNO) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initStyle(StageStyle.UTILITY);
		((Button)alert.getDialogPane().lookupButton(ButtonType.OK)).setDefaultButton(false);
		((Button)alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setDefaultButton(true);
		alert.setTitle("Согласие");
		alert.setHeaderText(header);
		alert.setContentText(content);
		
		if (alert.showAndWait().get() == ButtonType.OK) funcOK.onClick();
		else if(funcNO != null) funcNO.onClick();	
	}
	
	@Nullable
	public String initTextFieldDialog(String title, String content, String labelContent) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle(title);
		dialog.setHeaderText(content);
		dialog.setContentText(labelContent);
		dialog.initStyle(StageStyle.UTILITY);
		dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
		dialog.getEditor().textProperty().addListener(listener -> {
			if(!dialog.getEditor().getText().equals("")) 
				dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
			else dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
		});
		Optional<String> result = dialog.showAndWait();
		return result.isPresent() ? result.get() : null;
	}
	
	public File initDirect(Stage stage, File directory, String title) {
		final DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(title);
		directoryChooser.setInitialDirectory(directory);
        File file = directoryChooser.showDialog(stage);
        return file;
	}
	
	public File initFileOpen(Stage stage, File directory, String title, String extension) {
		final FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(title);
		fileChooser.setInitialDirectory(directory);
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Рабочие файлы", extension));
		File file = fileChooser.showOpenDialog(stage);
		return file;
	}
	
	public void reloadAllSessions() {
		for(Stage s : listStage) {
			((InitComponentV2)s).reloadSession(this, true);
			s.hide();
		}
		lastStage = 0;
		selectedStage = 0;
		showStage(0);
	}
	
	public Stage getStage(int index) {
		return listStage.get(index);
	}
	
	public Stage getSelectedStage() {
		return getStage(selectedStage);
	}
	
	public Stage createStage(InitComponentV2 stageComp, int index) {
		Stage stage = (Stage) stageComp;
		stage.setOnCloseRequest(event -> {
			if(taskManager.getCurrentTask() != null) {
				event.consume();
				initConformationDialog("�� �������, ��� ������ ������� ���������?", "��������� �������� ��� "
						+ "�� ���������. ���� �� ������ ��, ��� ����� ��������� � ������� ������", 
						() -> {
							taskManager.setClose(true);
							stage.hide();
						}, null);
			}
			else Platform.exit();
		});
		listStage.add(index, stage);
		return stage;
	}
	
	@Nullable
	public Stage reloadPrimaryStage(int index) {
		if(listStage.isEmpty() && listStage.size() < 2) return null;		
		Stage stage = listStage.get(index);
		boolean load = ((InitComponentV2)stage).onShow(this);
		if(load) {
			this.lastStage = selectedStage;
			this.selectedStage = index;
		}
		return stage;
	}
	
	@Nullable
	public Stage showStage(int index, Object... params) {
		if(listStage.isEmpty() && listStage.size() < 2) return null;
		Stage stage = listStage.get(index);
		boolean load = ((InitComponentV2)stage).onShow(this, params);
		if(load) {
			listStage.get(selectedStage).hide();
			stage.centerOnScreen();
			stage.show();
			this.lastStage = selectedStage;
			this.selectedStage = index;
		}
		return stage;
	}
	
	@Nullable
	public Stage showLastStage() {
		Stage stage = null;
		if(lastStage != selectedStage) stage = showStage(getLastStage());
		return stage;
	}
	
	public int getLastStage() {
		return lastStage;
	}

	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}
	
	public TaskManager getTaskManager() {
		return taskManager;
	}

	public HostServices getHost() {
		return host;
	}
	
	public boolean isChildFocused(Parent parent) {
	    for (Node node : parent.getChildrenUnmodifiable()) {
	        if (node.isFocused()) return true;
	        else if (node instanceof Parent && isChildFocused((Parent)node)) return true;
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