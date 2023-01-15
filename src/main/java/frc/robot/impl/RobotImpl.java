package frc.robot.impl;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public abstract class RobotImpl {
    /** Override to do robot specific init */
    public void init() {

    }

    public abstract DifferentialDrive getDrive();
    public abstract double calcTimeToMoveFeet(double feet, double speed);
    public abstract double calcTimeToRotate(double degrees, double speed);
}
