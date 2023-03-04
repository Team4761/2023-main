package frc.robot.Auto.command;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.field.Field;
import frc.robot.main.Constants;
import frc.robot.main.Robot;

public class AutoCommandPos2 extends SequentialCommandGroup implements StartPoseProvider {
    public AutoCommandPos2(){
        var startPose = getStartPose();

        var goalPosition = Field.CHARGING_STATION_OTHER_ON_TOP.getCenter().
                plus(new Translation2d(Constants.ROBOT_LENGTH / 2, 0));

        Robot.impl.setPose(startPose);
        
        // from edge of charge station to center is around 104.7 cm accounting for angle
        addCommands(
            new MoveToPointCommand(goalPosition)
        );
    }

    @Override
    public Pose2d getStartPose() {
        return Field.STARTING_POSE_2;
    }
}
