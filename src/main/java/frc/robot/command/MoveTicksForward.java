package frc.robot.command;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import frc.robot.impl.placeholder.Placeholder;

class Distances {
    public double frontLeft;
    public double frontRight;
    public double backLeft;
    public double backRight;

    public static Distances fromPlaceholder() {
        return new Distances(
                getSensorPosition(Placeholder.front_left),
                getSensorPosition(Placeholder.front_right),
                getSensorPosition(Placeholder.back_left),
                getSensorPosition(Placeholder.back_right)
        );
    }

    private static double getSensorPosition(WPI_TalonFX motor) {
        return motor.getSensorCollection().getIntegratedSensorPosition();
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

public class MoveTicksForward extends MoveCommand {
    private Distances start;
    private double ticks;

    private int count = 0;
    private double speed;

    public MoveTicksForward(double ticks, double speed) {
        super(speed, 0);

        this.ticks = ticks;
        this.speed = 2;
        start = Distances.fromPlaceholder();
    }

    @Override
    public boolean isFinished() {
        var rightNow = Distances.fromPlaceholder();
        Distances delta = getDelta(rightNow);

        var avg = delta.average();
        if (ticks - avg <= 0) {
            System.out.println("Stopping after " + ticks + " " + delta + " " + delta.average());
            return true;
        }
        return false;
    }

    private Distances getDelta(Distances rightNow) {
        var delta = new Distances(
                Math.abs(rightNow.frontLeft - start.frontLeft),
                Math.abs(rightNow.frontRight - start.frontRight),
                Math.abs(rightNow.backLeft - start.backLeft),
                Math.abs(rightNow.backRight - start.frontRight)
        );
        return delta;
    }
}
