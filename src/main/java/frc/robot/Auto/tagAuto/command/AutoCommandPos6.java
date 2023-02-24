package frc.robot.Auto.tagAuto.command;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.field.Field;
import frc.robot.main.Robot;

public class AutoCommandPos6 extends SequentialCommandGroup {
    private static final double PAST_ITEM = 12; // inches

    public AutoCommandPos6(){
        var startPose = Field.STARTING_POSE_6;
        var item = Field.ItemInlineWithZone6;
        var goalPosition = Field.ZONE_6.bottomShelfMid.getCenterRight();

        // TODO: implement a simple pose on the robot as a setter/getter
        Robot.impl.setPose(startPose);

        // TODO: create each of these commands and make sure they make sense for position 6
        addCommands(
            new ScoreDirectlyInFront(),
            new MoveToPointCommand(item.getX() + PAST_ITEM, startPose.getY()),
            new RotateCommand(Math.PI),
            new MoveToPointCommand(item.getX(), item.getY() - Robot.impl.getLength() / 2.0),
            new RotateCommand(Math.PI),
            new MoveToPointCommand(goalPosition)
        );

        // As an alternative, if we can get
    }

}
