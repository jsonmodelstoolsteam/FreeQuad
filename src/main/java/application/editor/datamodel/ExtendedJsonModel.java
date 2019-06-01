package application.editor.datamodel;

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
    public TransformationMap transformations;//mutable; АРГХ! Когда я начинаю писать комменты в коде - значит все идет по пизде!
}
