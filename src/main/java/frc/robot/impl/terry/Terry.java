package frc.robot.impl.terry;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.impl.RobotImpl;

public class Terry extends RobotImpl {

    @Override
    public void init() {
        super.init();
        getDrive().setExpiration(.3);
        TalonRoboMap.m_rightMotors.setInverted(true);
    }

    @Override
    public DifferentialDrive getDrive() {
        return TalonRoboMap.getDrive();
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
