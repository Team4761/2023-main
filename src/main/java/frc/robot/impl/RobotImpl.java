package frc.robot.impl;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.command.Distances;
import frc.robot.command.Distances;

public abstract class RobotImpl {

    public abstract DifferentialDrive getDrive();
    public abstract double calcTimeToMoveFeet(double feet, double speed);
    public abstract double calcTimeToRotate(double degrees, double speed);

    public Distances getSensorReadings() {
        return new Distances(0, 0, 0, 0);
    }

    public abstract void drive(double xSpeed, double rot);
}
