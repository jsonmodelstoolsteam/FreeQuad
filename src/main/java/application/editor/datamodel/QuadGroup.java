package application.editor.datamodel;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashSet;
import java.util.Set;

public class QuadGroup implements ModelEntry {

    public Set<ModelEntry> group;

    public QuadGroup() {
        group = new HashSet<>();
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
