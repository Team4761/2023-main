package frc.robot.impl.placeholder;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import frc.robot.impl.RobotImpl;
import frc.robot.main.Constants;

public class Placeholder extends RobotImpl {
    // Drivetrain motors. The Talons already have encoders inside them
    public static WPI_TalonFX front_left = new WPI_TalonFX(8);
    public static WPI_TalonFX front_right = new WPI_TalonFX(0);
    public static WPI_TalonFX back_left = new WPI_TalonFX(7);
    public static WPI_TalonFX back_right = new WPI_TalonFX(1);

    //Gyro and PID controllers for PID
    public static ADXRS450_Gyro m_gyro = new ADXRS450_Gyro();

    private final PIDController left_PIDController = new PIDController(.1, 0, .001);

    private final PIDController right_PIDController = new PIDController(-2.5, 0, .001);

    //Encoders that are pre-built into the talons
//    public static CANCoder front_left_CANCoder = new CANCoder(0);
//    public static CANCoder front_right_CANCoder = new CANCoder(1);
//    public static CANCoder back_left_CANCoder = new CANCoder(2);
//    public static CANCoder back_right_CANCoder = new CANCoder(3);
//
//    //Setting encoder variables
//    CANCoderConfiguration CANCoderConfig = new CANCoderConfiguration();
//

    // Drivetrain
    public static final MotorControllerGroup m_leftMotors = new MotorControllerGroup(front_left, back_left);
    public static final MotorControllerGroup m_rightMotors = new MotorControllerGroup(front_right, back_right);
    //    public static final MotorControllerGroup m_rightMotors = new MotorControllerGroup(front_right);
    private static final DifferentialDrive m_drive = new DifferentialDrive(m_leftMotors, m_rightMotors);

    //PID controllers (for auto stuff, maybe drive)
    public static final PIDController linear_PIDcontroller = new PIDController(Constants.LINEAR_P, Constants.LINEAR_I, Constants.LINEAR_D);
    public static final PIDController angular_PIDcontroller = new PIDController(Constants.ANGULAR_P, Constants.ANGULAR_I, Constants.ANGULAR_D);


    private final DifferentialDriveKinematics m_kinematics =
            new DifferentialDriveKinematics(Constants.trackWidth);

    //To be determined
    private final SimpleMotorFeedforward m_feedFoward = new SimpleMotorFeedforward(0.106, 0.76);

    public static boolean doLogging = false;

    public Placeholder() {

        //config of the encoders

//        CANCoderConfig.sensorCoefficient = 0.000244140625; // this is will be (1/4096) * gearRatio ( < 1) * wheelRadius to return meters.
//        CANCoderConfig.unitString = "revs"; //defines the units
//        CANCoderConfig.sensorTimeBase = SensorTimeBase.PerSecond; //defines the time period
//        //the above stuff basically makes it so when you ask for a velocity from the encoder, (getVelocity), it returns revs/sec.
//        //This may want to be converted into meters or yards per second to simplify the goDistanceEncoder command.
//
//        front_left_CANCoder.configAllSettings(CANCoderConfig); //put the config into the encoders
//        front_right_CANCoder.configAllSettings(CANCoderConfig);
//        back_left_CANCoder.configAllSettings(CANCoderConfig);
//        back_right_CANCoder.configAllSettings(CANCoderConfig);

        getDrive().setExpiration(.3);
//        m_rightMotors.setInverted(true);

        m_leftMotors.setInverted(true);

        front_left.configFactoryDefault();
        back_left.configFactoryDefault();
        front_right.configFactoryDefault();
        back_right.configFactoryDefault();
        front_left.setNeutralMode(NeutralMode.Brake);
        back_left.setNeutralMode(NeutralMode.Brake);
        front_right.setNeutralMode(NeutralMode.Brake);
        back_right.setNeutralMode(NeutralMode.Brake);

        front_left.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        front_right.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        back_left.set(ControlMode.Follower, front_left.getDeviceID());
        back_right.set(ControlMode.Follower, front_left.getDeviceID());

        linear_PIDcontroller.setSetpoint(0);
    }

    @Override
    public DifferentialDrive getDrive() {
        return m_drive;
    }

    @Override
    public double calcTimeToMoveFeet(double feet, double speed) {
        double ratio = 0.525;
        return feet * speed * ratio;
    }

    @Override
    public double calcTimeToRotate(double degrees, double speed) {
        double ratio = 0.544;
        return degrees / 90 * speed * ratio;
    }

    public static void logMySpeed() {
        log(front_left, "front_left");
        log(front_right, "front_right");
        log(back_left, "back_left");
        log(back_right, "back_right");
        System.out.println();
    }

    public static void log(WPI_TalonFX motor, String label) {
        if (doLogging) {
            System.out.println(
                    label + ": v=" + motor.getSensorCollection().getIntegratedSensorVelocity() +
                            "  pos=" + motor.getSensorCollection().getIntegratedSensorPosition()
            );
        }

    }

    public static void logGyro() {
        System.out.println(m_gyro.getAngle());
    }

    public static void zeroEncoders() {
        front_right.getSensorCollection().setIntegratedSensorPosition(0, 100);
        front_left.getSensorCollection().setIntegratedSensorPosition(0, 100);
        back_right.getSensorCollection().setIntegratedSensorPosition(0, 100);
        back_left.getSensorCollection().setIntegratedSensorPosition(0, 100);
    }


    public static double averageMotorGroupVelocity(WPI_TalonFX front_motor, WPI_TalonFX back_motor) {
        //Needs to account for gear ratio
        return ((front_motor.getSensorCollection().getIntegratedSensorVelocity() + back_motor.getSensorCollection().getIntegratedSensorVelocity()) / 2);
    }

    public void setSpeeds(DifferentialDriveWheelSpeeds speeds) {
        final double leftFeedforward = m_feedFoward.calculate(speeds.leftMetersPerSecond);
        final double rightFeedforward = m_feedFoward.calculate(speeds.rightMetersPerSecond);

        final double leftOutput =
                left_PIDController.calculate(nativeUnitsToDistanceMeters(Placeholder.averageMotorGroupVelocity(front_left, back_left)), 1);
        final double rightOutput =
                right_PIDController.calculate(nativeUnitsToDistanceMeters(Placeholder.averageMotorGroupVelocity(front_right, back_right)), 1);

        System.out.println("Right Voltage: " + (rightOutput + rightFeedforward));
        System.out.println("Left Voltage: " + (leftFeedforward + leftOutput));

        System.out.println("Right " + averageMotorGroupVelocity(front_right, back_right));
        System.out.println("Left " + averageMotorGroupVelocity(front_left, back_left));
    }

    public void drive(double xSpeed, double rot) {
        var wheelSpeeds = m_kinematics.toWheelSpeeds(new ChassisSpeeds(xSpeed, 0.0, rot));
        setSpeeds(wheelSpeeds);
    }

    private double nativeUnitsToDistanceMeters(double sensorCounts) {
        double motorRotations = (double) sensorCounts / Constants.talonEncoderResolution;
        double wheelRotations = motorRotations / Constants.drivetrainGearRatio;
        double positionMeters = wheelRotations * (2 * Math.PI * Units.inchesToMeters(Constants.wheelRadius));
        return positionMeters;
    }
}
