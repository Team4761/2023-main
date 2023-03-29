package frc.robot.Auto.command;


import edu.wpi.first.math.util.Units;
import frc.robot.Auto.EncoderAuto.TurnDegreesGyro;
import frc.robot.command.MoveStraightMeasuredCommand;
import frc.robot.field.Field;
import frc.robot.main.Robot;

/**
 * Auto starting from the starting position in front of AprilTag 6
 */
public class AutoCommandPos6 extends BaseAutoCommand  {
    public AutoCommandPos6(){
        super(Field.STARTING_POSE_6, 6);
        var startPose = getStartPose();
        var item = Field.ItemInlineWithZone6;

        Robot.impl.setPose(startPose);

        addCommands(
            new MoveStraightMeasuredCommand(-.8, Units.inchesToMeters(item.getX() - startPose.getX()-8)),
            new TurnDegreesGyro(180)
        );
        addAfterSideCommands();
    }
}
