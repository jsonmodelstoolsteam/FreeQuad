package application.editor.datamodel;

public class ExtendedJsonModel {

    public ExtendedJsonModel(QuadGroup rootGroup, TransformationMap transformations) {
        this.rootGroup = rootGroup;
        this.transformations = transformations;
    }

    public ExtendedJsonModel() {
        rootGroup = QuadGroup.empty;
        transformations = new TransformationMap();
    }

    public final QuadGroup rootGroup;
    public final TransformationMap transformations;//mutable; АРГХ! Когда я начинаю писать комменты в коде - значит все идет по пизде!
}
