package frc.robot.Vision;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import edu.wpi.first.math.geometry.*;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.field.Field;
import frc.robot.impl.placeholder.Placeholder;
import org.photonvision.PhotonCamera;
import org.photonvision.RobotPoseEstimator;
import org.photonvision.RobotPoseEstimator.PoseStrategy;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.Pair;
import org.photonvision.targeting.PhotonTrackedTarget;

public class visionVarsAndMethods {

    /**
     * This is sudo-camera code for right now, when cameras are mounted and april tags mounted, these will change accordingly.
     * The code should be correct, just not the transform and rotation numbers.
     */

    //apriltags
//    static AprilTag tag1 = new AprilTag(1, new Pose3d(0.1524,0,0, new Rotation3d(0,0,0)));
//    static AprilTag tag2 = new AprilTag(6, new Pose3d(10,0,0, new Rotation3d(90,0,0)));
//    static AprilTag tag3 = new AprilTag(7, new Pose3d(10,10,0, new Rotation3d(270,0,0)));
//    static AprilTag tag4 = new AprilTag(8, new Pose3d(0,10,0, new Rotation3d(180,0,0)));
    static AprilTag tag1 = new AprilTag(1, TagPositions.APRIL_TAG_1);
    static AprilTag tag2 = new AprilTag(2, TagPositions.APRIL_TAG_2);
    static AprilTag tag3 = new AprilTag(3, TagPositions.APRIL_TAG_3);
    static AprilTag tag4 = new AprilTag(4, TagPositions.APRIL_TAG_4);
    static AprilTag tag5 = new AprilTag(5, TagPositions.APRIL_TAG_5);
    static AprilTag tag6 = new AprilTag(6, TagPositions.APRIL_TAG_6);
    static AprilTag tag7 = new AprilTag(7, TagPositions.APRIL_TAG_7);
    static AprilTag tag8 = new AprilTag(8, TagPositions.APRIL_TAG_8);

    static List<AprilTag> tagList = Arrays.asList(tag6, tag7, tag8);

    //Final Layout
    public static AprilTagFieldLayout fieldLayout = new AprilTagFieldLayout(
        tagList,
        Units.inchesToMeters(Field.MIDPOINT_X * 2),
        Units.inchesToMeters(Field.ZONE_6.bottomShelfAboveCenter.getTopCenter().getY() * 2)
    );

    //Cameras for use with PhotonLib
    static PhotonCamera camera_drive = new PhotonCamera("driveCamera");
    static PhotonCamera camera_tagsLeft = new PhotonCamera("tagLeftCamera");
    static PhotonCamera camera_tagsRight = new PhotonCamera("tagRightCamera");

    static Transform3d cDriveToRobot =
        new Transform3d(
            new Translation3d(
                Units.inchesToMeters(25 - Placeholder.ROBOT_LENGTH_INCHES / 2),
                Units.inchesToMeters(-(Placeholder.ROBOT_WIDTH_INCHES / 2 - 5)),
                Units.inchesToMeters(10 - Placeholder.ROBOT_HEIGHT_INCHES / 2)),
            new Rotation3d(0,0,0)
        );
    static Transform3d ctagsLeftToRobot = new Transform3d(new Translation3d(0,1,1), new Rotation3d(0,0,90));
    static Transform3d ctagsRightToRobot = new Transform3d(new Translation3d(0,0,-1), new Rotation3d(0,0,270));

    static Pair<PhotonCamera, Transform3d> pairedDriveCamera = new Pair<>(camera_drive, cDriveToRobot);
//    static Pair<PhotonCamera, Transform3d> pairedLeftCamera = new Pair<>(camera_tagsLeft, ctagsLeftToRobot);
//    static Pair<PhotonCamera, Transform3d> pairedRightCamera = new Pair<>(camera_tagsRight, ctagsRightToRobot);
    //final list of photon cameras
    static List<Pair<PhotonCamera, Transform3d>> photonCameraList = Arrays.asList(pairedDriveCamera/*, pairedLeftCamera, pairedRightCamera*/);

    //pose estimator (object from photon lib to estimate coords)
    public static RobotPoseEstimator robotPoseEstimator = new RobotPoseEstimator(fieldLayout, PoseStrategy.AVERAGE_BEST_TARGETS, photonCameraList);


    /**
     * returns pose3d of where robot is
     */
    public static Pair<Pose3d, Double> getEstimatedPose(){
        //robotPoseEstimator.setReferencePose(prevEstimatedPose);

        double currentTime = Timer.getFPGATimestamp();
        Optional<Pair<Pose3d, Double>> result = robotPoseEstimator.update();

        if(result.isPresent()){
            return new Pair<>(result.get().getFirst(), currentTime - result.get().getSecond());
        }else{
            return new Pair<>(new Pose3d(0,0,0, new Rotation3d(0,0,0)), 0.0); //or 0.0
        }
    }


    public static Pair<Double, Double> getBestTagTransform(){
        var result = camera_drive.getLatestResult();
        boolean hasTargets = result.hasTargets();
        if(hasTargets){
            PhotonTrackedTarget target = result.getBestTarget();
            Transform3d bestCameraToTarget = target.getBestCameraToTarget();
            double x = bestCameraToTarget.getX();
            double y = bestCameraToTarget.getY();
            return new Pair<>(x, y);
        }else{
            return new Pair<>(0.0,0.0);
        }

    }

    public static int getBestTagID(){
        var result = camera_drive.getLatestResult();
        boolean hasTargets = result.hasTargets();
        if(hasTargets){
            PhotonTrackedTarget target = result.getBestTarget();
            int targetID = target.getFiducialId();
            return targetID;
        }else{
            return 0;
        }
    }

    public static double getBestTagPoseAmbi(){
        var result = camera_drive.getLatestResult();
        boolean hasTargets = result.hasTargets();
        if(hasTargets){
            PhotonTrackedTarget target = result.getBestTarget();
            double poseAmbi = target.getPoseAmbiguity();
            return poseAmbi;
        }else{
            return 0.0;
        }
    }

    public static boolean getIsTarget(){
        var result = camera_drive.getLatestResult();
        return result.hasTargets();
    }

    public static double getLinearDistance(Pose2d goal){
        double xDifference = goal.getX() - getEstimatedPose().getFirst().getX();
        double yDifference = goal.getY() - getEstimatedPose().getFirst().getY(); //TODO find if its y or z
        double pythagStuff = Math.sqrt((xDifference * xDifference) + (yDifference * yDifference));
        return pythagStuff;
    }





    
}
