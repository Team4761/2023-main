package frc.robot.Auto.command;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.intake.IntakeSubsystem;
import frc.robot.main.Constants;
import frc.robot.command.OutTakeCommand;
import frc.robot.command.MoveArmAngles;

public class ScoreDirectlyInFront extends SequentialCommandGroup {
    public ScoreDirectlyInFront() {
        addCommands(
            new MoveArmAngles(Constants.INTAKE_POSITION.getX(), Constants.INTAKE_POSITION.getY()),
            new OutTakeCommand(IntakeSubsystem.getInstance(), 1)
        );
    }
}       