package frc.robot.Auto.tagAuto;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Vision.visionVarsAndMethods;
import frc.robot.impl.placeholder.Placeholder;
import frc.robot.main.Constants;

import static frc.robot.impl.placeholder.Placeholder.angular_PIDcontroller;


public class goToAngle extends CommandBase {

    /**
     * This method should make the robot move to a given pose using real time tag feedback.
     * This will not pathtrace, and will not recognize any barriers/stuff in the way. (for later)
     */

    double goalYaw;


    public goToAngle(double goalYaw) {
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements();
        this.goalYaw = goalYaw;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double rotateSpeed;
        boolean isTarget = visionVarsAndMethods.getIsTarget();
        if(isTarget){
           double currentYaw = visionVarsAndMethods.getEstimatedPose().getFirst().getRotation().getX();
           rotateSpeed = angular_PIDcontroller.calculate(currentYaw, goalYaw);
        }
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
