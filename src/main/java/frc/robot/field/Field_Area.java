package frc.robot.field;

import edu.wpi.first.math.geometry.Translation2d;

public class Field_Area {
    private final Translation2d BottemLeft;
    private final Translation2d TopRight;

    public Field_Area(double x1, double y1, double x2, double y2){
        BottemLeft = new Translation2d (x1,y1);
        TopRight = new Translation2d (x2,y2);
    }


    public Translation2d getBottemLeft() {
        return BottemLeft;
    }

    public Translation2d getTopRight() {
        return TopRight;
    }
}
