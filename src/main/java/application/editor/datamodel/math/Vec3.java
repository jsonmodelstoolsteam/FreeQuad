package application.editor.datamodel.math;

public class Vec3<E extends Number> {

    public final E x, y, z;

    public Vec3(E x, E y, E z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
