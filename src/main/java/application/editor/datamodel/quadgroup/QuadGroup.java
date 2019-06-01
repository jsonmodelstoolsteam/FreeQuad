package application.editor.datamodel.quadgroup;

import application.editor.datamodel.ModelEntry;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashMap;
import java.util.Map;

public class QuadGroup implements ModelEntry {

    public Map<String, ModelEntry> group;

    public QuadGroup() {
        group = new HashMap<>();
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
