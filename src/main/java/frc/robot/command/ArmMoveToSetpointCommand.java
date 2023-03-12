package frc.robot.command;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.main.Robot;

public class ArmMoveToSetpointCommand extends CommandBase {
    public ArmMoveToSetpointCommand(Translation2d rotations) {
        Robot.arms.enablePID();
        Robot.arms.moveToSetRotation(rotations);
    }

    @Override
    public boolean isFinished() {
        return Robot.arms.isAtSetpoint();
    }
}
