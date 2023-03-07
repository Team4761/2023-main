package frc.robot.field;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Translation3d;

import static frc.robot.field.Constants.*;

/**
 * Where all the elements of the field are.  Units are inches.
 */
public class Field {
    private static final Translation2d Origin = new Translation2d(ORIGIN_X, ORIGIN_Y);

    //------------------------------------------------------------------------------------------------------------
    // Items are identified by their relative positions to zones.  Zones are named according to the April Tag
    // that is posted on them.
    //------------------------------------------------------------------------------------------------------------
    public static final Translation2d ItemInlineWithZone6 = new Translation2d (ITEM_X,36.25);
    public static final Translation2d ItemBelowCenterOfZone7 = new Translation2d (ITEM_X,84.25);
    public static final Translation2d ItemAboveCenterOfZone7= new Translation2d (ITEM_X,132.25);
    public static final Translation2d ItemInlineWithZone8 = new Translation2d (ITEM_X,180.25);
    public static final Translation2d ItemInLineWithZone3 = mirror(ItemInlineWithZone6);
    public static final Translation2d ItemBelowCenterOfZone2 = mirror(ItemBelowCenterOfZone7);
    public static final Translation2d ItemAboveCenterOfZone2 = mirror(ItemAboveCenterOfZone7);
    public static final Translation2d ItemInlineWithZone1 = mirror(ItemInlineWithZone8);

    //------------------------------------------------------------------------------------------------------------
    // Zones are numbered according to the April Tag attached to them
    //------------------------------------------------------------------------------------------------------------
    public static final Zone ZONE_8 = new Zone(
        Origin,
        new Translation2d(UPPER_SHELF_FRONT_X, ZONE1_BIN1_TOP_Y),   
        new Translation2d(UPPER_SHELF_FRONT_X, ZONE1_BIN2_TOP_Y),
        new Translation2d(UPPER_SHELF_FRONT_X, ZONE1_BIN3_TOP_Y), 
        new Translation2d(MIDDLE_SHELF_FRONT_X, ZONE1_BIN1_TOP_Y), 
        new Translation2d(MIDDLE_SHELF_FRONT_X, ZONE1_BIN2_TOP_Y),
        new Translation2d(MIDDLE_SHELF_FRONT_X, ZONE1_BIN3_TOP_Y),
        new Translation2d(BOTTOM_SHELF_FRONT_X, ZONE1_BIN1_TOP_Y),
        new Translation2d(BOTTOM_SHELF_FRONT_X, ZONE1_BIN2_TOP_Y),
        new Translation2d(BOTTOM_SHELF_FRONT_X, ZONE1_BIN3_TOP_Y)
    );

    public static final Zone ZONE_7 = new Zone(
        ZONE_8.topShelfAboveCenter.getTopLeft(),
        new Translation2d(UPPER_SHELF_FRONT_X, ZONE2_BIN1_TOP_Y),
        new Translation2d(UPPER_SHELF_FRONT_X, ZONE2_BIN2_TOP_Y),
        new Translation2d(UPPER_SHELF_FRONT_X, ZONE2_BIN3_TOP_Y),
        new Translation2d(MIDDLE_SHELF_FRONT_X, ZONE2_BIN1_TOP_Y),
        new Translation2d(MIDDLE_SHELF_FRONT_X, ZONE2_BIN2_TOP_Y),
        new Translation2d(MIDDLE_SHELF_FRONT_X, ZONE2_BIN3_TOP_Y),
        new Translation2d(BOTTOM_SHELF_FRONT_X, ZONE2_BIN1_TOP_Y),
        new Translation2d(BOTTOM_SHELF_FRONT_X, ZONE2_BIN2_TOP_Y),
        new Translation2d(BOTTOM_SHELF_FRONT_X, ZONE2_BIN3_TOP_Y)
    );

    public static final Zone ZONE_6 = new Zone(
        ZONE_7.topShelfAboveCenter.getTopLeft(),
        new Translation2d(UPPER_SHELF_FRONT_X, ZONE3_BIN1_TOP_Y),
        new Translation2d(UPPER_SHELF_FRONT_X, ZONE3_BIN2_TOP_Y),
        new Translation2d(UPPER_SHELF_FRONT_X, ZONE3_BIN3_TOP_Y),
        new Translation2d(MIDDLE_SHELF_FRONT_X, ZONE3_BIN1_TOP_Y),
        new Translation2d(MIDDLE_SHELF_FRONT_X, ZONE3_BIN2_TOP_Y),
        new Translation2d(MIDDLE_SHELF_FRONT_X, ZONE3_BIN3_TOP_Y),
        new Translation2d(BOTTOM_SHELF_FRONT_X, ZONE3_BIN1_TOP_Y),
        new Translation2d(BOTTOM_SHELF_FRONT_X, ZONE3_BIN2_TOP_Y),
        new Translation2d(BOTTOM_SHELF_FRONT_X, ZONE3_BIN3_TOP_Y)
    );

