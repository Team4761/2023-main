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
     * @param xSpeed in meters / second
     * @param meters
     */
    public MoveStraightMeasuredCommand(double xSpeed, double meters) {
        start = Robot.impl.getSensorReadings();
        speed = xSpeed;
        this.meters = meters;
    }

    @Override
    public void execute() {
        var m_deadband = RobotDriveBase.kDefaultDeadband;
        var xSpeed = MathUtil.applyDeadband(speed, m_deadband);

        var speeds = DifferentialDrive.curvatureDriveIK(xSpeed, 0.0, true);

//        Robot.impl.getDrive().getLeft().set(speeds.left * kMaxOutput);
//        Robot.impl.getDrive().getRight().set(speeds.right * kMaxOutput);
        Placeholder.m_leftMotors.set(speeds.left * kMaxOutput);
        Placeholder.m_rightMotors.set(speeds.right * kMaxOutput);
    }

    @Override
    public boolean isFinished() {
        var rightNow = Robot.impl.getSensorReadings();
        var delta = new Distances(
            Math.abs(rightNow.frontLeft - start.frontLeft),
            Math.abs(rightNow.frontRight - start.frontRight),
            Math.abs(rightNow.backLeft - start.backLeft),
            Math.abs(rightNow.backRight - start.frontRight)
        );
        var distance = ticksToMeters(delta.average());
        return distance > this.meters;
    }

    private double ticksToMeters(double sensorCounts) {
        double motorRotations = sensorCounts / kCountsPerRev;
        double wheelRotations = motorRotations / kGearRatio;
        return wheelRotations * (2 * Math.PI * Units.inchesToMeters(kWheelRadiusInches));
    }
}
