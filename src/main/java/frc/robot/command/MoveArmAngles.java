package frc.robot.command;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.arm.ArmSubsystem;
import frc.robot.main.Robot;

public class MoveArmAngles extends CommandBase {
    public MoveArmAngles(Translation2d pt) {
        ArmSubsystem.setDesiredTopRotation(pt.getX());
        ArmSubsystem.setDesiredBottomRotation(pt.getY());
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
        return Robot.arms.top.getController().atGoal() && Robot.arms.bottom.getController().atGoal();
    }
}