    public static final Zone ZONE_1 = mirror(ZONE_8);
    public static final Zone ZONE_2 = mirror(ZONE_7);
    public static final Zone ZONE_3 = mirror(ZONE_6);

    //------------------------------------------------------------------------------------------------------------
    // Charging station and the one on the other side (maybe name these for the community color?)
    //------------------------------------------------------------------------------------------------------------
    public static final Field_Area CHARGING_STATION_ALL =
        new Field_Area(CHARGING_STATION_ALL_BOTTOM_X, CHARGING_STATION_ALL_BOTTOM_Y, CHARGING_STATION_ALL_TOP_X, CHARGING_STATION_ALL_TOP_Y);
    public static final Field_Area CHARGING_STATION_ON_TOP =
        new Field_Area(CHARGING_STATION_ON_TOP_BOTTOM_X, CHARGING_STATION_ON_TOP_BOTTOM_Y, CHARGING_STATION_ON_TOP_TOP_X, CHARGING_STATION_ON_TOP_TOP_Y);

    public static final Field_Area CHARGING_STATION_OTHER_ALL = mirror(CHARGING_STATION_ALL);
    public static final Field_Area CHARGING_STATION_OTHER_ON_TOP = mirror(CHARGING_STATION_ON_TOP);

    //------------------------------------------------------------------------------------------------------------
    // Starting positions for Auto.  They are also named for the April Tag zone they are closest to.
    //------------------------------------------------------------------------------------------------------------
    private static final double STARTING_POS_OFFSET = 12.0;
    private static final Translation2d START_8 =
        ZONE_8.bottomShelfBelowCenter.getCenterRight().plus(new Translation2d(STARTING_POS_OFFSET, 0));
    private static final Translation2d START_7 =
        ZONE_7.bottomShelfMid.getCenterRight().plus(new Translation2d(STARTING_POS_OFFSET, 0));
    private static final Translation2d START_6 =
        ZONE_6.bottomShelfAboveCenter.getCenterRight().plus(new Translation2d(STARTING_POS_OFFSET, 0));
    private static final Translation2d START_1 = mirror(START_8);
    private static final Translation2d START_2 = mirror(START_7);
    private static final Translation2d START_3 = mirror(START_6);

    public static final Pose2d STARTING_POSE_8 = new Pose2d(START_8, new Rotation2d(Math.PI)); //these were Math.PI*2, assuming they were meant to be Math.PI
    public static final Pose2d STARTING_POSE_7 = new Pose2d(START_7, new Rotation2d(Math.PI));
    public static final Pose2d STARTING_POSE_6 = new Pose2d(START_6, new Rotation2d(Math.PI));
    public static final Pose2d STARTING_POSE_1 = new Pose2d(START_1, new Rotation2d(0.0));
    public static final Pose2d STARTING_POSE_2 = new Pose2d(START_2, new Rotation2d(0.0));
    public static final Pose2d STARTING_POSE_3 = new Pose2d(START_3, new Rotation2d(0.0));

    //----------------------
    // Implementation support
    //----------------------
    public static final double MIDPOINT_X=651.2/2;
    private static Zone mirror(Zone zone) {
        return new Zone(
            mirror(zone.topShelfBelowCenter),
            mirror(zone.topShelfMid),
            mirror(zone.topShelfAboveCenter),
            mirror(zone.midShelfBelowCenter),
            mirror(zone.midShelfMid),
            mirror(zone.midShelfAboveCenter),
            mirror(zone.bottomShelfBelowCenter),
            mirror(zone.bottomShelfMid),
            mirror(zone.bottomShelfAboveCenter),
            mirror(zone.poleTopShelfBelowCenter),
            mirror(zone.poleTopShelfAboveCenter),
            mirror(zone.poleMidShelfBelowCenter),
            mirror(zone.poleMidShelfAboveCenter)
        );
    }
    private static Field_Area mirror(Field_Area area) {
        return new Field_Area(
            mirror(area.getBottomRight()),
            mirror(area.getTopLeft())
        );
    }

    private static Translation2d mirror(Translation2d pt) {
        return new Translation2d(mirror(pt.getX()), pt.getY());
    }
    private static Translation3d mirror(Translation3d pt) {
        return new Translation3d(mirror(pt.getX()), pt.getY(), pt.getZ());
    }
    private static double mirror(double x1) {
        return 2 * MIDPOINT_X - x1;
    }
}
