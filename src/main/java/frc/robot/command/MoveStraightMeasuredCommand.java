package frc.robot.command;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.impl.placeholder.Placeholder;
import frc.robot.main.Robot;

public class MoveStraightMeasuredCommand extends CommandBase {
    final int kCountsPerRev = 4096;  //Encoder counts per revolution of the motor shaft.
    final double kGearRatio = 1.0;
    final double kWheelRadiusInches = 2;
    final double kMaxOutput = RobotDriveBase.kDefaultMaxOutput;

    private final Distances start;
    private final double speed;
    private final double meters;

    /**
     * @param xSpeed between -1.0 and 1.0
     * @param meters the number of meters to go forward
     */
    public MoveStraightMeasuredCommand(double xSpeed, double meters) {
        start = Robot.impl.getSensorReadings();
        speed = MathUtil.applyDeadband(xSpeed, RobotDriveBase.kDefaultDeadband);
        this.meters = meters;
    }

    @Override
    public void execute() {
        var speeds = DifferentialDrive.tankDriveIK(speed, speed, true);

        var delta = getDelta();
        var avgDeltaLeftRight = (delta.frontLeft - delta.frontRight) / 2;

        // the closer the average delta is to zero, the more we want speeds.left to stay the same.
        // if the average delta is positive, we want left to slow down just a bit, and go to zero if it's a full
        // revolution behind.
        speeds.left *= 1 - (avgDeltaLeftRight / 4096);

        // the closer the average delta is to zero, the more we want speeds.left to stay the same
        // if the average delta is positive, we want right to speed up just a bit, and go to 2*speed if it's a full
        // revolution behind.
        speeds.right *= 1 + (avgDeltaLeftRight / 4096);

        // TODO: this can be extracted to RobotImpl
        Placeholder.m_leftMotors.set(speeds.left * kMaxOutput);
        Placeholder.m_rightMotors.set(speeds.right * kMaxOutput);
    }

    @Override
    public boolean isFinished() {
        Distances delta = getDelta();
        var distance = ticksToMeters(delta.average());
        return distance > this.meters;
    }

    private Distances getDelta() {
        var rightNow = Robot.impl.getSensorReadings();
        var delta = new Distances(
            Math.abs(rightNow.frontLeft - start.frontLeft),
            Math.abs(rightNow.frontRight - start.frontRight),
            Math.abs(rightNow.backLeft - start.backLeft),
            Math.abs(rightNow.backRight - start.frontRight)
        );
        return delta;
    }

    private double ticksToMeters(double sensorCounts) {
        double motorRotations = sensorCounts / kCountsPerRev;
        double wheelRotations = motorRotations / kGearRatio;
        return wheelRotations * (2 * Math.PI * Units.inchesToMeters(kWheelRadiusInches));
    }
}
