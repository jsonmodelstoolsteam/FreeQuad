package application.run;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import application.run.tasks.ModTask;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;

public class TaskManager {

	private ArrayList<ModTask> listTask;
	private ModTask currentTask;
	private final ExecutorService executor;
	private ProgressPane pane;
	private boolean work;
    private boolean close;
	
	public TaskManager(ProgressPane pane) {
		listTask = new ArrayList<ModTask>();
		currentTask = null;
		executor = Executors.newSingleThreadExecutor(r -> {
			Thread t = new Thread(r);
	        t.setDaemon(true);
	        return t ;
	    });
		this.pane = pane;
		close = false;
		work = false;
	}
	
	public ExecutorService getExecutor() {
		return executor;
	}
	
	public void addTaskToQueue(ModTask task) {
		listTask.add(task);
		Platform.runLater(() -> pane.getUndertitle().setText(Integer.toString(listTask.size())));
		if(listTask.size() == 1) {
			currentTask = task;
			activate(task);
			work = true;
		}
	}
	
	public void activate(ModTask task) {
		executor.submit(task);
	}
	
	public void removeTask(ModTask task) {
		if(listTask.contains(task)) {
			listTask.remove(task);
			Platform.runLater(() ->  {
				pane.getUndertitle().setText(Integer.toString(listTask.size()));
			});
			if(listTask.size() > 0) {
				ModTask startTask = listTask.get(0);
				currentTask = startTask;
				activate(startTask);
			}
			else {
				currentTask = null;
				work = false;
				Platform.runLater(() -> {
					if(listTask.size() < 1)  {
						if(close) Platform.exit();
					}
					pane.getUndertitle().setText(Integer.toString(listTask.size()));
					pane.getBar().progressProperty().unbind();
					pane.getBar().setProgress(ProgressBar.INDETERMINATE_PROGRESS);
				});
			}
		}
	}
	
	public void setProgressPane(ProgressPane pane) {
		this.pane = pane;
	}
	
	public ProgressPane getProgressPane() {
		return pane;
	}
	
	public void setClose(boolean close) {
		this.close = close;
	}
	
	public boolean isClose() {
		return close;
	}
	
	public void setWork(boolean work) {
		this.work = work;
	}
	
	public boolean isWork() {
		return work;
	}
	
	public ModTask getCurrentTask() {
		return currentTask;
	}
}