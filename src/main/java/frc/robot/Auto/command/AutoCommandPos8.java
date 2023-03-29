package frc.robot.Auto.command;

import edu.wpi.first.math.util.Units;
import frc.robot.Auto.EncoderAuto.TurnDegreesGyro;
import frc.robot.command.MoveStraightMeasuredCommand;
import frc.robot.field.Field;
import frc.robot.main.Robot;

public class AutoCommandPos8 extends BaseAutoCommand {
    public AutoCommandPos8(){
        super(Field.STARTING_POSE_8, 8);

        var startPose = getStartPose();
        var item = Field.ItemInlineWithZone8;
        var goalPosition = Field.ZONE_8.bottomShelfMid.getCenterRight();

        Robot.impl.setPose(startPose);

        addCommands(
            new MoveStraightMeasuredCommand(-.8, Units.inchesToMeters(item.getX() - startPose.getX()-8)),
            new TurnDegreesGyro(180)
        );
        addAfterSideCommands();
    }
}
