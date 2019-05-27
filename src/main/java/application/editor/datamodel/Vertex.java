package application.editor.datamodel;

import application.editor.datamodel.math.Vec2;
import application.editor.datamodel.math.Vec3;

import java.awt.*;

public class Vertex {
    public Vertex(float x, float y, float z, Color color) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = color;
    }

    public float x, y, z;
    public float u, v;
    public Color color;
    public Vec3<Byte> normal;
    public Vec2<Short> lightmap;
}
