package application.stages;

import application.Helper;
import application.stages.components.ModIconImage;
import application.stages.components.ModMenuBar;
import application.stages.components.ObjectFile;
import application.stages.components.ObjectType;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StageFromJava extends Stage implements InitComponentV2 {

	private final String act = "Создание моделей из .java";
	private File file = null;
	private TableView<ObjectFile> list;

	@SuppressWarnings("unchecked")
	public StageFromJava(Helper helper) {
		VBox root = new VBox();
			ModMenuBar menuBar = new ModMenuBar(this, helper);
			ToolBar toolUp = new ToolBar();
			toolUp.setOrientation(Orientation.HORIZONTAL);
			toolUp.setFocusTraversable(false);
			toolUp.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
				Label labelList = new Label("Список объектов");
				labelList.setFont(helper.standardFontTitle);
				Separator sepBoxUp = new Separator();
				sepBoxUp.setOrientation(Orientation.VERTICAL);
				Label labelTemplate = new Label("Шаблоны");
				labelTemplate.setFont(helper.standardFontTitle);
			HBox boxTools = new HBox();
				VBox boxList = new VBox();
					list = new TableView<>();
					list.setEditable(true);
						TableColumn<ObjectFile, String> columnName = new TableColumn<>("");
						TableColumn<ObjectFile, ObjectType> columnType = new TableColumn<>("");
					TitledPane titleCatalogSetting = new TitledPane();
					titleCatalogSetting.setText("");
					titleCatalogSetting.setFont(helper.standardFontTitle);
						VBox boxTitleCatalogSetting = new VBox();
						boxTitleCatalogSetting.setSpacing(5D);
							HBox boxChooser = new HBox();
							boxChooser.setSpacing(5D);
								Label labelChooser = new Label("");
								labelChooser.setFont(helper.standardFont);
								Button buttonChooser = new Button("");
								Button buttonRefresh = new Button("");
				TabPane tabPane = new TabPane();
				tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
					Tab tab1 = new Tab("");
					Tab tab2 = new Tab("");
						HBox boxTab2 = new HBox();

		columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnName.setCellFactory(TextFieldTableCell.forTableColumn());
		columnName.setMinWidth(149);
		columnName.setOnEditCommit((CellEditEvent<ObjectFile, String> event) -> {
            TablePosition<ObjectFile, String> pos = event.getTablePosition();
            String newName = event.getNewValue();
            int row = pos.getRow();
            ObjectFile object = event.getTableView().getItems().get(row);
            object.setName(newName);
        });

        ObservableList<ObjectType> typeList = FXCollections.observableArrayList(ObjectType.values());
        columnType.setCellValueFactory(param -> {
			ObjectFile object = param.getValue();
			String typeCode = object.getType();
			ObjectType type = ObjectType.getByCode(typeCode);
			return new SimpleObjectProperty<>(type);
		});
        columnType.setCellFactory(ComboBoxTableCell.forTableColumn(typeList));
        columnType.setOnEditCommit((CellEditEvent<ObjectFile, ObjectType> event) -> {
            TablePosition<ObjectFile, ObjectType> pos = event.getTablePosition();
            ObjectType newType = event.getNewValue();
            int row = pos.getRow();
            ObjectFile object = event.getTableView().getItems().get(row);
            object.setName(newType.getCode());
        });
        columnType.setMinWidth(149);

        list.getColumns().addAll(columnName, columnType);

		menuBar.setPrefSize(800, 25);
		toolUp.setPrefSize(800, 30);
		labelList.setPrefWidth(287);
		list.setPrefSize(300, 395);
		tabPane.setPrefSize(500, 395);

		toolUp.getItems().addAll(labelList, sepBoxUp, labelTemplate);
		boxTools.getChildren().addAll(boxList, tabPane);
			//1
			boxList.getChildren().addAll(list, titleCatalogSetting);
				titleCatalogSetting.setContent(boxTitleCatalogSetting);
					boxTitleCatalogSetting.getChildren().addAll(boxChooser);
						boxChooser.getChildren().addAll(labelChooser, buttonChooser, buttonRefresh);
			//2
			tabPane.getTabs().addAll(tab1, tab2);
				//1
				//2
				tab2.setContent(boxTab2);

		root.getChildren().addAll(menuBar, toolUp, boxTools);

		buttonChooser.setOnAction(e -> openExplorer(helper));

		setScene(new Scene(root, 800, 450));
		sizeToScene();
		setResizable(false);
		getIcons().add(new ModIconImage(helper, "icon2.png"));
		setTitle(act);
	}

	private void openExplorer(Helper helper) {
		File selectedFile = helper.initFileOpen(this, new File(System.getProperty("user.home")), "Выбор .java файла", "*.java");
		if (selectedFile != null) readFromFile(selectedFile);
	}

	private void readFromFile(File selectedFile) {
		file = selectedFile;
		try {
			Files.lines(Paths.get(file.toURI())).forEach(s -> {
				if(s.contains("public") && s.contains("static") && (s.contains("Block") || s.contains("Item"))) {
					String[] str = s.split(" ");
					for(int i = 0; i < str.length; ++i) {
						if(str[i].equals("Block") || str[i].equals("Item")) {
							String name = str[i+1];
							for(char ch : name.toCharArray()) {
								if(Character.isUpperCase(ch)) {
									name = name.replaceFirst(String.valueOf(ch), "_" + String.valueOf(ch).toLowerCase());
								}
							}
							ObjectFile object = new ObjectFile(name, String.valueOf(str[i].charAt(0)));
							list.getItems().add(object);
						}
					}
				}
			});
		}
		catch (IOException e) {e.printStackTrace();}
		list.getSelectionModel().selectFirst();
	}

	@Override
	public boolean onShow(Helper helper, Object... params) {
		return true;
	}

	@Override
	public void reloadSession(Helper helper, boolean hide) {
		list.getItems().clear();
	}
}