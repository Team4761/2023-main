package frc.robot.Auto.command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Auto.EncoderAuto.TurnToGyro;
import frc.robot.field.Field;
import frc.robot.main.Robot;

import static frc.robot.Auto.command.AutoCommandPos1.isOnlyBackup;

public class AutoCommandPos8 extends SequentialCommandGroup {
    private static final double PAST_ITEM = 12; // inches

    public AutoCommandPos8(){
        var startPose = Field.STARTING_POSE_8;
        var item = Field.ItemInlineWithZone8;
        var goalPosition = Field.ZONE_8.bottomShelfMid.getCenterRight();

        Robot.impl.setPose(startPose);

        boolean onlyBackup = isOnlyBackup();
        addCommands(
                new ScoreDirectlyInFront(),
                new MoveToPointCommand(item.getX() + PAST_ITEM, startPose.getY())
        );

        if (!onlyBackup) {
            addCommands(
                new TurnToGyro(-90),
                new MoveToPointCommand(item.getX(), item.getY() - Robot.impl.getLength() / 2.0),
                new TurnToGyro(0),
                new MoveToPointCommand(goalPosition)
            );
        }
    }
}
