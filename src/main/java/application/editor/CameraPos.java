package application.editor;

public class CameraPos {
    public double x, y, z;
    private double yaw, pitch, roll;
    private double maxAngle = Math.PI * 2;

    public CameraPos(double x, double y, double z, double yaw, double pitch, double roll) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
    }

    public double getYaw() {
        return yaw;
    }

    public void setYaw(double yaw) {
        //todo: make % to lazy operation
        this.yaw = yaw % maxAngle;
    }

    public double getPitch() {
        return pitch;
    }

    public void setPitch(double pitch) {
        this.pitch = pitch % maxAngle;
    }

    public double getRoll() {
        return roll;
    }

    public void setRoll(double roll) {
        this.roll = roll % maxAngle;
    }
}
