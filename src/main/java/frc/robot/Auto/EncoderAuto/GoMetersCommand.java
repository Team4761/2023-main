package frc.robot.Auto.EncoderAuto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Drivetrain.DrivetrainSubsystem;
import frc.robot.impl.Paligator.Paligator;
import frc.robot.main.Constants;

enum Direction {FORWARD, BACK}

public class GoMetersCommand extends CommandBase {
    private final double distance;
    private final Direction dir;

    private double leftSpeed;
    private double rightSpeed;
    private double targetVelocity;

    private final Timer timer = new Timer();
    private double lastTime;
    private double output = 0;

    private DifferentialDriveOdometry odometry;
    private Pose2d pose;

    public GoMetersCommand(double distance, Direction dir) {
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        this.distance = Math.abs(distance);
        this.dir = dir;

        addRequirements(DrivetrainSubsystem.getInstance());
    }

    @Override
    public void initialize() {
        odometry = new DifferentialDriveOdometry(
            new Rotation2d(
                Units.degreesToRadians(Paligator.m_gyro.getAngle())),
            Paligator.frontLeftPosition() * Constants.distancePerEncoderTick,
            Paligator.frontRightPosition() * Constants.distancePerEncoderTick,
            new Pose2d()
        );
        pose = odometry.getPoseMeters();

        timer.start();
        lastTime = timer.get();
    }

    @Override
    public void execute() {
        pose = odometry.update(
            new Rotation2d(
                Units.degreesToRadians(Paligator.m_gyro.getAngle())),
            Paligator.frontLeftPosition() * Constants.distancePerEncoderTick,
            Paligator.frontRightPosition() * Constants.distancePerEncoderTick
        );
        SmartDashboard.putNumber(dir == Direction.FORWARD ? "forward command" : "backward command", pose.getX());

        targetVelocity =
            Math.max(Math.min((distance - pose.getX()) * 1.5, Constants.DRIVETRAIN_MAX_VELOCITY), -Constants.DRIVETRAIN_MAX_VELOCITY);
        if (dir == Direction.BACK) {
            targetVelocity *= -1;
        }

        double maxChange = (timer.get() - lastTime) * Constants.DRIVETRAIN_MAX_ACCELERATION;
        lastTime = timer.get();

        output = Math.max(Math.min(targetVelocity, output + maxChange), output - maxChange);

        leftSpeed = 4 * output + 0.2 * (output - Paligator.getLeftVelocity() * Constants.distancePerEncoderTick);
        rightSpeed = 4 * output + 0.2 * (output - Paligator.getRightVelocity() * Constants.distancePerEncoderTick);

        // maybe not needed
        leftSpeed += Math.signum(leftSpeed) * 0.33;
        rightSpeed += Math.signum(rightSpeed) * 0.25;

        Paligator.setVoltages(Math.max(-12, Math.min(12, leftSpeed)), Math.max(-12, Math.min(12, rightSpeed)));
    }

    @Override
    public boolean isFinished() {
        return Math.abs(targetVelocity)<=0.1;
    }

    @Override
    public void end(boolean interrupted) {
        Paligator.setVoltages(0, 0);
    }
}
