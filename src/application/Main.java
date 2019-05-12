package application;

import application.run.ProgressPane;
import application.run.TaskManager;
import application.stages.StageChangeModel;
import application.stages.StageFromJava;
import application.stages.StageStart;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
	
	static int weight;
	static int height;
	
	protected Parameters par;
	Helper helper;
	
	@Override
	public void start(Stage primaryStage) {	
		
		//Приложение сможет работать в фоне
		Platform.setImplicitExit(false);
		
		//Самый главный контент:)
		helper = new Helper(getHostServices());
		par = getParameters();
		
		//Менеджер задач
		helper.setTaskManager(new TaskManager(new ProgressPane()));
		
		//Регистрация окон
		initStage();
		
		//Замена главного окна
		primaryStage = helper.reloadPrimaryStage(0);
		
		//Показ главного окна
		primaryStage.show();
	}
	private void initStage() {
		helper.createStage(new StageStart(helper), 0);
		helper.createStage(new StageChangeModel(helper), 1);
		helper.createStage(new StageFromJava(helper), 2);
	}
	
	public static void main(String[] args) {
		weight = (int) Screen.getPrimary().getVisualBounds().getWidth();
		height = (int) Screen.getPrimary().getVisualBounds().getHeight();
		launch(args);
	}
}