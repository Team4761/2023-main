package frc.robot.Auto.tagAuto.moveToPointCommands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Vision.visionVarsAndMethods;
import frc.robot.main.Robot;

import static frc.robot.impl.placeholder.Placeholder.angular_PIDcontroller;


public class faceGoalPoseAprilTag extends CommandBase {

    Pose2d goalPose;
    double goalYaw;
    double prevKnownAngle;
    double currentYaw;
    double rotateSpeed;

    public faceGoalPoseAprilTag(Pose2d goalPose) {
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements();
        this.goalPose = goalPose;
    }

    @Override
    public void initialize() {
        double xDifference = visionVarsAndMethods.getEstimatedPose().getFirst().getX() - goalPose.getX();
        double yDifference = visionVarsAndMethods.getEstimatedPose().getFirst().getY() - goalPose.getY();
        goalYaw = Math.atan(yDifference/xDifference); //TODO find if I need to switch these
    }

    @Override
    public void execute() {
        boolean isTarget = visionVarsAndMethods.getIsTarget();
        if(isTarget){
            currentYaw = visionVarsAndMethods.getEstimatedPose().getFirst().getRotation().getX();
            prevKnownAngle = currentYaw;
            rotateSpeed = angular_PIDcontroller.calculate(currentYaw, goalYaw); //TODO find if photonvision does degrees or radians!
        }else{
            rotateSpeed = angular_PIDcontroller.calculate(prevKnownAngle, goalYaw); //TODO find if this actually works, it may make the robot just spin around forever
        }
        //Robot.driveTrain.arcadeDrive(0, rotateSpeed);
    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
         return Math.abs(currentYaw - goalYaw) < Math.PI / 32; //TODO TUNE THIS NUMBER! This needs to be pretty precise for single movement goals.
    }

    @Override
    public void end(boolean interrupted) {

    }
}
