package frc.robot.impl.westcoast;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.impl.RobotImpl;
import frc.robot.main.Constants;

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
    public void setVoltages(double left, double right) {
        WestCoast.left.setVoltage(left);
        WestCoast.right.setVoltage(right);
    }

    @Override
    public void drive(double xSpeed, double rot) {

    }

    public double getTrackWidth() {
        return Units.inchesToMeters(24);
    }

    @Override
    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(
            left.getEncoder().getVelocity(),
            right.getEncoder().getVelocity()
        );
    }
}
