package frc.robot.Auto.command;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Auto.EncoderAuto.GoMetersEncoder;
import frc.robot.Auto.tagAuto.moveToPointCommands.goToPoseAprilTag;
import frc.robot.command.MoveStraightMeasuredCommand;
import frc.robot.main.Robot;

public class MoveToPointCommand extends SequentialCommandGroup {

    private final Translation2d goalPostion;

    public MoveToPointCommand(double x, double y) {
        this(new Translation2d(x, y));
    }

    public MoveToPointCommand(Translation2d goalPostion) {
        this.goalPostion = goalPostion;
        boolean visionEnabled = false; // TODO: detect if vision is enabled
        if (visionEnabled) {
            Pose2d pose = new Pose2d(goalPostion, Robot.impl.getPose().getRotation());
            addCommands(
                new goToPoseAprilTag(pose)
            );
        } else {
            Translation2d currentPos = Robot.impl.getPose().getTranslation();
            Translation2d delta = goalPostion.minus(currentPos);

            // TODO: replace this with Ian's pathing if that works, or roll this up into "MoveToPose"
            if (Math.abs(delta.getX()) > Math.abs(delta.getY())) {
                int pointedAwayFromOrigin = currentPos.getAngle().getDegrees() < 45 ? 1 : -1;
                int sign = pointedAwayFromOrigin * ((goalPostion.getX() > currentPos.getX()) ? 1 : -1);
                double distanceX = Units.inchesToMeters(Math.abs(goalPostion.getX() - currentPos.getX()));
                if (sign < 0) {
                    // TODO: kill this when GoMetersEncoder can go backwards
                    addCommands(new MoveStraightMeasuredCommand(sign * .5, distanceX));
                } else {
                    addCommands(new GoMetersEncoder(distanceX));
                }
            } else {
                int pointedAwayFromOrigin = currentPos.getAngle().getDegrees() > 45 && currentPos.getAngle().getDegrees() < 135  ? 1 : -1;
                int sign = pointedAwayFromOrigin * ((goalPostion.getY() < currentPos.getY()) ? -1 : 1);
                double distanceY = Units.inchesToMeters(Math.abs(goalPostion.getY() - currentPos.getY()));
                if (sign < 0) {
                    addCommands(new MoveStraightMeasuredCommand(sign * .5, distanceY));
                } else {
                    addCommands(new GoMetersEncoder(distanceY));
                }
            }
        }
    }


}
