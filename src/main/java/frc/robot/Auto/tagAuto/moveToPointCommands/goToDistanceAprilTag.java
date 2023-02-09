package frc.robot.Auto.tagAuto.moveToPointCommands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Vision.visionVarsAndMethods;

import static frc.robot.impl.placeholder.Placeholder.linear_PIDcontroller;


public class goToDistanceAprilTag extends CommandBase {
    //TODO this takes the linear distance, there will be seperate stuff for calcs to find this
    //take goalPose, get linear distance, goal of 0, which would be at the point.


    Pose2d goalPose;
    double prevKnownDistance;
    double currentDistance;
    double linearSpeed;
    public goToDistanceAprilTag(Pose2d goalPose) {
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements();
        this.goalPose = goalPose;
    }

    @Override
    public void initialize() {
        boolean isTarget = visionVarsAndMethods.getIsTarget();
        if(isTarget){
            prevKnownDistance = currentDistance;
            currentDistance = visionVarsAndMethods.getLinearDistance(goalPose);
            linearSpeed = linear_PIDcontroller.calculate(currentDistance, 0); //TODO check if this works!
        }else{

        }
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return Math.abs(currentDistance) < 1;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
