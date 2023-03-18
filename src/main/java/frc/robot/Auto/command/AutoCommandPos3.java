package frc.robot.Auto.command;

import frc.robot.Auto.EncoderAuto.TurnDegreesGyro;
import frc.robot.field.Field;

import static frc.robot.Auto.command.AutoCommandPos1.PAST_ITEM;

/**
 * Auto starting from the starting position in front of AprilTag 6
 */
public class AutoCommandPos3 extends BaseAutoCommand {
    public AutoCommandPos3(){
        super(Field.STARTING_POSE_3, 3);
        var item = Field.ItemInLineWithZone3;
        var goalPosition = Field.ZONE_3.bottomShelfMid.getCenterRight();

        addCommands(
            new MoveToPointCommand(item.getX() + PAST_ITEM, getStartPose().getY()),
            new TurnDegreesGyro(-180)
        );
        addAfterSideCommands();
    }
}
