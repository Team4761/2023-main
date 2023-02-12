package frc.robot.main;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

public class Constants {

    public static final double drivetrainGearRatio = 1;// Estimate //TODO to be determined, will be degrees of CANCoder to distance
    public static final double wheelRadius = 2; // Inches : 2 Meters : 0.0508 //TODO to be determined, will be in yards or meters, dunno
    public static final double wheelCircumference = wheelRadius * 2 * Math.PI;

    public static final double weight = 10; //TODO in lbs, need to find

    public static final double distancePerEncoderTick = wheelCircumference / 4096;

    public static final double talonEncoderResolution = 4096;

    public static final double trackWidth = 19.5 ;//Inches : 19.5 //Meters : 0.4953


    //PID constants for robot.
    public static  double LINEAR_P = 0.01;
    public static  double LINEAR_I = 0;
    public static  double LINEAR_D = 0.1;

    public static  double ANGULAR_P = 0.01;
    public static  double ANGULAR_I = 0;
    public static  double ANGULAR_D = 0.1;

    public static Pose2d goalPose = new Pose2d(0,0,new Rotation2d(0,0));









}
