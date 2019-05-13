package application.stages;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import application.Helper;
import application.run.ProgressPane;
import application.run.tasks.TaskChange;
import application.stages.components.FileContextMenu;
import application.stages.components.JFile;
import application.stages.components.JStringConverter;
import application.stages.components.LabelWithFlag;
import application.stages.components.ModIconImage;
import application.stages.components.ModListCell;
import application.stages.components.ModMenuBar;
import application.stages.components.ModScrollPane;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StageChangeModel extends Stage implements InitComponentV2 {

	private TextField fieldStep;
	private int hashIndex = 0;
	private File catalog = null;
	private ObservableList<JFile> bList = FXCollections.observableArrayList();
	private ArrayList<File> deletedFile = new ArrayList<>();
	private boolean deleteWork;
	private ListView<File> list;
	private TimerTask task;
	private CheckBox allowDelete;
	private CheckBox allowExtension;
	private TextArea area;
	private boolean allFiles;
	private boolean changeCheck;

	public StageChangeModel(Helper helper) {

		deleteWork = false;
		allFiles = false;
		changeCheck = false;

		VBox root = new VBox();
			ModMenuBar modBar = new ModMenuBar(this, helper);
			HBox deleteBox = new HBox();
			deleteBox.setSpacing(5D);
				Label deleteLabel = new Label("Файлы были удалены с диска...");
				deleteLabel.setFont(helper.standardFontTitle);
				deleteLabel.setTextFill(Color.RED);
				Hyperlink deleteLink = new Hyperlink("Восстановить?");
				deleteLink.setTextFill(Color.GREEN);
			HBox boxTools = new HBox();
				VBox boxMarkers = new VBox();
					area = new TextArea();
					area.setScrollLeft(Double.MAX_VALUE);
					area.setScrollTop(Double.MAX_VALUE);
					area.setEditable(false);
					TitledPane paneMarkers = new TitledPane();
					paneMarkers.setText("Создание маркеров");
					paneMarkers.setFont(helper.standardFontTitle);
						VBox markersRoot = new VBox();
							HBox markersSelectBox = new HBox();
								ChoiceBox<JFile> choiceBoxMarkers = new ChoiceBox<>(bList);
								choiceBoxMarkers.setConverter(new JStringConverter(bList));
								Button plus = new Button("+");
								Button minus = new Button("-");
							TextField markName = new TextField();
							markName.setPromptText("Имя маркера...");
					TitledPane paneReplace = new TitledPane();
					paneReplace.setText("Изменение маркеров");
					paneReplace.setFont(helper.standardFontTitle);
						VBox replacePaneBox = new VBox();
							HBox markersReplaceBox = new HBox();
								TextField fieldChange = new TextField();
								LabelWithFlag labelImplicate = new LabelWithFlag(" --> ");
								labelImplicate.setFont(new Font(15));
								labelImplicate.setCursor(Cursor.HAND);
								TextField fieldReplace = new TextField();
								Hyperlink labelWarningMarkers = new Hyperlink("Маркер не выбран");
								labelWarningMarkers.setTextFill(Color.RED);
				VBox listPanel = new VBox();
					list = new ListView<>(FXCollections.observableArrayList());
					list.setCellFactory(filesListView -> new ModListCell(allowExtension));
					list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
					list.getSelectionModel().select(0);
					TitledPane paneCatalogSettings = new TitledPane();
					paneCatalogSettings.setText("Настройки списка");
					paneCatalogSettings.setFont(helper.standardFontTitle);
						VBox boxCatalogSettings = new VBox();
						boxCatalogSettings.setSpacing(5D);
							HBox boxChooser = new HBox();
							boxChooser.setSpacing(5D);
								Label labelChooser = new Label("Папка:");
								labelChooser.setFont(helper.standardFont);
								Button buttonChooser = new Button("Сменить");
								Button buttonRefresh = new Button("Обновить");
							allowDelete = new CheckBox("Удалять файлы с диска");
							allowDelete.setFont(helper.standardFont);
							allowExtension = new CheckBox("Отображать разрешения");
							allowExtension.setFont(helper.standardFont);

				ModScrollPane scroll = new ModScrollPane();
				scroll.setFitToWidth(true);
					VBox scrollBox = new VBox();
						TitledPane settings = new TitledPane();
						settings.setText("Настройки");
						settings.setFont(helper.standardFontTitle);
							VBox boxSetting = new VBox();
							boxSetting.setSpacing(7D);
								Label labelStep = new Label("Итераций на файл");
								labelStep.setFont(helper.standardFont);
								HBox boxIterable = new HBox();
									fieldStep = new TextField("0");
									fieldStep.setTooltip(new Tooltip("Установите 0, если неограничено"));
									Button plus2 = new Button("+");
									Button minus2 = new Button("-");
						TitledPane start = new TitledPane();
						start.setText("Запуск действий");
						start.setFont(helper.standardFontTitle);
							VBox boxStart = new VBox();
							boxStart.setSpacing(7D);
								Label applyFor = new Label("Применить для:");
								applyFor.setFont(helper.standardFont);
								HBox applyBox = new HBox();
									Button applySelect = new Button("Выделенных файлов");
									Button applyAll = new Button("Всех файлов");
						TitledPane progress = new TitledPane();
						progress.setText("Прогресс");
						progress.setFont(helper.standardFontTitle);
							ProgressPane progressPane = helper.getTaskManager().getProgressPane();

		boxMarkers.setPrefSize(330, 575);
		deleteBox.setPrefSize(1000, 30);
		list.setPrefSize(340, 575);
		boxSetting.setPrefWidth(330);
		boxStart.setPrefWidth(330);
		progressPane.setPrefWidth(330);
		choiceBoxMarkers.setPrefWidth(254);
		fieldChange.setPrefWidth(135);
		fieldReplace.setPrefWidth(135);
		fieldStep.setPrefWidth(254);
		minus.setPrefWidth(26);
		minus2.setPrefWidth(27);
		applySelect.setPrefWidth(170);
		applyAll.setPrefWidth(140);

		paneReplace.setExpanded(false);
		settings.setExpanded(false);
		start.setExpanded(false);
		progress.setExpanded(false);

		deleteBox.getChildren().addAll(deleteLabel, deleteLink);
		HBox.setMargin(deleteLabel, new Insets(2, 0, 0, 0));

		boxTools.getChildren().addAll(boxMarkers, listPanel, scroll);
			boxMarkers.getChildren().addAll(area, paneMarkers, paneReplace);
				//2
				paneMarkers.setContent(markersRoot);
					markersRoot.getChildren().addAll(markersSelectBox, markName);
						markersSelectBox.getChildren().addAll(choiceBoxMarkers, plus, minus);
				//3
				paneReplace.setContent(replacePaneBox);
					replacePaneBox.getChildren().addAll(markersReplaceBox, labelWarningMarkers);
						markersReplaceBox.getChildren().addAll(fieldChange, labelImplicate, fieldReplace);
			listPanel.getChildren().addAll(list, paneCatalogSettings);
				//2
				paneCatalogSettings.setContent(boxCatalogSettings);
					boxCatalogSettings.getChildren().addAll(boxChooser, allowDelete, allowExtension);
						//1
						boxChooser.getChildren().addAll(labelChooser, buttonChooser, buttonRefresh);
			scroll.setContent(scrollBox);
				scrollBox.getChildren().addAll(settings, start, progress);
					//1
					settings.setContent(boxSetting);
						boxSetting.getChildren().addAll(labelStep, boxIterable);
							boxIterable.getChildren().addAll(minus2, fieldStep, plus2);
					//2
					start.setContent(boxStart);
						boxStart.getChildren().addAll(applyFor, applyBox);
							applyBox.getChildren().addAll(applySelect, applyAll);
					//3
					progress.setContent(progressPane);

		root.getChildren().addAll(modBar, boxTools);

		allowExtension.setOnAction(e -> list.refresh());

		labelImplicate.setOnMouseClicked(e -> {
			if (!choiceBoxMarkers.getSelectionModel().isEmpty()) {
				labelImplicate.revert();
				choiceBoxMarkers.getSelectionModel().getSelectedItem().setRevert(labelImplicate.isRevert());
			}
		});

		area.selectedTextProperty().addListener((observable, oldValue, newValue) -> {
			if (!isDublicateName(newValue)) markName.setText(newValue);
		});
		area.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (oldValue && !newValue) {
				if (!helper.isChildFocused(boxMarkers)) markName.setText(null);
			}
		});

		plus2.setOnAction(e -> fieldStep.setText(Integer.toString(Integer.parseInt(fieldStep.getText()) + 1)));
		minus2.setOnAction(e -> fieldStep.setText(Integer.toString(Integer.parseInt(fieldStep.getText()) - 1)));

		labelWarningMarkers.setOnAction(e -> {
			InnerShadow shadow = new InnerShadow(10, Color.RED);
			shadow.setHeight(50D);
			shadow.setWidth(50D);
			choiceBoxMarkers.setEffect(shadow);
			if (!paneMarkers.isExpanded()) paneMarkers.setExpanded(true);
			paneReplace.setExpanded(false);
		});

		applyAll.setOnAction(event -> {
			if (!progress.isExpanded()) progress.setExpanded(true);
			helper.getTaskManager().addTaskToQueue(new TaskChange(helper, list.getItems(), bList, Integer.valueOf(fieldStep.getText())));
		});
		applySelect.setOnAction(event -> {
			if (!progress.isExpanded()) progress.setExpanded(true);
			helper.getTaskManager().addTaskToQueue(new TaskChange(helper, list.getSelectionModel().getSelectedItems(), bList, Integer.valueOf(fieldStep.getText())));
		});

		buttonRefresh.setOnAction(e -> {
			list.getItems().clear();
			File[] listOfFiles = catalog.listFiles();
			assert listOfFiles != null;
			for (File file : listOfFiles) {
				if (allFiles && file.isFile()) {
					try (Reader ignored1 = Files.newBufferedReader(file.toPath())) {
						list.getItems().add(file);
					} catch (IOException ignored) {
					}
				} else if (file.isFile() && file.getName().endsWith(".json")) list.getItems().add(file);
			}
			list.getSelectionModel().selectFirst();
		});
		buttonChooser.setOnAction(e -> {
			File direct = helper.initDirect(this, catalog, "Выберите папку с файлами");

			if (direct != null) {
				list.getItems().clear();
				catalog = direct;
				File[] listOfFiles = catalog.listFiles();
				assert listOfFiles != null;
				for (File file : listOfFiles) {
					if (allFiles && file.isFile()) {
						try (Reader ignored1 = Files.newBufferedReader(file.toPath())) {
							list.getItems().add(file);
						} catch (IOException ignored) {
						}
					} else if (file.isFile() && file.getName().endsWith(".json")) list.getItems().add(file);
				}
				list.getSelectionModel().selectFirst();
			}
		});

		if (!list.getItems().isEmpty()) helper.markDirtyText(list.getSelectionModel().getSelectedItem(), area);
		list.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					if (newValue != null) helper.markDirtyText(newValue, area);
				});

		Timer timer = new Timer(true);
		task = new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(() -> root.getChildren().remove(deleteBox));
				for (File f : deletedFile) f.delete();
				deleteWork = false;
			}
		};
		deleteLink.setOnAction(e -> {
			root.getChildren().remove(deleteBox);
			list.getItems().addAll(deletedFile);
			deletedFile.clear();
			deleteWork = false;
			task.cancel();
			timer.purge();
		});

		list.setContextMenu(new FileContextMenu((event -> helper.getHost().showDocument(list.getSelectionModel().getSelectedItem().toURI()
				.toString())), e -> {
			if (allowDelete.isSelected()) {
				deletedFile.addAll(list.getSelectionModel().getSelectedItems());
				list.getItems().removeAll(deletedFile);

				if (!deleteWork) {
					root.getChildren().add(1, deleteBox);
					deleteWork = true;
					task = new TimerTask() {
						@Override
						public void run() {
							Platform.runLater(() -> root.getChildren().remove(deleteBox));
							for (File f : deletedFile) f.delete();
							deleteWork = false;
						}
					};
					timer.schedule(task, 10000);
				} else {
					task.cancel();
					timer.purge();
					task = new TimerTask() {
						@Override
						public void run() {
							Platform.runLater(() -> root.getChildren().remove(deleteBox));
							for (File f : deletedFile) f.delete();
							deleteWork = false;
						}
					};
					timer.schedule(task, 10000);
				}
			} else {
				list.getItems().removeAll(list.getSelectionModel().getSelectedItems());
			}
		}));

		fieldStep.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("[0-9]*")) {
				if (!oldValue.equals("")) fieldStep.setText(oldValue);
				else fieldStep.setText("1");
			}
		});

		plus.setOnMouseReleased(event -> {
			if (plus.effectProperty().isNotNull().get()) plus.setEffect(null);
			if (!markName.getText().isEmpty() && isDublicateName(markName.getText())) {
				helper.initError("Такое имя уже существует!", "Попробуйте оставить поле пустым, и программа сама придумает имя");
				markName.setText(null);
				return;
			}
			String markText = area.getSelectedText().equals("") ? helper
					.initTextFieldDialog("Введите текст", "Это текст, который программа будет заменять. Попробуёте выделить текст в " +
							"редакторе над кнопками, чтобы это окно не появлялось", "")
					: area.getSelectedText();
			if (markText != null) {
				JFile file;
				if (!markName.getText().equals("")) {
					file = new JFile(markName.getText(), markText, markText);
					bList.add(file);
				} else {
					file = new JFile("Маркер" + hashIndex++, markText, markText);
					bList.add(file);
				}

				choiceBoxMarkers.getSelectionModel().select(file);
			}
		});

		fieldChange.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!choiceBoxMarkers.getSelectionModel().isEmpty())
				choiceBoxMarkers.getSelectionModel().getSelectedItem().edit(newValue);
		});

		fieldReplace.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!choiceBoxMarkers.getSelectionModel().isEmpty())
				choiceBoxMarkers.getSelectionModel().getSelectedItem().setReplace(newValue);
		});

		choiceBoxMarkers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			if (!choiceBoxMarkers.getSelectionModel().isEmpty()) {
				fieldChange.setText(choiceBoxMarkers.getSelectionModel().getSelectedItem().getText());
				fieldReplace.setText(choiceBoxMarkers.getSelectionModel().getSelectedItem().getReplace());
				if (newValue.isRevert() && !labelImplicate.isRevert()) labelImplicate.revert();
				else if (!newValue.isRevert() && labelImplicate.isRevert()) labelImplicate.revert();
				if (!paneReplace.isExpanded()) paneReplace.setExpanded(true);
				if (!settings.isExpanded()) settings.setExpanded(true);
				if (!start.isExpanded()) start.setExpanded(true);
				replacePaneBox.getChildren().remove(labelWarningMarkers);
			} else {
				if (labelImplicate.isRevert()) labelImplicate.revert();
				if (paneReplace.isExpanded()) paneReplace.setExpanded(false);
				if (settings.isExpanded()) settings.setExpanded(false);
				if (start.isExpanded()) start.setExpanded(false);
				if (!replacePaneBox.getChildren().contains(labelWarningMarkers))
					replacePaneBox.getChildren().add(1, labelWarningMarkers);
				fieldChange.setText(null);
				fieldReplace.setText(null);
			}
		});
		choiceBoxMarkers.setOnMouseReleased(e -> {
			if (choiceBoxMarkers.effectProperty().isNotNull().get()) choiceBoxMarkers.setEffect(null);
			if (choiceBoxMarkers.getSelectionModel().isEmpty()) {
				InnerShadow shadow = new InnerShadow(10, Color.RED);
				shadow.setHeight(50D);
				shadow.setWidth(50D);
				plus.setEffect(shadow);
			}
		});

		minus.setOnMouseReleased(event -> {
			if (!choiceBoxMarkers.getSelectionModel().isEmpty()) {
				bList.remove(choiceBoxMarkers.getSelectionModel().getSelectedItem());
				choiceBoxMarkers.getSelectionModel().selectFirst();
			}
		});

		setScene(new Scene(root, 1000, 600));
		sizeToScene();
		setResizable(false);
		getIcons().add(new ModIconImage(helper, "icon2.png"));
		String act = "Изменение моделей";
		setTitle(act);
	}

	private boolean isDublicateName(String name) {
		boolean isDublicate = false;
		for (JFile s : bList) {
			if (name.equals(s.getName())) {
				isDublicate = true;
				break;
			}
		}
		return isDublicate;
	}

	public void updateTextArea(Helper helper) {
		if (!list.getSelectionModel().isEmpty()) helper.markDirtyText(list.getSelectionModel().getSelectedItem(), area);
	}

	@Override
	public boolean onShow(Helper helper, Object... params) {
		if (params.length > 0) {
			if (((Boolean) params[0]) == allFiles) changeCheck = true;
			allFiles = !((Boolean) params[0]);
		}
		if (catalog == null) {
			File direct = helper.initDirect(this, new File(System.getProperty("user.home")), "Выберите папку с файлами");

			if (direct == null) helper.showLastStage();
			else {
				catalog = direct;
				initCatalog();
				list.getSelectionModel().selectFirst();
			}
		} else if (changeCheck) {
			list.getItems().clear();
			initCatalog();
			changeCheck = false;
			list.getSelectionModel().selectFirst();
		}
		return catalog != null;
	}

	private void initCatalog() {
		File[] listOfFiles = catalog.listFiles();
		assert listOfFiles != null;
		for (File file : listOfFiles) {
			if(file.isFile()) {
				if (allFiles) {
					try (Reader ignored = Files.newBufferedReader(file.toPath())) {
						list.getItems().add(file);
					} catch (IOException ignored) {
					}
				} else if (file.getName().endsWith(".json")) list.getItems().add(file);
			}
		}
	}

	@Override
	public void reloadSession(Helper helper, boolean hide) {
		if (!hide) {
			File direct = helper.initDirect(this, catalog, "Выберите папку с файлами");

			if (direct != null) {
				catalog = direct;
				list.getItems().clear();
				bList.clear();
				hashIndex = 0;
				allowDelete.setSelected(false);
				allowExtension.setSelected(false);
				area.setText(null);
				fieldStep.setText("0");
				initCatalog();
			}
		} else {
			list.getItems().clear();
			bList.clear();
			hashIndex = 0;
			allowDelete.setSelected(false);
			allowExtension.setSelected(false);
			fieldStep.setText("0");
			catalog = null;
			area.setText(null);
		}
	}
}