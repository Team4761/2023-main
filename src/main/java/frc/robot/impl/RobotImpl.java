package frc.robot.impl;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.command.Distances;
import frc.robot.impl.Paligator.Paligator;

public abstract class RobotImpl {

    private Pose2d pose;

    public abstract double calcTimeToMoveFeet(double feet, double speed);
    public abstract double calcTimeToRotate(double degrees, double speed);

    public Distances getSensorReadings() {
        return new Distances(0, 0, 0, 0);
    }


    public abstract void setVoltages(double left, double right);

    public abstract void drive(double xSpeed, double rot);

    public int getLength() {
        return 12;
    }

    public void setPose(Pose2d pose) {
        this.pose = pose;
    }

    public Pose2d getPose() {
        return this.pose;
    }

    public abstract DifferentialDriveWheelSpeeds getWheelSpeeds();

    public abstract double getTrackWidth();
}
