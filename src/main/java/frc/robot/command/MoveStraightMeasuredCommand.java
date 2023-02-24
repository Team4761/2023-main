package frc.robot.command;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.impl.placeholder.Placeholder;
import frc.robot.main.Robot;

public class MoveStraightMeasuredCommand extends CommandBase {
    final int kCountsPerRev = 2048;  //Encoder counts per revolution of the motor shaft.
    final double kGearRatio = 7.5;
    final double kWheelRadiusInches = 2;
    final double linearSpeedIncrease = .01;
    final double kMaxOutput = RobotDriveBase.kDefaultMaxOutput;

    private final Distances start;
    private final double goalSpeed;

    private double speed = 0.0;
    private final double meters;

    private final double startAngle;

    /**
     * @param xSpeed between -1.0 and 1.0
     * @param meters the number of meters to go forward
     */
    public MoveStraightMeasuredCommand(double xSpeed, double meters) {
        start = Robot.impl.getSensorReadings();
        goalSpeed = MathUtil.applyDeadband(xSpeed, RobotDriveBase.kDefaultDeadband);
        this.meters = meters;
        Placeholder.gyro.reset();
        startAngle = Placeholder.gyro.getAngle();
    }

    @Override
    public void execute() {
        // TODO: linear interpolation for speed
        if (speed < goalSpeed) {
            speed += linearSpeedIncrease;
        }
        var speeds = DifferentialDrive.tankDriveIK(speed, speed, true);

        var delta = getDelta();
        var avgDeltaLeftRight = (delta.frontLeft - delta.frontRight) / 2;

        if ((count++ % 10) == 0) {
            System.out.println("delta=" + delta);
            System.out.println("avgDelta=" + avgDeltaLeftRight + " start speeds=" + speeds.left + " speed right=" + speeds.right);
        }

//        final double nudge = .02;
//        if (avgDeltaLeftRight > 0) {
//            speeds.left *= (1.0 - nudge);
//            //speeds.right *= (1.0 + nudge);
//        } else {
//            speeds.left *= (1.0 + nudge);
//            //speeds.right *= (1.0 - nudge);
//        }

//        // the closer the average delta is to zero, the more we want speeds.left to stay the same.
//        // if the average delta is positive, we want left to slow down just a bit, and go to zero if it's a full
//        // revolution behind.
//        speeds.left *= 1 - (avgDeltaLeftRight / kCountsPerRev);
//
//        // the closer the average delta is to zero, the more we want speeds.left to stay the same
//        // if the average delta is positive, we want right to speed up just a bit, and go to 2*speed if it's a full
//        // revolution behind.
//        speeds.right *= 1 + (avgDeltaLeftRight / kCountsPerRev);

        System.out.println("Speed left=" + speeds.left + " speed right=" + speeds.right);

        // TODO: this can be extracted to RobotImpl
        Placeholder.m_leftMotors.set(speeds.left * kMaxOutput);
        Placeholder.m_rightMotors.set(speeds.right * kMaxOutput);
    }

    int count = 0;
    @Override
    public boolean isFinished() {
        Distances delta = getDelta();
        var distance = ticksToMeters(delta.average());
        boolean finished = distance > this.meters;
        if (finished) {
            System.out.println(count + " Finished after " + distance + " meters " + Robot.impl.getSensorReadings());
        }
        return finished;
    }

    private Distances getDelta() {
        var rightNow = Robot.impl.getSensorReadings();
        return new Distances(
            Math.abs(rightNow.frontLeft - start.frontLeft),
            Math.abs(rightNow.frontRight - start.frontRight),
            Math.abs(rightNow.backLeft - start.backLeft),
            Math.abs(rightNow.backRight - start.frontRight)
        );
    }

    private double ticksToMeters(double sensorCounts) {
        double motorRotations = sensorCounts / kCountsPerRev;
        double wheelRotations = motorRotations / kGearRatio;
        return wheelRotations * (2 * Math.PI * Units.inchesToMeters(kWheelRadiusInches));
    }
}
