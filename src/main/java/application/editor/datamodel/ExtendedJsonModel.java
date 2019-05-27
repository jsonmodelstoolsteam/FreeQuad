package application.editor.datamodel;

import application.editor.datamodel.perspective.TransformationMap;
import application.editor.datamodel.quadgroup.QuadGroup;

public class ExtendedJsonModel {

    public ExtendedJsonModel(QuadGroup root, TransformationMap transformations) {
        this.root = root;
        this.transformations = transformations;
    }

    public ExtendedJsonModel() {
        root = QuadGroup.empty;
        transformations = new TransformationMap();
    }

    public QuadGroup root;
    public TransformationMap transformations;//mutable; АРГХ! Когда я начинаю писать комменты в коде - значит все идет по пизде!
}
