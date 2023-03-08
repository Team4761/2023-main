package frc.robot.command;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.impl.RobotImpl;
import frc.robot.main.Robot;

import java.util.Arrays;

public class TrajectoryCommand extends CommandBase {
    // These are numbers that were obtained using the robot characterization toolsuite
    // TODO: Find these numbers using the robot characterization toolsuite!
    public static final double ksVolts = 0.22;
    public static final double kvVoltsSecondsPerMeter = 1.98;
    public static final double kaVoltSecondsSquaredPerMeter = 0.2;
    public static final double kMaxVolts = 11;

    public static final double kPDriveVel = 8.5;

    public static final double kMaxSpeedMetersPerSecond = 3.0;
    public static final double kMaxAccelerationMetersPerSecondSquared = 3.0;

    //RAMSETE Follower variables Keep these numbers! They work good!
    public static final double kRamseteB = 2.0;
    public static final double kRamseteZeta = 0.7;

    private Trajectory trajectory;
    private RamseteCommand ramseteCommand;
    private DifferentialDriveKinematics kinematics;

    public TrajectoryCommand(Pose2d start, Pose2d end, Translation2d...waypoints) {
        kinematics = new DifferentialDriveKinematics(impl().getTrackWidth());
        DifferentialDriveOdometry odometry = Robot.odometry;

        SimpleMotorFeedforward motorfeed = new SimpleMotorFeedforward(
            ksVolts,
            kvVoltsSecondsPerMeter,
            kaVoltSecondsSquaredPerMeter
        );

        DifferentialDriveVoltageConstraint voltageConstraint =
            new DifferentialDriveVoltageConstraint(motorfeed, kinematics, kMaxVolts);

        TrajectoryConfig config =
            new TrajectoryConfig(
                kMaxSpeedMetersPerSecond,
                kMaxAccelerationMetersPerSecondSquared
            )
            .setKinematics(kinematics)
            .addConstraint(voltageConstraint);

        trajectory = TrajectoryGenerator.generateTrajectory(
            start,
            Arrays.asList(waypoints),
            end,
            config
        );

        ramseteCommand = new RamseteCommand(
            trajectory,
            odometry::getPoseMeters,
            new RamseteController(kRamseteB, kRamseteZeta),
            motorfeed,
            kinematics,
            this::getWheelSpeeds,
            new PIDController(kPDriveVel,0,0),
            new PIDController(kPDriveVel, 0,0),
            this::setTankDriveVolts
        );
    }

    private DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return impl().getWheelSpeeds();
    }

    private void setTankDriveVolts(double l, double r) {
        impl().setVoltages(l, r);
    }

    @Override
    public void execute() {
        ramseteCommand.execute();
    }

    private RobotImpl impl() {
        return Robot.impl;
    }
}
