package frc.robot.Auto.command;

import frc.robot.Auto.EncoderAuto.TurnDegreesGyro;
import frc.robot.field.Field;

public class AutoCommandPos1 extends BaseAutoCommand {
    public static final double PAST_ITEM = 8; // inches

    public AutoCommandPos1(){
        super(Field.STARTING_POSE_1, 1);
        var item = Field.ItemInlineWithZone1;

        addCommands(
            new MoveToPointCommand(item.getX() + PAST_ITEM, getStartPose().getY()),
            new TurnDegreesGyro(-180)
        );
        addAfterSideCommands();
    }
}
