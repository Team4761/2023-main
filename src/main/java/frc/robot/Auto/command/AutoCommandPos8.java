package frc.robot.Auto.command;

import frc.robot.Auto.EncoderAuto.TurnDegreesGyro;
import frc.robot.field.Field;

import static frc.robot.Auto.command.AutoCommandPos1.PAST_ITEM;

public class AutoCommandPos8 extends BaseAutoCommand {
    public AutoCommandPos8(){
        super(Field.STARTING_POSE_8, 8);

        var startPose = getStartPose();
        var item = Field.ItemInlineWithZone8;
        addCommands(
            new MoveToPointCommand(item.getX() - PAST_ITEM, startPose.getY()),
            new TurnDegreesGyro(180)
        );
        addAfterSideCommands();
    }
}
