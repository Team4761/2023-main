package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.arm.ArmSubsystem;
import frc.robot.main.Robot;

public class MoveArmAngles extends CommandBase {
    public MoveArmAngles(double top, double bottom) {
        ArmSubsystem.setDesiredBottomRotation(bottom);
        ArmSubsystem.setDesiredTopRotation(top);
    }
    @Override
    public void initialize() {
        Robot.arms.enablePID();

        Robot.arms.bottom.getController().setTolerance(0.1, 0.1);
        Robot.arms.top.getController().setTolerance(0.1, 0.1);
    }
    @Override
    public void execute() {
    }
    @Override
    public boolean isFinished() {
        // at goal or setpoint???
        // assuming setpoint is current point to, affecting pid, and goal is endgoal
        return Robot.arms.top.getController().atSetpoint()&&Robot.arms.bottom.getController().atSetpoint();
    }
}
