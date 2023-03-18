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
public class AutoCommandPos3 extends SequentialCommandGroup {
    public AutoCommandPos3(){
        var startPose = Field.STARTING_POSE_3;
        var item = Field.ItemInLineWithZone3;
        var goalPosition = Field.ZONE_3.bottomShelfMid.getCenterRight();

        Robot.impl.setPose(startPose);

        addCommands(
            new MoveToPointCommand(item.getX() + PAST_ITEM, startPose.getY()),
            new TurnDegreesGyro(-180),
            new MoveArmAngles(Constants.INTAKE_POSITION),
            new ParallelCommandGroup(
                new InTakeCommand(IntakeSubsystem.getInstance(), 3),
                new MoveStraightMeasuredCommand(0.4, .5)
            )
        );
    }
}
