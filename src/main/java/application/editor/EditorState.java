package application.editor;

public class EditorState {
    public CameraPos cameraPos;
    public ExtendedJsonModel model;
    public Tool currentTool;


    public EditorState(CameraPos cameraPos, ExtendedJsonModel model, Tool currentTool) {
        this.cameraPos = cameraPos;
        this.model = model;
        this.currentTool = currentTool;
    }
}
