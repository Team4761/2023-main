package frc.robot.Auto.command;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Auto.EncoderAuto.TurnToGyro;
import frc.robot.field.Field;
import frc.robot.main.Robot;

import static frc.robot.Auto.command.AutoCommandPos1.PAST_ITEM;

/**
 * Auto starting from the starting position in front of AprilTag 6
 */
public class AutoCommandPos6 extends SequentialCommandGroup {
    public AutoCommandPos6(){
        var startPose = Field.STARTING_POSE_6;
        var item = Field.ItemInlineWithZone6;
        var goalPosition = Field.ZONE_6.bottomShelfMid.getCenterRight();

        Robot.impl.setPose(startPose);
        addCommands(
            new MoveToPointCommand(item.getX() - PAST_ITEM, startPose.getY()),
            new TurnToGyro(180)
        );
    }
}
