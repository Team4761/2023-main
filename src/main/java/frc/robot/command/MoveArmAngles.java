package frc.robot.command;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.arm.ArmSubsystem;
import frc.robot.main.Robot;

public class MoveArmAngles extends CommandBase {
    Translation2d pos;
    public MoveArmAngles(Translation2d pt) {
        addRequirements(ArmSubsystem.getInstance());
        pos = pt;
    }
    @Override
    public void initialize() {
        Robot.arms.enablePID();
        Robot.arms.bottom.getController().setTolerance(0.2, 0.2);
        Robot.arms.top.getController().setTolerance(0.2, 0.2);
        Robot.arms.movePID();
        Robot.arms.moveToSetRotation(pos);
    }
    @Override
    public void execute() {
        // PID does all the work
    }

    @Override 
    public void end(boolean interrupted) {
        if (interrupted) System.out.println("move arm angles interrupted");
        else System.out.println("move arm angles done");
    }
    @Override
    public boolean isFinished() {
        // at goal or setpoint???
        // assuming setpoint is current point to, affecting pid, and goal is endgoal
        return Robot.arms.top.getController().atGoal() && Robot.arms.bottom.getController().atGoal();
    }
}
