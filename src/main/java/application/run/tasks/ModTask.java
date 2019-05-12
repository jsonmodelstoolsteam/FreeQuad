package application.run.tasks;

import application.Helper;
import application.run.ProgressPane;
import javafx.application.Platform;
import javafx.concurrent.Task;

public abstract class ModTask extends Task<Boolean> {
	
	protected final Helper helper;
	
	public ModTask(Helper helper) {
		this.helper = helper;
	}
	
	@Override
	protected Boolean call() throws Exception {
		boolean isLoad = canStart();
		if(isLoad) {
			Platform.runLater(() -> {
				ProgressPane pane = helper.getTaskManager().getProgressPane();
				pane.getBar().progressProperty().unbind();
				pane.getBar().progressProperty().bind(this.progressProperty());
			});
			start();
		}
		return isLoad;
	}
	
	@Override
	protected void succeeded() {
		helper.getTaskManager().removeTask(this);
	}
	
	public abstract boolean canStart();	
	protected abstract void start();
}