package frc.robot.Auto.tagAuto.moveToPointCommands;


import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Auto.getNewData;
import frc.robot.main.Constants;

public class goToPoseAprilTag extends SequentialCommandGroup {

    public goToPoseAprilTag() {
        // TODO: Add your sequential commands in the super() call, e.g.
        //           super(new OpenClawCommand(), new MoveArmCommand());
        super(
                new faceGoalPoseAprilTag(Constants.goalPose),
                new goToDistanceAprilTag(Constants.goalPose),
                new goToAngleAprilTag(Constants.goalPose)
        ); //TODO find if this will need to be run a few times, may need an angle change once there
    }
}