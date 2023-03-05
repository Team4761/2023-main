package frc.robot.Auto.command;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.command.ArmMoveCommand;
import frc.robot.command.OutTakeCommand;
import frc.robot.intake.IntakeSubsystem;

public class ScoreDirectlyInFront extends SequentialCommandGroup {
    public ScoreDirectlyInFront() {
        addCommands(
            new ArmMoveCommand(-1.0, 0.0).withTimeout(1.0),
            new OutTakeCommand(IntakeSubsystem.getInstance(), 2)
        );
    }
}