package frc.robot.command;

import frc.robot.main.Robot;

public class Distances {
    public double frontLeft;
    public double frontRight;
    public double backLeft;
    public double backRight;

    public static Distances fromPlaceholder() {
        return Robot.impl.getSensorReadings();
    }
    
    public Distances(double frontLeft, double frontRight, double backLeft, double backRight) {
        this.frontLeft = frontLeft;
        this.frontRight = frontRight;
        this.backLeft = backLeft;
        this.backRight = backRight;
    }

    public double average() {
        return (frontLeft + frontRight + backLeft + backRight) / 4;
    }

    @Override
    public String toString() {
        return "fl=" + frontLeft + " fr=" + frontRight + " bl=" + backLeft + " br=" + backRight;
    }
}
