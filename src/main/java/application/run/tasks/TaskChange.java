package application.run.tasks;

import application.Helper;
import application.stages.components.JFile;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TaskChange extends ModTaskChange {

    private final ArrayList<JFile> listMarkers;
    private final int stepOnFile;

    public TaskChange(Helper helper, List<File> observableList, ObservableList<JFile> bList, int stepOnFile) {
        super(helper, observableList);
        listMarkers = new ArrayList<>(bList);
        this.stepOnFile = new Integer(stepOnFile);
    }

    @Override
    public boolean canStart() {
        return !listFiles.isEmpty() && !listMarkers.isEmpty();
    }

    @Override
    protected void start() {
        for (int i = 0; i < listFiles.size(); ++i) {
            Path path = listFiles.get(i).toPath();

            String writable;
            try {
                writable = new String(Files.readAllBytes(path), helper.charset);
                for (int j = 0; j < listMarkers.size(); ++j) {
                    JFile mark = listMarkers.get(j);
                    if (!mark.getReplace().equals(mark.getText())) {
                        if (stepOnFile != 0) {
                            if (!mark.isRevert()) {
                                writable = helper.replaceTimes(Pattern.compile(mark.getText()), mark.getReplace(), writable, stepOnFile);
                            } else {
                                writable = helper.replaceTimes(Pattern.compile(mark.getReplace()), mark.getText(), writable, stepOnFile);
                            }
                        } else if (stepOnFile == 0) {
                            if (!mark.isRevert()) {
                                writable = writable.replace(mark.getText(), mark.getReplace());
                            } else {
                                writable = writable.replace(mark.getReplace(), mark.getText());
                            }
                        }
                    }
                }
                Files.write(path, writable.getBytes(helper.charset));
            } catch (IOException e) {
                e.printStackTrace();
            }
            updateProgress(i, listFiles.size());
        }
    }

    @Override
    protected void succeeded() {
        super.succeeded();
    }
}