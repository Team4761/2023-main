package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.arm.ArmSubsystem;

public class ArmMoveCommand extends CommandBase {
    @Override
    public void execute() {
        ArmSubsystem.getInstance().setTop(-0.1);
    }

    @Override
    public void end(boolean interrupted) {
        ArmSubsystem.getInstance().setTop(0.0);
    }
}
