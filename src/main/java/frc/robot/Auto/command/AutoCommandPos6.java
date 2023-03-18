package frc.robot.Auto.command;

import frc.robot.Auto.EncoderAuto.TurnDegreesGyro;
import frc.robot.field.Field;

import static frc.robot.Auto.command.AutoCommandPos1.PAST_ITEM;

/**
 * Auto starting from the starting position in front of AprilTag 6
 */
public class AutoCommandPos6 extends BaseAutoCommand  {
    public AutoCommandPos6(){
        super(Field.STARTING_POSE_6, 6);
        var startPose = getStartPose();
        var item = Field.ItemInlineWithZone6;

        addCommands(
            new MoveToPointCommand(item.getX() - PAST_ITEM, startPose.getY()),
            new TurnDegreesGyro(180)
        );
        addAfterSideCommands();
    }
}
