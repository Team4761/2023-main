package frc.robot.Auto.command;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.command.RotateDegreesCommand;
import frc.robot.field.Field;
import frc.robot.main.Robot;

public class AutoCommandPos7 extends SequentialCommandGroup {

    public AutoCommandPos7(){
        var startPose = Field.STARTING_POSE_7;

        // we may have to subtract our robot length / 2
        var goalPosition = Field.CHARGING_STATION_ON_TOP.getCenter();

        Robot.impl.setPose(startPose);

        // TODO: create each of these commands and make sure they make sense for position 6
        addCommands(
            new ScoreDirectlyInFront(),
            new MoveToPointCommand(goalPosition)
        );

    }
}
