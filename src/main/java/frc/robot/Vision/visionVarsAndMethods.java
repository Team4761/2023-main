package frc.robot.Vision;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import edu.wpi.first.math.geometry.*;
import edu.wpi.first.wpilibj.Timer;
import org.photonvision.PhotonCamera;
import org.photonvision.RobotPoseEstimator;
import org.photonvision.RobotPoseEstimator.PoseStrategy;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.Pair;

public class visionVarsAndMethods {

    /**
     * This is sudo-camera code for right now, when cameras are mounted and april tags mounted, these will change accordingly.
     * The code should be correct, just not the transform and rotation numbers.
     */

    //apriltags
    static AprilTag tag1 = new AprilTag(1, new Pose3d(0,0,0, new Rotation3d()));
    static AprilTag tag2 = new AprilTag(2, new Pose3d(10,0,0, new Rotation3d(90,0,0)));
    static AprilTag tag3 = new AprilTag(3, new Pose3d(10,10,0, new Rotation3d(270,0,0)));
    static AprilTag tag4 = new AprilTag(4, new Pose3d(0,10,0, new Rotation3d(180,0,0)));

    static List<AprilTag> tagList = Arrays.asList(tag1, tag2, tag3, tag4);

    //Final Layout
    public static AprilTagFieldLayout fieldLayout = new AprilTagFieldLayout(tagList, 50, 20);

    //Cameras for use with PhotonLib
    static PhotonCamera camera_drive = new PhotonCamera("driveCamera");
    static PhotonCamera camera_tagsLeft = new PhotonCamera("tagLeftCamera");
    static PhotonCamera camera_tagsRight = new PhotonCamera("tagRightCamera");

    static Transform3d cDriveToRobot = new Transform3d(new Translation3d(1,0,1), new Rotation3d(0,0,0));
    static Transform3d ctagsLeftToRobot = new Transform3d(new Translation3d(0,1,1), new Rotation3d(0,0,90));
    static Transform3d ctagsRightToRobot = new Transform3d(new Translation3d(0,0,-1), new Rotation3d(0,0,270));

    static Pair<PhotonCamera, Transform3d> pairedDriveCamera = new Pair<>(camera_drive, cDriveToRobot);
    static Pair<PhotonCamera, Transform3d> pairedLeftCamera = new Pair<>(camera_tagsLeft, ctagsLeftToRobot);
    static Pair<PhotonCamera, Transform3d> pairedRightCamera = new Pair<>(camera_tagsRight, ctagsRightToRobot);
    //final list of photon cameras
    static List<Pair<PhotonCamera, Transform3d>> photonCameraList = Arrays.asList(pairedDriveCamera, pairedLeftCamera, pairedRightCamera);

    //pose estimator (object from photon lib to estimate coords)
    public static RobotPoseEstimator robotPoseEstimator = new RobotPoseEstimator(fieldLayout, PoseStrategy.CLOSEST_TO_REFERENCE_POSE, photonCameraList);


    /**
     * returns pose2d of where robot is
     */
    public static Pair<Pose2d, Double> getEstimatedPose(Pose2d prevEstimatedPose){
        robotPoseEstimator.setReferencePose(prevEstimatedPose);

        double currentTime = Timer.getFPGATimestamp();
        Optional<Pair<Pose3d, Double>> result = robotPoseEstimator.update();

        if(result.isPresent()){
            return new Pair<>(result.get().getFirst().toPose2d(), currentTime - result.get().getSecond());
        }else{
            return new Pair<>(null, currentTime - result.get().getSecond()); //or 0.0
        }
    }



    
}
