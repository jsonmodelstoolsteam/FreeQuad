package application.editor.datamodel;

import application.editor.datamodel.perspective.TransformationMap;
import application.editor.datamodel.quadgroup.QuadGroup;

public class ExtendedJsonModel {

    public ExtendedJsonModel(QuadGroup rootGroup, TransformationMap transformations) {
        this.rootGroup = rootGroup;
        this.transformations = transformations;
    }

    public ExtendedJsonModel() {
        rootGroup = new QuadGroup();
        transformations = new TransformationMap();
    }

    public QuadGroup rootGroup;
    public TransformationMap transformations;
}
