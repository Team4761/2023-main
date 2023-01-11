package frc.robot.Vision;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.photonvision.PhotonCamera;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.Pair;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;

public class visionVars {

    //apriltags
    static AprilTag tag1 = new AprilTag(1, new Pose3d(0,0,0, new Rotation3d()));
    static AprilTag tag2 = new AprilTag(2, new Pose3d(10,0,0, new Rotation3d(90,0,0)));
    static AprilTag tag3 = new AprilTag(3, new Pose3d(10,10,0, new Rotation3d(270,0,0)));
    static AprilTag tag4 = new AprilTag(4, new Pose3d(0,10,0, new Rotation3d(180,0,0)));

    static List<AprilTag> tagList = Arrays.asList(tag1, tag2, tag3, tag4);

    public static AprilTagFieldLayout fieldLayout = new AprilTagFieldLayout(tagList, 50, 20);

    //Cameras for use with PhotonLib
    PhotonCamera camera_drive = new PhotonCamera("driveCamera");
    PhotonCamera camera_tagsLeft = new PhotonCamera("tagLeftCamera");
    PhotonCamera camear_tagsRight = new PhotonCamera("tagRightCamera");

    Transform3d cDriveToRobot = new Transform3d(new Translation3d(1,0,1), new Rotation3d(0,0,0));
    Transform3d ctagsLeftToRobot = new Transform3d(new Translation3d(0,1,1), new Rotation3d(0,0,90));
    Transform3d ctagsRightToRobot = new Transform3d(new Translation3d(0,0,-1), new Rotation3d(0,0,270));

    Pair<PhotonCamera, Transform3d> pairedDriveCamera = new Pair<PhotonCamera, Transform3d>(camera_drive, cDriveToRobot);
    
}
