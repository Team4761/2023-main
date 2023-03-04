package frc.robot.Auto.command;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Auto.EncoderAuto.TurnToGyro;
import frc.robot.field.Field;
import frc.robot.main.Robot;

import static frc.robot.Auto.command.AutoCommandPos1.isOnlyBackup;


/**
 * Auto starting from the starting position in front of AprilTag 6
 */
public class AutoCommandPos3 extends SequentialCommandGroup {
    private static final double PAST_ITEM = 12;
    public AutoCommandPos3(){
        var startPose = Field.STARTING_POSE_3;
        var item = Field.ItemInLineWithZone3;
        var goalPosition = Field.ZONE_3.bottomShelfMid.getCenterRight();

        Robot.impl.setPose(startPose);

        boolean onlyBackup = isOnlyBackup();
        addCommands(
            new MoveToPointCommand(item.getX() - PAST_ITEM, startPose.getY())
        );
        if (!onlyBackup) {
            addCommands(
                new TurnToGyro(90),
                new MoveToPointCommand(item.getX(), item.getY() - Robot.impl.getLength() / 2.0),
                new TurnToGyro(0),
                new MoveToPointCommand(goalPosition)
            );
        }
    }
}
