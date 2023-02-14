package frc.robot.Auto.tagAuto.moveToPointCommands;


import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class goToPoseAprilTag extends SequentialCommandGroup {

    public goToPoseAprilTag(Pose2d goalPose) {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new OpenClawCommand(), new MoveArmCommand());
        super(
                new faceGoalPoseAprilTag(goalPose),
                new goToDistanceAprilTag(goalPose),
                new goToAngleAprilTag(goalPose)
        ); //TODO find if this will need to be run a few times, may need an angle change once there
    }
}