package application.run.tasks;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import application.Helper;

public abstract class ModTaskChange extends ModTask {
	
	protected final ArrayList<File> listFiles;
	
	public ModTaskChange(Helper helper, List<File> listFiles) {
		super(helper);
		this.listFiles = new ArrayList<File>(listFiles);
	}
}