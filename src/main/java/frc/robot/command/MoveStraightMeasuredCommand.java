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
    final double kGearRatio = 3.1;
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

        double kP = 1.0;

        // Setpoint is implicitly 0, since we don't want the heading to change
        Gyro gyro = Placeholder.gyro;
        double error = -gyro.getRate();

        // Drives forward continuously at half speed, using the gyro to stabilize the heading
        Robot.impl.getDrive().tankDrive(speed + kP * error, speed - kP * error);
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
