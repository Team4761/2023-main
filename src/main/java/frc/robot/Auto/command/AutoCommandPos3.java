package frc.robot.Auto.command;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.field.Field;
import frc.robot.main.Robot;

import static frc.robot.Auto.command.AutoCommandPos1.PAST_ITEM;

/**
 * Auto starting from the starting position in front of AprilTag 6
 */
public class AutoCommandPos3 extends SequentialCommandGroup implements StartPoseProvider {
    public AutoCommandPos3(){
        var startPose = getStartPose();
        var item = Field.ItemInLineWithZone3;
        var goalPosition = Field.ZONE_3.bottomShelfMid.getCenterRight();

        Robot.impl.setPose(startPose);

        addCommands(
            new MoveToPointCommand(item.getX() + PAST_ITEM, startPose.getY())
//            new TurnCommand(-180),
        );
    }

    @Override
    public Pose2d getStartPose() {
        return Field.STARTING_POSE_3;
    }
}
