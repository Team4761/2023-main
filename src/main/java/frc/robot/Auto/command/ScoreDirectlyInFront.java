package frc.robot.Auto.command;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.intake.IntakeSubsystem;
import frc.robot.main.Constants;
import frc.robot.command.OutTakeCommand;
import frc.robot.command.MoveArmAngles;

import static frc.robot.Auto.command.AutoCommandPos1.isOnlyBackup;

public class ScoreDirectlyInFront extends SequentialCommandGroup {
    public ScoreDirectlyInFront() {
        if (armsAreWorking()) {
            addCommands(
                    new MoveArmAngles(Constants.INTAKE_POSITION.getX(), Constants.INTAKE_POSITION.getY()),
                    new OutTakeCommand(IntakeSubsystem.getInstance(), 1)
            );
        }
        addCommands(
            new OutTakeCommand(IntakeSubsystem.getInstance(), 2)
        );
    }

    public static boolean armsAreWorking() {
        return SmartDashboard.getBoolean("autoArms", false);
    }
}       