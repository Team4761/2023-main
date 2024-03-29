package frc.robot.Auto.command;

import edu.wpi.first.math.util.Units;
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

public class AutoCommandPos8 extends SequentialCommandGroup {
    public AutoCommandPos8(){
        var startPose = Field.STARTING_POSE_8;
        var item = Field.ItemInlineWithZone8;
        var goalPosition = Field.ZONE_8.bottomShelfMid.getCenterRight();

        Robot.impl.setPose(startPose);
        
        addCommands(
            new MoveStraightMeasuredCommand(-.8, Units.inchesToMeters(item.getX() - startPose.getX()-8)),
            new TurnDegreesGyro(180),
            new MoveArmAngles(Constants.INTAKE_POSITION),
            new ParallelCommandGroup(
                new InTakeCommand(IntakeSubsystem.getInstance(), 3),
                new MoveStraightMeasuredCommand(0.4, 1.5)
            )
        );
    }
}
