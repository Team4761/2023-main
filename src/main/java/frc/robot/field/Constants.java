package frc.robot.field;

public class Constants {
    public static final double ORIGIN_X =0.0;
    public static final double ORIGIN_Y=0.0;
    public static final double UPPER_SHELF_FRONT_X=22.5;
    public static final double MIDDLE_SHELF_FRONT_X=40.5;
    public static final double BOTTOM_SHELF_FRONT_X=54.75;
    public static final double CHARGING_STATION_ALL_BOTTOM_X=115.45;
    public static final double CHARGING_STATION_ALL_BOTTOM_Y=59.4;
    public static final double CHARGING_STATION_ALL_TOP_X=191.45;
    public static final double CHARGING_STATION_ALL_TOP_Y=155.4;

    public static final double CHARGING_STATION_ON_TOP_BOTTOM_X=129.45;
    public static final double CHARGING_STATION_ON_TOP_BOTTOM_Y=59.4;
    public static final double CHARGING_STATION_ON_TOP_TOP_X=177.45;
    public static final double CHARGING_STATION_ON_TOP_TOP_Y=155.4;

    public static final double ITEM_X=224.0;
    //ZONE 1
    public static final double ZONE1_BIN1_TOP_Y=33.0;
    public static final double ZONE1_BIN2_TOP_Y=ZONE1_BIN1_TOP_Y + 18.25;
    public static final double ZONE1_BIN3_TOP_Y=ZONE1_BIN2_TOP_Y + 47.75/2;
    //ZONE 2
    public static final double ZONE2_BIN1_TOP_Y=ZONE1_BIN3_TOP_Y + 47.75/2;
    public static final double ZONE2_BIN2_TOP_Y=ZONE2_BIN1_TOP_Y + 18.25;
    public static final double ZONE2_BIN3_TOP_Y=ZONE2_BIN2_TOP_Y + 47.75/2;
    //ZONE 3
    public static final double ZONE3_BIN1_TOP_Y=ZONE2_BIN3_TOP_Y + 47.75/2;
    public static final double ZONE3_BIN2_TOP_Y=ZONE3_BIN1_TOP_Y + 18.25;
    public static final double ZONE3_BIN3_TOP_Y=ZONE3_BIN2_TOP_Y + 33.22;

    // Heights
    public static final double LOWER_SHELF_HEIGHT = 23.5;
    public static final double HIGHER_SHELF_HEIGHT = 35.5;
    public static final double CONE_POLE_DIAMETER = 1.25;
    public static final double CONE_HEIGHT_LOWER = 34.0;
    public static final double CONE_HEIGHT_HIGHER = 46.0;
    public static final double CONE_LOWER_X_OFFSET_FROM_ZONE_FRONT = 22.75;
    public static final double CONE_HIGHER_X_OFFSET_FROM_ZONE_FRONT = 39.75;
    public static final double CONE_Y_OFFSET_FROM_CENTER_WALL = 47.75/4; // center point of a narrow bin
}
