package frc.robot.main;

import edu.wpi.first.math.geometry.Translation2d;

public class Constants {

    // Ports & LEDs
    public static final int LED_PORT = 0;                       // RoboRIO, PWM port
    public static final int LED_NUMBER = 30;                    // Number of LEDs
    public static final int ARM_ENCODER_TOP_PORT = 9;           // RoboRIO, DIO port
    public static final int ARM_ENCODER_BOTTOM_PORT = 0;        // RoboRIO, DIO port
    //Ports are for new arm old ports on side or the same
    public static final int ARM_MOTOR_BOTTOM_LEFT_PORT = 3;     // CAN, initialized port //same ports
    public static final int ARM_MOTOR_BOTTOM_RIGHT_PORT = 2;    // CAN, initialized port //42 for new arm, 2 for old
    public static final int ARM_MOTOR_TOP_LEFT_PORT = 1;        // CAN, initialized port //same ports
    public static final int ARM_MOTOR_TOP_RIGHT_PORT = 5;        // CAN, initialized port //same ports
    public static final int ARM_MOTOR_INTAKE_PORT = 4;          // CAN, initialized port //only for new arm
    public static final double ROBOT_LENGTH = 27;
    public static final double ROBOT_WIDTH = 24;
    public static final double ROBOT_HEIGHT = 5;

    // Drivetrain constants.
    public static final double drivetrainGearRatio = 8.0;
    public static final double wheelRadiusInches = 2.0;
    public static double DRIVETRAIN_SPEED = 5.0;
    public static double DRIVETRAIN_ROTATION_SPEED = 0.8;
    //public static final double wheelCircumference = wheelRadiusInches * 2 * Math.PI;
    public static final double wheelRadius = 0.0508; //meters
    public static final double wheelCircumference = wheelRadius * 2 * Math.PI;
    public static final double weight = 10; //TODO in lbs, need to find

    public static final double talonEncoderResolution = 2048;

    public static final double distancePerEncoderTick = wheelCircumference / talonEncoderResolution / drivetrainGearRatio;

    public static final double trackWidth = 0.5;//Inches : 19.5 //Meters : 0.4953

    public static final double DRIVETRAIN_MAX_ACCELERATION = 2.5; // meters/s^2
    public static final double DRIVETRAIN_MAX_VELOCITY = 1.1; // meters/s

    // Arm Constants
    static final public double ARM_LENGTH_BOTTOM = 22.5;         // Bottom Arm?  //now 22.5 was 25
    static final public double ARM_LENGTH_TOP = 39.0;            // Top Arm?     //now 28.5 was 30
    static public double ARM_P_TOP = 0.4;                        // PID control for arms
    static public double ARM_I_TOP = 0.0;                        // PID control for arms
    static public double ARM_D_TOP = 0.1;                        // PID control for arms
    static public double ARM_P_BOTTOM = 0.6;                     // PID control for arms
    static public double ARM_I_BOTTOM = 0.0;                     // PID control for arms
    static public double ARM_D_BOTTOM = 0.0;                     // PID control for arms
    static final public double ARM_MAX_ACCELERATION_SPEED = 0.1;
    static final public double ARM_MAX_ROTATION_SPEED = 1.0;
    static final public double ENCODER_ZERO_VALUE_TOP = 4.6; //PAST: 0.6189683810569215
    static final public double ENCODER_ZERO_VALUE_BOTTOM = 3.9; //PAST: 5.06664074953627
    public static final double JOINT_2_MAX = 110.8;
    public static final double JOINT_2_MIN = -200.5;
    public static final double JOINT_1_MAX = 100;
    public static final double JOINT_1_MIN = -100;
    public static final double KINEMATICS_OFFSET_TOP = Math.toRadians(15);
    public static final double KINEMATICS_OFFSET_BOTTOM = Math.toRadians(15);
    public static final double FLAT_ARM_TOP_OFFSET = 0.0;   // To tune
    public static final double FLAT_ARM_BOTTOM_OFFSET = 0.0;   // To tune
    public static final double CONTROLLER_DEADZONE = 0.05;
    
    //Arm Feedforward
    public static final double TOP_LENGTH = 1.016;//m
    public static final double TOP_MASS = 5.08;//kg
    public static final double TOP_MOI = 2.05;//kgm^2
    public static final double TOP_CGRADIUS = 0.635;//m
    public static final DCMotor TOP_MOTOR = DCMotor.getNEO(2).withReduction(45.45454545);
    public static final double BOTTOM_LENGTH = 0.5715;//m
    public static final double BOTTOM_MASS = 2.404;//kg
    public static final double BOTTOM_MOI = 0.207;//kgm^2
    public static final double BOTTOM_CGRADIUS = 0.1955;//m
    public static final DCMotor BOTTOM_MOTOR = DCMotor.getNEO(2).withReduction(40);

    //PID constants for robot.
    public static final double LINEAR_P = 0.01;
    public static final double LINEAR_I = 0;
    public static final double LINEAR_D = 0.1;

    public static final double ANGULAR_P = 0.01;
    public static final double ANGULAR_I = 0;
    public static final double ANGULAR_D = 0.1;


    // Set Positions
    public static final Translation2d INTAKE_POSITION = new Translation2d(0.18, 0.270);    // x & y are angles in radians (top_rotation, bottom_rotation)
    public static final Translation2d MID_RUNG_POSITION = new Translation2d(2.0, 2.4);    // x & y are angles in radians (top_rotation, bottom_rotation) [Went to 2.1, 2.2]
    public static final Translation2d TOP_RUNG_POSITION = new Translation2d(2.22, 3.14);    // x & y are angles in radians (top_rotation, bottom_rotation) [Went to 2.4, 3.02]
    public static final Translation2d STARTING_POSITION = new Translation2d(1.50, 0.08);    // x & y are angles in radians (top_rotation, bottom_rotation) [Went to 2.4, 3.02]
}
