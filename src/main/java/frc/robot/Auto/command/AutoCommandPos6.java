package frc.robot.Auto.command;


import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Auto.EncoderAuto.TurnDegreesGyro;
import frc.robot.command.InTakeCommand;
import frc.robot.command.MoveArmAngles;
import frc.robot.command.MoveStraightMeasuredCommand;
import frc.robot.main.Constants;
import frc.robot.field.Field;
import frc.robot.intake.IntakeSubsystem;
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
            new MoveStraightMeasuredCommand(-.8, item.getX() - startPose.getX()-8),
            new TurnDegreesGyro(-180),
            new MoveArmAngles(Constants.INTAKE_POSITION),
            new ParallelCommandGroup(
                new InTakeCommand(IntakeSubsystem.getInstance(), 3),
                new MoveStraightMeasuredCommand(0.4, 1)
            )
        );
    }
}
