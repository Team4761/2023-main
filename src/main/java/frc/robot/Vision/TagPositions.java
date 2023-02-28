package frc.robot.Vision;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.util.Units;

/**
 * All positions are in inches.
 */
public class TagPositions {
    public static final Pose3d APRIL_TAG_1 =
        toMeters(new Pose3d(610.77, 42.19, 18.22, new Rotation3d(0, 0, 2 * Math.PI)));
    public static final Pose3d APRIL_TAG_2 =
        toMeters(new Pose3d(610.77, 108.19, 18.22, new Rotation3d(0, 0, 2 * Math.PI)));
    public static final Pose3d APRIL_TAG_3 =
        toMeters(new Pose3d(610.77, 174.19, 18.22, new Rotation3d(0, 0, 2 * Math.PI)));
    public static final Pose3d APRIL_TAG_4 =
        toMeters(new Pose3d(636.96, 265.74, 27.38, new Rotation3d(0, 0, 2 * Math.PI)));
    public static final Pose3d APRIL_TAG_5 =
        toMeters(new Pose3d(14.25, 265.74, 27.38, new Rotation3d(0, 0, 0)));
    public static final Pose3d APRIL_TAG_6 =
        toMeters(new Pose3d(40.45, 174.19, 18.22, new Rotation3d(0, 0, 0)));
    public static final Pose3d APRIL_TAG_7 =
        toMeters(new Pose3d(40.45, 108.19, 18.22, new Rotation3d(0, 0, 0)));
    public static final Pose3d APRIL_TAG_8 =
        toMeters(new Pose3d(40.45, 42.19, 18.22, new Rotation3d(0, 0, 0)));

    public static Pose3d toMeters(Pose3d pose) {
        return new Pose3d(
            Units.inchesToMeters(pose.getX()),
            Units.inchesToMeters(pose.getY()),
            Units.inchesToMeters(pose.getZ()),
            pose.getRotation()
        );
    }
}
