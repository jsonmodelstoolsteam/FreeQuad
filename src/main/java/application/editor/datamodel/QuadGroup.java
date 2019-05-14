package application.editor.datamodel;

import com.google.common.collect.ImmutableList;

public class QuadGroup {

    public static final QuadGroup empty = new QuadGroup();

    public final ImmutableList<Quad> quads;
    public final ImmutableList<QuadGroup> groups;

    public QuadGroup() {
        quads = ImmutableList.of();
        groups = ImmutableList.of();
    }

    public QuadGroup(ImmutableList<Quad> quads, ImmutableList<QuadGroup> groups) {
        this.quads = quads;
        this.groups = groups;
    }
}
