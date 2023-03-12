package frc.robot.Auto.command;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.command.MoveArmAngles;
import frc.robot.command.OutTakeCommand;
import frc.robot.intake.IntakeSubsystem;
import frc.robot.main.Constants;

public class ScoreDirectlyInFront extends SequentialCommandGroup {
    public ScoreDirectlyInFront() {
        addCommands(
            new MoveArmAngles(Constants.TOP_RUNG_POSITION),
            new OutTakeCommand(IntakeSubsystem.getInstance(), 2)
        );
    }
}
