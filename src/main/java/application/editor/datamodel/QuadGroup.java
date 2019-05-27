package application.editor.datamodel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashSet;
import java.util.Set;

public class QuadGroup {

    public static final QuadGroup empty = new QuadGroup();

    public Set<Quad> quads;
    public Set<QuadGroup> groups;

    public QuadGroup() {
        quads = new HashSet<>();//ImmutableSet.of();
        groups = new HashSet<>();//ImmutableSet.of();
    }

    public QuadGroup(Set<Quad> quads, Set<QuadGroup> groups) {
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
