package application.stages;

import application.Helper;
import application.LangUtil;
import application.editor.EditorState;
import application.editor.render.RenderingCallback;
import application.stages.components.ModMenuBar;
import application.stages.components.ToolCategory;
import application.stages.components.ToolItem;
import application.stages.components.UnlocalisedLabel;
import lwjgui.geometry.Orientation;
import lwjgui.scene.Node;
import lwjgui.scene.Scene;
import lwjgui.scene.control.*;
import lwjgui.scene.layout.BorderPane;
import lwjgui.scene.layout.OpenGLPane;
import lwjgui.scene.layout.VBox;

import java.util.function.Consumer;

public class StageEditor implements SceneSource {
    public EditorState state = new EditorState();

    private final Helper helper;

    public StageEditor(Helper helper) {
        this.helper = helper;
    }

    @Override
    public void reloadSession(Helper helper, boolean hide) {
    }

    @Override
    public Scene getScene() {

        VBox root = new VBox();
        {
            root.setSpacing(1);

            //Меню
            ModMenuBar menuBar = new ModMenuBar(this, helper);
            menuBar.setPrefWidth(25);
            root.getChildren().add(menuBar);

            BorderPane hroot = new BorderPane();
            {
                //Левое меню
                ScrollPane leftScroll = new ScrollPane();
                {
                    leftScroll.setFillToParentHeight(true);
                    leftScroll.setPrefWidth(200);

                    TreeView<String> leftBox = new TreeView<>();
                    {
                        leftBox.setOnSelectItem(e -> {
                            TreeItem<String> lastSelectedItem = leftBox.getLastSelectedItem();
                            if (!(lastSelectedItem instanceof ToolItem))
                                leftBox.clearSelectedItems();
                        });
                        leftBox.setFillToParentHeight(true);
                        TreeItem<String> transforms = new ToolCategory("transforms");
                        TreeItem<String> navigate = new ToolCategory("navigate");
                        TreeItem<String> primitives = new ToolCategory("primitives");
                        {
                            TreeItem<String> quad = new ToolItem("quad", state);

                            primitives.getItems().addAll(quad);
                        }

                        leftBox.getItems().add(primitives);
                        leftBox.getItems().add(transforms);
                        leftBox.getItems().add(navigate);
                    }

                    leftScroll.setContent(leftBox);
                }
                OpenGLPane editorPane = new OpenGLPane();
                editorPane.setFillToParentHeight(true);
                editorPane.setFillToParentWidth(true);
                editorPane.setRendererCallback(new RenderingCallback(editorPane, state));

                editorPane.setOnMousePressed(e -> System.out.println("press"));
                editorPane.setOnMouseDragged(e -> System.out.println("drag"));
                editorPane.setOnMouseReleased(e -> System.out.println("release"));

                //Правое меню
                ScrollPane rightScroll = new ScrollPane();
                {

                    rightScroll.setFillToParentHeight(true);
                    rightScroll.setPrefWidth(200);

                    TreeView<String> rightBox = new TreeView<>();
                    {
                        rightBox.setFillToParentHeight(true);
                        TreeItem<String> modelStructure = new TreeItem<>(LangUtil.translateToLocal("modelStructure"));

                        rightBox.getItems().add(modelStructure);
                    }

                    rightScroll.setContent(rightBox);
                }


                hroot.setLeft(vBoxed(toolBared(new UnlocalisedLabel("title.toolbar")), leftScroll));
                hroot.setCenter(apply(vBoxed(toolBared(new UnlocalisedLabel("title.editor")), editorPane), r -> r.setFillToParentWidth(true)));
                hroot.setRight(vBoxed(toolBared(new UnlocalisedLabel("title.modelStructure")), rightScroll));
            }

            root.getChildren().add(hroot);
        }

        //Настройка окно
        return new Scene(root, 1100, 700);
    }

    private <T extends Node> T apply(T node, Consumer<T> f) {
        f.accept(node);
        return node;
    }

    private ToolBar toolBared(Label... label) {
        ToolBar r = new ToolBar();
        r.getItems().addAll(label);
        r.setFillToParentWidth(true);
        r.setOrientation(Orientation.HORIZONTAL);
        return r;
    }

    private VBox vBoxed(Node... a) {
        VBox r = new VBox();
        r.getChildren().addAll(a);
        r.setFillToParentHeight(true);
        return r;
    }

    @Override
    public String getTitle() {
        return "Редактор моделей";
    }

}