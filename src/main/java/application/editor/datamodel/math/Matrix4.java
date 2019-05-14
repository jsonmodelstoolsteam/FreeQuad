package application.editor.datamodel.math;

public class Matrix4 {

    double[][] matrix = new double[4][4];

    public Matrix4() {
        for (int i = 0; i < 4; i++)
            matrix[i][i] = 1;
    }

    public Matrix4(double m00, double m01, double m02, double m03,
                   double m10, double m11, double m12, double m13,
                   double m20, double m21, double m22, double m23,
                   double m30, double m31, double m32, double m33
    ) {
        matrix[0][0] = m00;
        matrix[0][1] = m01;
        matrix[0][2] = m02;
        matrix[0][3] = m03;

        matrix[1][0] = m10;
        matrix[1][1] = m11;
        matrix[1][2] = m12;
        matrix[1][3] = m13;

        matrix[2][0] = m20;
        matrix[2][1] = m21;
        matrix[2][2] = m22;
        matrix[2][3] = m23;

        matrix[3][0] = m30;
        matrix[3][1] = m31;
        matrix[3][2] = m32;
        matrix[3][3] = m33;
    }

    public static final Matrix4 identity = new Matrix4();

}
