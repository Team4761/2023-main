package frc.robot.field;

import edu.wpi.first.math.geometry.Translation2d;

public class Field_Area {
    private final Translation2d bottomLeft;
    private final Translation2d topRight;

    public Field_Area(double x1, double y1, double x2, double y2){
        bottomLeft = new Translation2d (x1,y1);
        topRight = new Translation2d (x2,y2);
    }

    public Translation2d getBottomLeft() {
        return bottomLeft;
    }

    public Translation2d getTopRight() {
        return topRight;
    }

    public Translation2d getBottomRight() {
        return new Translation2d(topRight.getX(), bottomLeft.getY());
    }

    public Translation2d getTopLeft() {
        return new Translation2d(bottomLeft.getX(), topRight.getY());
    }

    public Translation2d getCenter() {
        return new Translation2d(bottomLeft.getX() + getWidth() / 2, bottomLeft.getY() + getHeight() / 2);
    }

    public Translation2d getCenterLeft() {
        return new Translation2d(bottomLeft.getX(), getCenter().getY());
    }

    public Translation2d getCenterRight() {
        return new Translation2d(topRight.getX(), getCenter().getY());
    }

    public Translation2d getTopCenter() {
        return new Translation2d(getCenter().getX(), topRight.getY());
    }

    public Translation2d getBottomCenter() {
        return new Translation2d(getCenter().getX(), bottomLeft.getY());
    }

    public double getWidth() {
        return bottomLeft.getX() - topRight.getX();
    }

    public double getHeight() {
        return bottomLeft.getY() - topRight.getY();
    }
}
