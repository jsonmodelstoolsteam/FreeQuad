package application.stages;

import application.Helper;
import application.stages.components.ModIconImage;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StageStart extends Stage implements InitComponentV2 {

	private ChoiceBox<String> choiceBoxAct;
	private CheckBox check;

	public StageStart(Helper helper) {
		VBox root = new VBox();
		String ver1 = "1.12+";
		String act1 = "Изменение моделей";
		String act2 = "Создание моделей из .java";
		String act3 = "Создание моделей";
		String act4 = "Редактор моделей (alpha)";
        
        Label labelVer = new Label("Выберите версию:\n\n");
        Label labelAct = new Label("Выберите задачу:\n\n");

		ChoiceBox<String> choiceBoxVer = new ChoiceBox<>(FXCollections.observableArrayList(ver1));
        choiceBoxVer.getSelectionModel().select(0);
        choiceBoxVer.setDisable(true);
        choiceBoxAct = new ChoiceBox<>(FXCollections.observableArrayList(act1, act2, act3, act4));
       
		VBox box1 = new VBox(labelVer, choiceBoxVer);
		box1.setPadding(new Insets(20, 0, 0, 30));
		
		VBox box2 = new VBox(labelAct, choiceBoxAct);
		box2.setPadding(new Insets(0, 0, 0, 30));
		
		HBox boxOk = new HBox();
		
		check = new CheckBox("Только \".json\"");
		check.setVisible(false);

		Button OK = new Button("Старт");
		OK.setPrefSize(50, 15);
		OK.setCursor(Cursor.HAND);
		HBox.setMargin(OK, new Insets(6, 0, 6, 0));
		HBox.setMargin(check, new Insets(10, 27, 6, 30));
		
		OK.setOnMouseReleased(event -> {
			String act = choiceBoxAct.getSelectionModel().getSelectedItem();
			
			if(act.equals(act1)) helper.showStage(1, check.isSelected());
			else if(act.equals(act2)) helper.showStage(2);
			else if(act.equals(act1)) ;
			else if(act.equals(act3)) ;
			else if(act.equals(act4)) ;
		});
		
		boxOk.getChildren().addAll(check, OK);
		check.setSelected(true);
		
		choiceBoxAct.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> check.setVisible(newValue == act1));
		
		choiceBoxAct.getSelectionModel().select(0);
		
		root.getChildren().addAll(box1, box2, boxOk);
		root.setBackground(new Background(new BackgroundFill(Color.LIGHTSTEELBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
		
		setScene(new Scene(root, 255, 170));
		setResizable(false);
		setTitle("Выбор задач");
		getIcons().add(new ModIconImage(helper, "icon1.png"));
	}

	@Override public boolean onShow(Helper helper, Object... params) {return true;}
	@Override public void reloadSession(Helper helper, boolean hide) {
		check.setSelected(true);
	}
}