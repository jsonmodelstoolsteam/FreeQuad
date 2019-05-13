package application.stages.components;

import com.sun.istack.internal.Nullable;

import javafx.collections.ObservableList;
import javafx.util.StringConverter;

public class JStringConverter extends StringConverter<JFile> {

    private ObservableList<JFile> bList;

    public JStringConverter(ObservableList<JFile> list) {
        bList = list;
    }

    @Override
    @Nullable
    public JFile fromString(String string) {
        JFile fileI = null;
        for (JFile file : bList) {
            if (file.getName().equals(string)) fileI = file;
            break;
        }
        return fileI;
    }

    @Override
    public String toString(JFile file) {
        return file.getName();
    }
}