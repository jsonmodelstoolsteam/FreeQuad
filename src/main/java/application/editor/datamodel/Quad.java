package application.editor.datamodel;

import com.google.common.collect.ImmutableList;

public class Quad {

    public final ImmutableList<Vertex> vertices;
    public final int tint;
    public final ResourceLocation texture;

    public Quad(ImmutableList<Vertex> vertices, int tint, ResourceLocation texture) {
        this.vertices = vertices;
        this.tint = tint;
        this.texture = texture;
    }
}
