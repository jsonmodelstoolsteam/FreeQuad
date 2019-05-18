package application.editor.datamodel;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class QuadGroup {

    public static final QuadGroup empty = new QuadGroup();

    public final ImmutableSet<Quad> quads;
    public final ImmutableSet<QuadGroup> groups;

    public QuadGroup() {
        quads = ImmutableSet.of();
        groups = ImmutableSet.of();
    }

    public QuadGroup(ImmutableSet<Quad> quads, ImmutableSet<QuadGroup> groups) {
        this.quads = quads;
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof QuadGroup) {
            QuadGroup otherAdmin = (QuadGroup) o;
            EqualsBuilder builder = new EqualsBuilder();
            builder.append(quads, otherAdmin.quads);
            builder.append(groups, otherAdmin.groups);
            return builder.isEquals();
        } else
            return false;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(quads);
        builder.append(groups);
        return builder.toHashCode();
    }
}
