package application.editor.datamodel;

import application.editor.datamodel.math.Matrix4;

import java.util.*;

public class TransformationMap extends HashMap<Perspective, Matrix4> {
    @Override
    public Matrix4 get(Object key) {
        return Optional.ofNullable(super.get(key)).orElse(Matrix4.identity);
    }
}
