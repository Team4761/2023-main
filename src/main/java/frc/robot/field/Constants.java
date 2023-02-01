package frc.robot.field;

import edu.wpi.first.math.geometry.Translation2d;

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
    public static final double ZONE1_BIN2_TOP_Y=50.0;
    public static final double ZONE1_BIN3_TOP_Y=75.0;
    //ZONE 2
    public static final double ZONE2_BIN1_TOP_Y=99.0;
    public static final double ZONE2_BIN2_TOP_Y=117.25;
    public static final double ZONE2_BIN3_TOP_Y=141.0;
    //ZONE 3
    public static final double ZONE3_BIN1_TOP_Y=165;
    public static final double ZONE3_BIN2_TOP_Y=183.25;
    public static final double ZONE3_BIN3_TOP_Y=216.5;
    //////////
    public static final Translation2d Item1=new Translation2d (ITEM_X,36.25);
    public static final Translation2d Item2=new Translation2d (ITEM_X,84.25);
    public static final Translation2d Item3=new Translation2d (ITEM_X,132.25);
    public static final Translation2d Item4=new Translation2d (ITEM_X,180.25);
    
    //ZONE 1
    
    ///Zone 1 Top
    public static final Field_Area ZONE1_BIN1_TOP_LEFT = new Field_Area(ORIGIN_X, ORIGIN_Y,UPPER_SHELF_FRONT_X,ZONE1_BIN1_TOP_Y);
    public static final Field_Area ZONE1_BIN2_TOP_MID = new Field_Area(ORIGIN_X,ZONE1_BIN1_TOP_Y,UPPER_SHELF_FRONT_X,ZONE1_BIN2_TOP_Y);
    public static final Field_Area ZONE1_BIN3_TOP_RIGHT = new Field_Area(ORIGIN_X, ZONE1_BIN2_TOP_Y,UPPER_SHELF_FRONT_X,ZONE1_BIN3_TOP_Y);
    //Zone 1 Mid
    public static final Field_Area ZONE1_BIN1_MID_LEFT = new Field_Area(UPPER_SHELF_FRONT_X, ORIGIN_Y,MIDDLE_SHELF_FRONT_X,ZONE1_BIN1_TOP_Y);
    public static final Field_Area ZONE1_BIN2_MID_MID = new Field_Area(UPPER_SHELF_FRONT_X,ZONE1_BIN1_TOP_Y,MIDDLE_SHELF_FRONT_X,ZONE1_BIN2_TOP_Y);
    public static final Field_Area ZONE1_BIN3_MID_RIGHT = new Field_Area(UPPER_SHELF_FRONT_X, ZONE1_BIN2_TOP_Y,MIDDLE_SHELF_FRONT_X,ZONE1_BIN3_TOP_Y);
   //Zone 1 Bottom
   public static final Field_Area ZONE1_BIN1_BOTTOM_LEFT = new Field_Area(MIDDLE_SHELF_FRONT_X, ORIGIN_Y,BOTTOM_SHELF_FRONT_X,ZONE1_BIN1_TOP_Y);
    public static final Field_Area ZONE1_BIN2_BOTTOM_MID = new Field_Area(MIDDLE_SHELF_FRONT_X,ZONE1_BIN1_TOP_Y,BOTTOM_SHELF_FRONT_X,ZONE1_BIN2_TOP_Y);
    public static final Field_Area ZONE1_BIN3_BOTTOM_RIGHT = new Field_Area(MIDDLE_SHELF_FRONT_X, ZONE1_BIN2_TOP_Y,BOTTOM_SHELF_FRONT_X,ZONE1_BIN3_TOP_Y);
    
    //ZONE 2
    
    ///Zone 2 Top
    public static final Field_Area ZONE2_BIN1_TOP_LEFT = new Field_Area(ORIGIN_X, ZONE1_BIN3_TOP_Y,UPPER_SHELF_FRONT_X,ZONE2_BIN1_TOP_Y);
    public static final Field_Area ZONE2_BIN2_TOP_MID = new Field_Area(ORIGIN_X,ZONE2_BIN1_TOP_Y,UPPER_SHELF_FRONT_X,ZONE2_BIN2_TOP_Y);
    public static final Field_Area ZONE2_BIN3_TOP_RIGHT = new Field_Area(ORIGIN_X, ZONE2_BIN2_TOP_Y,UPPER_SHELF_FRONT_X,ZONE2_BIN3_TOP_Y);
    //Zone 2 Mid
    public static final Field_Area ZONE2_BIN1_MID_LEFT = new Field_Area(UPPER_SHELF_FRONT_X, ZONE1_BIN3_TOP_Y,MIDDLE_SHELF_FRONT_X,ZONE2_BIN1_TOP_Y);
    public static final Field_Area ZONE2_BIN2_MID_MID = new Field_Area(UPPER_SHELF_FRONT_X,ZONE2_BIN1_TOP_Y,MIDDLE_SHELF_FRONT_X,ZONE2_BIN2_TOP_Y);
    public static final Field_Area ZONE2_BIN3_MID_RIGHT = new Field_Area(UPPER_SHELF_FRONT_X, ZONE2_BIN2_TOP_Y,MIDDLE_SHELF_FRONT_X,ZONE2_BIN3_TOP_Y);
    //Zone 2 Bottom
    public static final Field_Area ZONE2_BIN1_BOTTOM_LEFT = new Field_Area(MIDDLE_SHELF_FRONT_X, ZONE1_BIN3_TOP_Y,BOTTOM_SHELF_FRONT_X,ZONE2_BIN1_TOP_Y);
    public static final Field_Area ZONE2_BIN2_BOTTOM_MID = new Field_Area(MIDDLE_SHELF_FRONT_X,ZONE2_BIN1_TOP_Y,BOTTOM_SHELF_FRONT_X,ZONE2_BIN2_TOP_Y);
    public static final Field_Area ZONE2_BIN3_BOTTOM_RIGHT = new Field_Area(MIDDLE_SHELF_FRONT_X, ZONE2_BIN2_TOP_Y,BOTTOM_SHELF_FRONT_X,ZONE2_BIN3_TOP_Y);

    //ZONE 3

    ///Zone 3 Top
    public static final Field_Area ZONE3_BIN1_TOP_LEFT = new Field_Area(ORIGIN_X, ZONE2_BIN3_TOP_Y,UPPER_SHELF_FRONT_X,ZONE3_BIN1_TOP_Y);
    public static final Field_Area ZONE3_BIN2_TOP_MID = new Field_Area(ORIGIN_X,ZONE3_BIN1_TOP_Y,UPPER_SHELF_FRONT_X,ZONE3_BIN2_TOP_Y);
    public static final Field_Area ZONE3_BIN3_TOP_RIGHT = new Field_Area(ORIGIN_X, ZONE3_BIN2_TOP_Y,UPPER_SHELF_FRONT_X,ZONE3_BIN3_TOP_Y);
    //Zone 3 Mid
    public static final Field_Area ZONE3_BIN1_MID_LEFT = new Field_Area(UPPER_SHELF_FRONT_X, ZONE2_BIN3_TOP_Y,MIDDLE_SHELF_FRONT_X,ZONE3_BIN1_TOP_Y);
    public static final Field_Area ZONE3_BIN2_MID_MID = new Field_Area(UPPER_SHELF_FRONT_X,ZONE3_BIN1_TOP_Y,MIDDLE_SHELF_FRONT_X,ZONE3_BIN2_TOP_Y);
    public static final Field_Area ZONE3_BIN3_MID_RIGHT = new Field_Area(UPPER_SHELF_FRONT_X, ZONE3_BIN2_TOP_Y,MIDDLE_SHELF_FRONT_X,ZONE3_BIN3_TOP_Y);
    //Zone 3 Bottom
    public static final Field_Area ZONE3_BIN1_BOTTOM_LEFT = new Field_Area(MIDDLE_SHELF_FRONT_X, ZONE2_BIN3_TOP_Y,BOTTOM_SHELF_FRONT_X,ZONE3_BIN1_TOP_Y);
    public static final Field_Area ZONE3_BIN2_BOTTOM_MID = new Field_Area(MIDDLE_SHELF_FRONT_X,ZONE3_BIN1_TOP_Y,BOTTOM_SHELF_FRONT_X,ZONE3_BIN2_TOP_Y);
    public static final Field_Area ZONE3_BIN3_BOTTOM_RIGHT = new Field_Area(MIDDLE_SHELF_FRONT_X, ZONE3_BIN2_TOP_Y,BOTTOM_SHELF_FRONT_X,ZONE3_BIN3_TOP_Y);
    
    //Charging Station
    public static final Field_Area CHARGING_STATION_ALL = new Field_Area(CHARGING_STATION_ALL_BOTTOM_X, CHARGING_STATION_ALL_BOTTOM_Y, CHARGING_STATION_ALL_TOP_X, CHARGING_STATION_ALL_TOP_Y);
    public static final Field_Area CHARGING_STATION_ON_TOP = new Field_Area(CHARGING_STATION_ON_TOP_BOTTOM_X, CHARGING_STATION_ON_TOP_BOTTOM_Y, CHARGING_STATION_ON_TOP_TOP_X, CHARGING_STATION_ON_TOP_TOP_Y);

    public static final double MIDPOINT_X=343.5;
    public static double mirror(double x1) {
        x1-=MIDPOINT_X;
        if (x1==0) {
            return MIDPOINT_X;
        }
        else {
            x1 *= -1;
            x1+=MIDPOINT_X;
            return x1;
        }
    }
    ////Other Side of the Field
    public static final double ORIGIN_X_F=mirror(0.0);

    public static final double UPPER_SHELF_FRONT_X_F=mirror(0.0);
    public static final double MIDDLE_SHELF_FRONT_X_F=mirror (40.5);
    public static final double BOTTOM_SHELF_FRONT_X_F=mirror(54.75);
    public static final double CHARGING_STATION_ALL_BOTTOM_X_F=mirror(115.45);

    public static final double CHARGING_STATION_ALL_TOP_X_F=mirror(191.45);
    public static final double CHARGING_STATION_ON_TOP_BOTTOM_X_F=mirror(129.45);
    public static final double CHARGING_STATION_ON_TOP_TOP_X_F=mirror(177.45);


    public static final double ITEM_X_F=mirror(224.0);

    public static final Translation2d Item1_F=new Translation2d (ITEM_X_F,36.25);
    public static final Translation2d Item2_F=new Translation2d (ITEM_X_F,84.25);
    public static final Translation2d Item3_F=new Translation2d (ITEM_X_F,132.25);
    public static final Translation2d Item4_F=new Translation2d (ITEM_X_F,180.25);

    //ZONE 1

    ///Zone 1 Top
    public static final Field_Area ZONE1_BIN1_TOP_LEFT_F = new Field_Area(ORIGIN_X_F, ORIGIN_Y,UPPER_SHELF_FRONT_X_F,ZONE1_BIN1_TOP_Y);
    public static final Field_Area ZONE1_BIN2_TOP_MID_F = new Field_Area(ORIGIN_X_F,ZONE1_BIN1_TOP_Y,UPPER_SHELF_FRONT_X_F,ZONE1_BIN2_TOP_Y);
    public static final Field_Area ZONE1_BIN3_TOP_RIGHT_F = new Field_Area(ORIGIN_X_F, ZONE1_BIN2_TOP_Y,UPPER_SHELF_FRONT_X_F,ZONE1_BIN3_TOP_Y);
    //Zone 1 Mid
    public static final Field_Area ZONE1_BIN1_FID_LEFT_F = new Field_Area(UPPER_SHELF_FRONT_X_F, ORIGIN_Y,MIDDLE_SHELF_FRONT_X_F,ZONE1_BIN1_TOP_Y);
    public static final Field_Area ZONE1_BIN2_FID_MID_F = new Field_Area(UPPER_SHELF_FRONT_X_F,ZONE1_BIN1_TOP_Y,MIDDLE_SHELF_FRONT_X_F,ZONE1_BIN2_TOP_Y);
    public static final Field_Area ZONE1_BIN3_FID_RIGHT_F = new Field_Area(UPPER_SHELF_FRONT_X_F, ZONE1_BIN2_TOP_Y,MIDDLE_SHELF_FRONT_X_F,ZONE1_BIN3_TOP_Y);
    //Zone 1 Bottom
    public static final Field_Area ZONE1_BIN1_BOTTOM_LEFT_F = new Field_Area(MIDDLE_SHELF_FRONT_X_F, ORIGIN_Y,BOTTOM_SHELF_FRONT_X_F,ZONE1_BIN1_TOP_Y);
    public static final Field_Area ZONE1_BIN2_BOTTOM_MID_F = new Field_Area(MIDDLE_SHELF_FRONT_X_F,ZONE1_BIN1_TOP_Y,BOTTOM_SHELF_FRONT_X_F,ZONE1_BIN2_TOP_Y);
    public static final Field_Area ZONE1_BIN3_BOTTOM_RIGHT_F = new Field_Area(MIDDLE_SHELF_FRONT_X_F, ZONE1_BIN2_TOP_Y,BOTTOM_SHELF_FRONT_X_F,ZONE1_BIN3_TOP_Y);

    //ZONE 2

    ///Zone 2 Top
    public static final Field_Area ZONE2_BIN1_TOP_LEFT_F = new Field_Area(ORIGIN_X_F, ZONE1_BIN3_TOP_Y,UPPER_SHELF_FRONT_X_F,ZONE2_BIN1_TOP_Y);
    public static final Field_Area ZONE2_BIN2_TOP_MID_F = new Field_Area(ORIGIN_X_F,ZONE2_BIN1_TOP_Y,UPPER_SHELF_FRONT_X_F,ZONE2_BIN2_TOP_Y);
    public static final Field_Area ZONE2_BIN3_TOP_RIGHT_F = new Field_Area(ORIGIN_X_F, ZONE2_BIN2_TOP_Y,UPPER_SHELF_FRONT_X,ZONE2_BIN3_TOP_Y);
    //Zone 2 Mid
    public static final Field_Area ZONE2_BIN1_FID_LEFT_F = new Field_Area(UPPER_SHELF_FRONT_X_F, ZONE1_BIN3_TOP_Y,MIDDLE_SHELF_FRONT_X_F,ZONE2_BIN1_TOP_Y);
    public static final Field_Area ZONE2_BIN2_FID_MID_F = new Field_Area(UPPER_SHELF_FRONT_X_F,ZONE2_BIN1_TOP_Y,MIDDLE_SHELF_FRONT_X_F,ZONE2_BIN2_TOP_Y);
    public static final Field_Area ZONE2_BIN3_FID_RIGHT_F = new Field_Area(UPPER_SHELF_FRONT_X_F, ZONE2_BIN2_TOP_Y,MIDDLE_SHELF_FRONT_X_F,ZONE2_BIN3_TOP_Y);
    //Zone 2 Bottom
    public static final Field_Area ZONE2_BIN1_BOTTOM_LEFT_F = new Field_Area(MIDDLE_SHELF_FRONT_X_F, ZONE1_BIN3_TOP_Y,BOTTOM_SHELF_FRONT_X_F,ZONE2_BIN1_TOP_Y);
    public static final Field_Area ZONE2_BIN2_BOTTOM_MID_F = new Field_Area(MIDDLE_SHELF_FRONT_X_F,ZONE2_BIN1_TOP_Y,BOTTOM_SHELF_FRONT_X_F,ZONE2_BIN2_TOP_Y);
    public static final Field_Area ZONE2_BIN3_BOTTOM_RIGHT_F = new Field_Area(MIDDLE_SHELF_FRONT_X_F, ZONE2_BIN2_TOP_Y,BOTTOM_SHELF_FRONT_X_F,ZONE2_BIN3_TOP_Y);

    //ZONE 3

    ///Zone 3 Top
    public static final Field_Area ZONE3_BIN1_TOP_LEFT_F = new Field_Area(ORIGIN_X_F, ZONE2_BIN3_TOP_Y,UPPER_SHELF_FRONT_X_F,ZONE3_BIN1_TOP_Y);
    public static final Field_Area ZONE3_BIN2_TOP_FID_F = new Field_Area(ORIGIN_X_F,ZONE3_BIN1_TOP_Y,UPPER_SHELF_FRONT_X_F,ZONE3_BIN2_TOP_Y);
    public static final Field_Area ZONE3_BIN3_TOP_RIGHT_F = new Field_Area(ORIGIN_X_F, ZONE3_BIN2_TOP_Y,UPPER_SHELF_FRONT_X_F,ZONE3_BIN3_TOP_Y);
    //Zone 3 Mid
    public static final Field_Area ZONE3_BIN1_FID_LEFT_F = new Field_Area(UPPER_SHELF_FRONT_X_F, ZONE2_BIN3_TOP_Y,MIDDLE_SHELF_FRONT_X_F,ZONE3_BIN1_TOP_Y);
    public static final Field_Area ZONE3_BIN2_FID_MID_F = new Field_Area(UPPER_SHELF_FRONT_X_F,ZONE3_BIN1_TOP_Y,MIDDLE_SHELF_FRONT_X_F,ZONE3_BIN2_TOP_Y);
    public static final Field_Area ZONE3_BIN3_FID_RIGHT_F = new Field_Area(UPPER_SHELF_FRONT_X_F, ZONE3_BIN2_TOP_Y,MIDDLE_SHELF_FRONT_X_F,ZONE3_BIN3_TOP_Y);
    //Zone 3 Bottom
    public static final Field_Area ZONE3_BIN1_BOTTOM_LEFT_F = new Field_Area(MIDDLE_SHELF_FRONT_X_F, ZONE2_BIN3_TOP_Y,BOTTOM_SHELF_FRONT_X_F,ZONE3_BIN1_TOP_Y);
    public static final Field_Area ZONE3_BIN2_BOTTOM_MID_F = new Field_Area(MIDDLE_SHELF_FRONT_X_F,ZONE3_BIN1_TOP_Y,BOTTOM_SHELF_FRONT_X_F,ZONE3_BIN2_TOP_Y);
    public static final Field_Area ZONE3_BIN3_BOTTOM_RIGHT_F = new Field_Area(MIDDLE_SHELF_FRONT_X_F, ZONE3_BIN2_TOP_Y,BOTTOM_SHELF_FRONT_X_F,ZONE3_BIN3_TOP_Y);

    //Charging Station
    public static final Field_Area CHARGING_STATION_ALL_F = new Field_Area(CHARGING_STATION_ALL_BOTTOM_X_F, CHARGING_STATION_ALL_BOTTOM_Y, CHARGING_STATION_ALL_TOP_X_F, CHARGING_STATION_ALL_TOP_Y);
    public static final Field_Area CHARGING_STATION_ON_TOP_F = new Field_Area(CHARGING_STATION_ON_TOP_BOTTOM_X_F, CHARGING_STATION_ON_TOP_BOTTOM_Y, CHARGING_STATION_ON_TOP_TOP_X_F, CHARGING_STATION_ON_TOP_TOP_Y);


    
    
    
    
    
}
