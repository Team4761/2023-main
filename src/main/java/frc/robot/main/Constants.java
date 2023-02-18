package frc.robot.main;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

public class Constants {

    // Ports.
    public static final int LED_PORT = 0;                       // RoboRIO, PWM port
    public static final int LED_NUMBER = 8;                     // Number of LEDs
    public static final int ARM_ENCODER_TOP_PORT = 9;           // RoboRIO, DIO port
    public static final int ARM_ENCODER_BOTTOM_PORT = 0;        // RoboRIO, DIO port
    public static final int ARM_MOTOR_BOTTOM_LEFT_PORT = 3;     // CAN, initialized port
    public static final int ARM_MOTOR_BOTTOM_RIGHT_PORT = 2;    // CAN, initialized port
    public static final int ARM_MOTOR_TOP_PORT = 1;             // CAN, initialized port


    // Drivetrain constants.
    public static final double drivetrainGearRatio = 0.01; //TODO to be determined, will be degrees of CANCoder to distance
    public static final double wheelRadius = 0.01; //TODO to be determined, will be in yards or meters, dunno
    public static final double wheelCircumference = wheelRadius * 2 * Math.PI;
    public static final double weight = 10; //TODO in lbs, need to find

    public static final double distancePerEncoderTick = wheelCircumference / Constants.talonEncoderResolution;

    public static final double talonEncoderResolution = 2048;

    public static final double trackWidth = 19.5 ;//Inches : 19.5 //Meters : 0.4953

    // Arm Constants
    static final public double ARM_LENGTH_BOTTOM = 25.0;        // Bottom Arm?
    static final public double ARM_LENGTH_TOP = 30.0;           // Top Arm?
    static final public double ARM_P = 0.5;                     // PID control for arms
    static final public double ARM_I = 0.0;                     // PID control for arms
    static final public double ARM_D = 0.0;                     // PID control for arms
    static final public double ARM_MAX_ACCELERATION_SPEED = 0.1;
    static final public double ARM_MAX_ROTATION_SPEED = 1.0;
    static final public double ENCODER_ZERO_VALUE_TOP = 0.6189683810569215;
    static final public double ENCODER_ZERO_VALUE_BOTTOM = 5.06664074953627;

    //pathweaver constants (ramsete stuff)
    public static final double ksVolts = 0.22;   //TODO NOT FINAL, DO NOT USE WITHOUT REAL VALUES
    public static final double kvVoltSecondsPerMeter = 1.98;
    public static final double kaVoltSecondsSquaredPerMeter = 0.2;
    public static final double kPDriveVel = 8.5;
    //define kinematics thing
    public static final DifferentialDriveKinematics kDriveKinematics =
            new DifferentialDriveKinematics(trackWidth);
    //set max values
    public static final double kMaxSpeedMetersPerSecond = 3;
    public static final double kMaxAccelerationMetersPerSecondSquared = 1;
    //ramsete constants
    public static final double kRamseteB = 2;
    public static final double kRamseteZeta = 0.7;


    //PID constants for robot.
    public static final double LINEAR_P = 0.01;
    public static final double LINEAR_I = 0;
    public static final double LINEAR_D = 0.1;

    public static final double ANGULAR_P = 0.01;
    public static final double ANGULAR_I = 0;
    public static final double ANGULAR_D = 0.1;











}
