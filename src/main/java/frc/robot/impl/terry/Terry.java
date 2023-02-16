package frc.robot.impl.terry;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import frc.robot.impl.RobotImpl;

public class Terry extends RobotImpl {
    // Drivetrain motors. The Talons already have encoders inside them
    public static WPI_TalonFX front_left = new WPI_TalonFX(0);
    public static WPI_TalonFX front_right = new WPI_TalonFX(1);
    public static WPI_TalonFX back_left = new WPI_TalonFX(2);
    public static WPI_TalonFX back_right = new WPI_TalonFX(3);

    // Drivetrain
    public static final MotorControllerGroup m_leftMotors = new MotorControllerGroup(front_left, back_left);
    public static final MotorControllerGroup m_rightMotors = new MotorControllerGroup(front_right, back_right);


    private static final DifferentialDrive m_drive = new DifferentialDrive(m_leftMotors, m_rightMotors);

    public Terry() {
        getDrive().setExpiration(.3);
        m_rightMotors.setInverted(true);
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

    @Override
    public void drive(double xSpeed, double rot) {

    }
}
