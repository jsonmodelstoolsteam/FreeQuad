package application.editor.datamodel;

import application.editor.datamodel.math.Vec2;
import application.editor.datamodel.math.Vec3;

import java.awt.*;

public class Vertex {
    float x, y, z;
    float u, v;
    Color color;
    Vec3<Byte> normal;
    Vec2<Short> lightmap;
}
