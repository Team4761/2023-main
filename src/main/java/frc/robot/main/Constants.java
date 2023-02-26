package frc.robot.main;

import edu.wpi.first.math.geometry.Translation2d;

public class Constants {

    // Ports & LEDs
    public static final int LED_PORT = 0;                       // RoboRIO, PWM port
    public static final int LED_NUMBER = 60;                    // Number of LEDs
    public static final int ARM_ENCODER_TOP_PORT = 9;           // RoboRIO, DIO port
    public static final int ARM_ENCODER_BOTTOM_PORT = 0;        // RoboRIO, DIO port
    //Ports are for new arm old ports on side or the same
    public static final int ARM_MOTOR_BOTTOM_LEFT_PORT = 3;     // CAN, initialized port //same ports
    public static final int ARM_MOTOR_BOTTOM_RIGHT_PORT = 2;    // CAN, initialized port //42 for new arm, 2 for old
    public static final int ARM_MOTOR_TOP_PORT = 1;             // CAN, initialized port //same ports
    public static final int ARM_MOTOR_INTAKE_PORT = 4;          // CAN, initialized port //only for new arm


    // Drivetrain constants.
    public static final double drivetrainGearRatio = 8; //TODO to be determined, will be degrees of CANCoder to distance
    public static final double wheelRadius = 0.0508; //meters, dunno
    public static final double wheelCircumference = wheelRadius * 2 * Math.PI;
    public static final double weight = 10; //TODO in lbs, need to find

    public static final double talonEncoderResolution = 2048;

    public static final double distancePerEncoderTick = wheelCircumference / talonEncoderResolution / drivetrainGearRatio;

    public static final double trackWidth = 0.5;//Inches : 19.5 //Meters : 0.4953

    public static final double DRIVETRAIN_MAX_ACCELERATION = 1.5; // random number for purepuresuit
    public static final double DRIVETRAIN_MAX_VELOCITY = 1.1; // random number for purepuresuit

    // Arm Constants
    static final public double ARM_LENGTH_BOTTOM = 22.5;         // Bottom Arm?  //now 22.5 was 25
    static final public double ARM_LENGTH_TOP = 28.5;            // Top Arm?     //now 28.5 was 30
    static public double ARM_P_TOP = 0.6;                        // PID control for arms
    static public double ARM_I_TOP = 0.0;                        // PID control for arms
    static public double ARM_D_TOP = 0.1;                        // PID control for arms
    static public double ARM_P_BOTTOM = 0.8;                     // PID control for arms
    static public double ARM_I_BOTTOM = 0.0;                     // PID control for arms
    static public double ARM_D_BOTTOM = 0.0;                     // PID control for arms
    static final public double ARM_MAX_ACCELERATION_SPEED = 0.1;
    static final public double ARM_MAX_ROTATION_SPEED = 1.0;
    static final public double ENCODER_ZERO_VALUE_TOP = 4.6; //PAST: 0.6189683810569215
    static final public double ENCODER_ZERO_VALUE_BOTTOM = 5.9; //PAST: 5.06664074953627
    public static final double JOINT_2_MAX = 110.8;
    public static final double JOINT_2_MIN = -200.5;
    public static final double JOINT_1_MAX = 100;
    public static final double JOINT_1_MIN = -100;
    public static final double KINEMATICS_OFFSET_TOP = 0.0;
    public static final double KINEMATICS_OFFSET_BOTTOM = 0.0;
    public static final double CONTROLLER_DEADZONE = 0.05;

    //PID constants for robot.
    public static final double LINEAR_P = 0.01;
    public static final double LINEAR_I = 0;
    public static final double LINEAR_D = 0.1;

    public static final double ANGULAR_P = 0.01;
    public static final double ANGULAR_I = 0;
    public static final double ANGULAR_D = 0.1;


    // Set Positions
    public static final Translation2d INTAKE_POSITION = new Translation2d(0.0, 0.0);    // x & y are angles in radians (top_rotation, bottom_rotation)
}
