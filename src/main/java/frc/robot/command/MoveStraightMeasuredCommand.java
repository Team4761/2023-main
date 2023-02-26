package frc.robot.command;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.impl.placeholder.Placeholder;
import frc.robot.main.Constants;
import frc.robot.main.Robot;

public class MoveStraightMeasuredCommand extends CommandBase {
    final int kCountsPerRev = 2048;  //Encoder counts per revolution of the motor shaft.
    final double linearSpeedIncrease = .01;

    private final Distances start;
    private final double goalSpeed;

    private final double meters;
    private final double drivetrainGearRatio;

    private final double startAngle;

    /**
     * @param xSpeed between -1.0 and 1.0
     * @param meters the number of meters to go forward
     */
    public MoveStraightMeasuredCommand(double xSpeed, double meters) {
        start = Robot.impl.getSensorReadings();
        goalSpeed = MathUtil.applyDeadband(xSpeed, RobotDriveBase.kDefaultDeadband);
        SmartDashboard.putNumber("GoalSpeed", goalSpeed);
        this.meters = meters;
        Placeholder.m_gyro.reset();
        startAngle = Placeholder.m_gyro.getAngle();
        drivetrainGearRatio = SmartDashboard.getNumber("Drivetrain Gear Ratio", 7.3);
    }

    @Override
    public void execute() {
        var speeds = DifferentialDrive.tankDriveIK(goalSpeed, goalSpeed, true);
        SmartDashboard.putNumber("Speed Left", speeds.left);
        SmartDashboard.putNumber("Speed Right", speeds.right);


        var delta = getDelta();
        var avgDeltaLeftRight = (delta.frontLeft - delta.frontRight) / 2;

        if ((count++ % 10) == 0) {
            System.out.println("delta=" + delta);
            System.out.println("avgDelta=" + avgDeltaLeftRight + " start speeds=" + speeds.left + " speed right=" + speeds.right);
        }

        // TODO: adjustments for driving straight
        System.out.println("Speed left=" + speeds.left + " speed right=" + speeds.right);

        Placeholder.m_leftMotors.set(speeds.left * RobotDriveBase.kDefaultMaxOutput);
        Placeholder.m_rightMotors.set(speeds.right * RobotDriveBase.kDefaultMaxOutput);
    }

    int count = 0;
    @Override
    public boolean isFinished() {
        Distances delta = getDelta();
        var distance = Math.abs(ticksToMeters(delta.average()));
        boolean finished = distance > this.meters;
        SmartDashboard.putBoolean("Finished", finished);
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
        double wheelRotations = motorRotations / drivetrainGearRatio;
        return wheelRotations * (2 * Math.PI * Units.inchesToMeters(Constants.wheelRadiusInches));
    }
}
