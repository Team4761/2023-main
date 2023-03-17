package frc.robot.Auto.command;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.command.InTakeCommand;
import frc.robot.command.MoveArmAngles;
import frc.robot.command.MoveStraightMeasuredCommand;
import frc.robot.command.OutTakeCommand;
import frc.robot.intake.IntakeSubsystem;
import frc.robot.main.Constants;

public class ScoreDirectlyInFront extends SequentialCommandGroup {
    public ScoreDirectlyInFront() {
        addCommands(
            new InTakeCommand(IntakeSubsystem.getInstance(), 3),
            new MoveStraightMeasuredCommand(-.5, Units.feetToMeters(2)),
            new WaitCommand(1),
            new MoveArmAngles(Constants.NEUTRAL_POSITION),
            new MoveArmAngles(Constants.TOP_RUNG_POSITION),
            new MoveStraightMeasuredCommand(.5, Units.feetToMeters(2.1)),
            new OutTakeCommand(IntakeSubsystem.getInstance(), 0.5),
            new MoveArmAngles(Constants.NEUTRAL_POSITION)
        );
    }
}
