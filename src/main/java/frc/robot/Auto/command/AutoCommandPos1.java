package frc.robot.Auto.command;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.field.Field;
import frc.robot.main.Robot;

public class AutoCommandPos1 extends SequentialCommandGroup implements StartPoseProvider {
    public static final double PAST_ITEM = 18; // inches

    public AutoCommandPos1(){
        var startPose = getStartPose();
        var item = Field.ItemInlineWithZone1;
        var goalPosition = Field.ZONE_1.bottomShelfMid.getCenterRight();

        Robot.impl.setPose(startPose);

        addCommands(
            new MoveToPointCommand(item.getX() + PAST_ITEM, startPose.getY())
//            new TurnCommand(-180),
        );
    }

    @Override
    public Pose2d getStartPose() {
        return Field.STARTING_POSE_1;
    }
}
