package frc.robot.Auto.command;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
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
            addCommands(
                //new goToPoseAprilTag(goalPostion)
            );
        } else {
            Translation2d currentPos = Robot.impl.getPose().getTranslation();
            Translation2d delta = goalPostion.minus(currentPos);

            if (Math.abs(delta.getX()) > Math.abs(delta.getY())) {
                // todo travel so that X is the primary point of change
                addCommands(

                );
            } else {
                // todo travel so that Y is the primary point of change
                addCommands(

                );
            }
        }
    }


}
