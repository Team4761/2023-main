package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.arm.ArmSubsystem;

public class ArmMoveCommand extends CommandBase {
    private final double topSpeed;

    private final double bottomSpeed;

    public ArmMoveCommand(double speedTop, double speedBottom) {
        this.topSpeed = speedTop;
        this.bottomSpeed = speedBottom;
    }

    @Override
    public void execute() {
        ArmSubsystem arms = ArmSubsystem.getInstance();
        arms.setTop(topSpeed);
        arms.setBottom(bottomSpeed);
    }

    @Override
    public void end(boolean interrupted) {
        ArmSubsystem.getInstance().setTop(0.0);
    }
}
