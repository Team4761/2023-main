package frc.robot.Auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.main.Constants;


public class getNewData extends CommandBase {

    Pose2d goalPose;
    double goalPoseX;
    double goalPoseY;
    double goalPoseRotationX;

    public getNewData() {
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements();
    }

    @Override
    public void initialize() {
        SmartDashboard.putNumber("linear P", Constants.LINEAR_P);
        SmartDashboard.putNumber("linear I", Constants.LINEAR_I);
        SmartDashboard.putNumber("linear D", Constants.LINEAR_D);
        SmartDashboard.putNumber("angular P", Constants.ANGULAR_P);
        SmartDashboard.putNumber("angular I", Constants.ANGULAR_I);
        SmartDashboard.putNumber("angular D", Constants.ANGULAR_D);

        goalPose = new Pose2d(0,0,new Rotation2d(0,0));
        SmartDashboard.putNumber("pose goal x", 0);
        SmartDashboard.putNumber("pose goal y", 0);
        SmartDashboard.putNumber("pose goal rotate x", 0);
        SmartDashboard.putNumber("pose goal rotate y", 0);
    }

    @Override
    public void execute() {
        Constants.LINEAR_P = SmartDashboard.getNumber("linear P", 0);
        Constants.LINEAR_I = SmartDashboard.getNumber("linear I", 0);
        Constants.LINEAR_D = SmartDashboard.getNumber("linear D", 0);
        Constants.ANGULAR_P = SmartDashboard.getNumber("angular P", 0);
        Constants.ANGULAR_I = SmartDashboard.getNumber("angular I", 0);
        Constants.ANGULAR_D = SmartDashboard.getNumber("angular D", 0);

        goalPoseX = SmartDashboard.getNumber("pose goal x", 0);
        goalPoseY = SmartDashboard.getNumber("pose goal y", 0);
        goalPoseRotationX = SmartDashboard.getNumber("pose goal rotate x", 0);

        Constants.goalPose = new Pose2d(goalPoseX, goalPoseY, new Rotation2d(goalPoseRotationX, 0));
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
