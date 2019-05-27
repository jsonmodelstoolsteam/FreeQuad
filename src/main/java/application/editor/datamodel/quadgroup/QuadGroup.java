package application.editor.datamodel.quadgroup;

import application.editor.datamodel.ModelEntry;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class QuadGroup implements IQuadGroup {

    public static final QuadGroup empty = new QuadGroup();

    public final ImmutableSet<ModelEntry> group;

    public QuadGroup(ImmutableSet<ModelEntry> group) {
        this.group = group;
    }

    public QuadGroup() {
        group = ImmutableSet.of();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof QuadGroup) {
            QuadGroup otherAdmin = (QuadGroup) o;
            EqualsBuilder builder = new EqualsBuilder();
            builder.append(group, otherAdmin.group);
            return builder.isEquals();
        } else
            return false;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(group);
        return builder.toHashCode();
    }
}
