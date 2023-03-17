package frc.robot.command;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.impl.Paligator.Paligator;
import frc.robot.main.Constants;
import frc.robot.main.Robot;

public class MoveStraightMeasuredCommand extends CommandBase {
    final int kCountsPerRev = 2048;  //Encoder counts per revolution of the motor shaft.

    private Distances start;
    private final double goalSpeed;

    private final double meters;
    private final double drivetrainGearRatio;

    /**
     * @param xSpeed between -1.0 and 1.0
     * @param meters the number of meters to go forward
     */
    public MoveStraightMeasuredCommand(double xSpeed, double meters) {
        goalSpeed = MathUtil.applyDeadband(xSpeed, RobotDriveBase.kDefaultDeadband);
        SmartDashboard.putNumber("GoalSpeed", goalSpeed);
        this.meters = meters;
        drivetrainGearRatio = SmartDashboard.getNumber("Drivetrain Gear Ratio", 7.3);
    }

    @Override
    public void initialize() {
        super.initialize();
        start = Robot.impl.getSensorReadings();
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

        Paligator.m_leftMotors.set(speeds.left * RobotDriveBase.kDefaultMaxOutput);
        Paligator.m_rightMotors.set(speeds.right * RobotDriveBase.kDefaultMaxOutput);
    }

    int count = 0;
    @Override
    public boolean isFinished() {
        Distances delta = getDelta();
        var distance = Math.abs(ticksToMeters(delta.average()));

        boolean finished = distance > this.meters;
        SmartDashboard.putNumber("Auto distance", distance);
        SmartDashboard.putBoolean("Auto finished", finished);
        if (finished) {
            System.out.println(count + " Finished after " + distance + " meters " + Robot.impl.getSensorReadings());
        }
        return finished;
    }

    @Override
    public void end(boolean interrupted) {
        Paligator.m_leftMotors.set(0);
        Paligator.m_rightMotors.set(0);
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
