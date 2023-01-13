package frc.robot.impl.westcoast;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.impl.RobotImpl;
import frc.robot.impl.terry.CanSparkMaxRoboMap;

public class WestCoast extends RobotImpl {
    @Override
    public DifferentialDrive getDrive() {
        return CanSparkMaxRoboMap.getDrive();
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
}
