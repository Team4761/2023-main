package frc.robot.Auto.command;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.command.RotateDegreesCommand;
import frc.robot.field.Field;
import frc.robot.impl.placeholder.Placeholder;
import frc.robot.main.Constants;
import frc.robot.main.Robot;

public class AutoCommandPos7 extends SequentialCommandGroup {

    public AutoCommandPos7(){
        var startPose = Field.STARTING_POSE_7;

        var goalPosition = Field.CHARGING_STATION_ON_TOP.getCenter().
                minus(new Translation2d(Constants.ROBOT_LENGTH / 2, 0));

        Robot.impl.setPose(startPose);

        addCommands(
            new MoveToPointCommand(goalPosition)
        );
    }
}
