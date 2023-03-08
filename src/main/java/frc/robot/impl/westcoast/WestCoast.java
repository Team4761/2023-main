package frc.robot.impl.westcoast;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.impl.RobotImpl;

public class WestCoast extends RobotImpl {
    public static CANSparkMax left = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
    public static CANSparkMax right = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);
    public static DifferentialDrive m_drive = new DifferentialDrive(left,right);

    public WestCoast() {
    }

    @Override
    public double calcTimeToMoveFeet(double feet, double speed) {
        double ratio = 0.6;
        return feet * speed * ratio;
    }
    @Override
    public double calcTimeToRotate(double degrees, double speed) {
        double ratio = 0.9;
        return degrees / 90 * speed * ratio;
    }

    @Override
    public void drive(double xSpeed, double rot) {

    }
}
