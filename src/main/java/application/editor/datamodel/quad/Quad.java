package application.editor.datamodel.quad;

import application.editor.datamodel.ModelEntry;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;
import java.util.UUID;

public class Quad implements ModelEntry {

    public transient final int id = UUID.randomUUID().hashCode();

    public final List<Vertex> vertices;
    public int tint;
    public ResourceLocation texture;
    private String name;

    public Quad(String name, ImmutableList<Vertex> vertices, int tint, ResourceLocation texture) {
        this.vertices = vertices;
        this.tint = tint;
        this.texture = texture;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Quad) {
            Quad otherAdmin = (Quad) o;
            EqualsBuilder builder = new EqualsBuilder();
            builder.append(vertices, otherAdmin.vertices);
            builder.append(tint, otherAdmin.tint);
            builder.append(texture, otherAdmin.texture);
            builder.append(id, otherAdmin.id);
            return builder.isEquals();
        } else
            return false;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(vertices);
        builder.append(tint);
        builder.append(texture);
        builder.append(id);
        return builder.toHashCode();
    }

    @Override
    public String name() {
        return name;
    }
}
