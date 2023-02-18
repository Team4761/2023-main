package frc.robot.Auto.tagAuto.moveToPointCommands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Vision.visionVarsAndMethods;
import frc.robot.main.Robot;

import static frc.robot.impl.placeholder.Placeholder.angular_PIDcontroller;


public class goToAngleAprilTag extends CommandBase {

    /**
     * This method should make the robot move to a given pose using real time tag feedback.
     * This will not pathtrace, and will not recognize any barriers/stuff in the way. (for later)
     */

    double goalYaw;
    double prevKnownAngle;
    double currentYaw;
    double rotateSpeed;
    Pose2d goalPose;


    public goToAngleAprilTag(Pose2d goalPose) {
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements();
        this.goalPose = goalPose;
    }

    @Override
    public void initialize() {
        goalYaw = goalPose.getRotation().getRadians();
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
        //TODO Tune this value
        return Math.abs(currentYaw - goalYaw) < Math.PI / 16; //TODO TUNE THIS NUMBER
    }

    @Override
    public void end(boolean interrupted) {

    }
}
