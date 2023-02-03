package frc.robot.impl.placeholder;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.SensorTimeBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import frc.robot.impl.RobotImpl;

public class Placeholder extends RobotImpl {
    // Drivetrain motors. The Talons already have encoders inside them
    public static WPI_TalonFX front_left = new WPI_TalonFX(8);
    public static WPI_TalonFX front_right = new WPI_TalonFX(0);
    public static WPI_TalonFX back_left = new WPI_TalonFX(7);
    public static WPI_TalonFX back_right = new WPI_TalonFX(1);

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
    public static final MotorControllerGroup m_leftMotors = new MotorControllerGroup(front_left,back_left);
    public static final MotorControllerGroup m_rightMotors = new MotorControllerGroup(front_right,back_right);
//    public static final MotorControllerGroup m_rightMotors = new MotorControllerGroup(front_right);
    private static final DifferentialDrive m_drive = new DifferentialDrive(m_leftMotors,m_rightMotors);



    public Placeholder(){

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


}
