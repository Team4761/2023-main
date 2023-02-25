package frc.robot.command;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Auto.PurePursuit.PathFollower;
import frc.robot.Auto.PurePursuit.PathoGen;
import frc.robot.impl.placeholder.Placeholder;
import frc.robot.main.Constants;

public class MoveMetersStraightCommand extends CommandBase {
    private Pose2d pose;
    private final DifferentialDriveOdometry odometry;
    private double meters;
    public PathoGen path;
    public PathFollower follower;
    public Timer timer = new Timer();


    private int count = 0;
    public MoveMetersStraightCommand(double meters) {
        this.meters = meters;
        double[][] pathPoints = {{0, 0}, {meters, 0}};

        Placeholder.zeroEncoders();

        path = new PathoGen(pathPoints);
        follower = new PathFollower(path.getPoints(), path.getTargetVelocities());

        for (double[] i : path.getPoints()) {
            System.out.println(i[0] + ", " + i[1]);
        }

        odometry = new DifferentialDriveOdometry(
                new Rotation2d(Units.degreesToRadians(Placeholder.m_gyro.getAngle())),
                Placeholder.frontLeftPosition() * Constants.distancePerEncoderTick,
                Placeholder.frontRightPosition() * Constants.distancePerEncoderTick,
                new Pose2d(0, 0, new Rotation2d()));
        pose = odometry.getPoseMeters();
    }

    @Override
    public void execute() {
        SmartDashboard.putNumber("odometry x", pose.getX());
        SmartDashboard.putNumber("odometry y", pose.getY());

        pose = odometry.update(
            new Rotation2d(Units.degreesToRadians(Placeholder.m_gyro.getAngle())),
            Placeholder.frontLeftPosition() * Constants.distancePerEncoderTick,
            Placeholder.frontRightPosition() * Constants.distancePerEncoderTick);
        double[] voltages = follower.calculate(pose,
                Placeholder.getLeftVelocity() * Constants.distancePerEncoderTick,
                Placeholder.getRightVelocity() * Constants.distancePerEncoderTick, timer.get());

        SmartDashboard.putNumber("volts left", voltages[0]);
        SmartDashboard.putNumber("volts right", voltages[1]);
        System.out.println("Move meters left=" + voltages[0] + " right=" + voltages[1] + " pose=" + pose);

        Placeholder.setVoltages(Math.max(-12, Math.min(12, voltages[0])), Math.max(-12, Math.min(12, voltages[1])));
    }

    @Override
    public boolean isFinished() {
        return count++ > 50;
    }
}
