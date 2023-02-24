package frc.robot.field;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;

public class PoseUtil {
    public static Pose2d sameAnglePose(double x, double y, Pose2d pose) {
        return sameAnglePose(new Translation2d(x, y), pose);
    }

    public static Pose2d sameAnglePose(Translation2d point, Pose2d pose) {
        return new Pose2d(new Translation2d(point.getX(), point.getY()), pose.getRotation());
    }
}
