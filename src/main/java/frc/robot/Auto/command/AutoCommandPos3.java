package frc.robot.Auto.command;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.command.RotateDegreesCommand;
import frc.robot.field.Field;
import frc.robot.main.Robot;


/**
 * Auto starting from the starting position in front of AprilTag 6
 */
public class AutoCommandPos3 extends SequentialCommandGroup {
    private static final double PAST_ITEM = 12;
    public AutoCommandPos3(){
        var startPose = Field.STARTING_POSE_3;
        var item = Field.ItemInLineWithZone3;
        var goalPosition = Field.ZONE_3.bottomShelfMid.getCenterRight();

        // TODO: implement a simple pose on the robot as a setter/getter
        Robot.impl.setPose(startPose);

        // TODO: create each of these commands and make sure they make sense for position 6
        addCommands(
            new ScoreDirectlyInFront(),
            new MoveToPointCommand(item.getX() - PAST_ITEM, startPose.getY()),
            new RotateDegreesCommand(.5, 90),
            new MoveToPointCommand(item.getX(), item.getY() - Robot.impl.getLength() / 2.0),
            new RotateDegreesCommand(.5, -90),
            new MoveToPointCommand(goalPosition)
        );
    }
}
