package frc.robot.Auto;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Vision.visionVarsAndMethods;
import frc.robot.main.Constants;

import java.util.List;


public class trajectoryFollower extends CommandBase {

    public trajectoryFollower() {
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)
        addRequirements();
    }

    DifferentialDriveVoltageConstraint autoVoltageConstraint;
    TrajectoryConfig config;
    Trajectory trajectory;
    RamseteCommand ramseteCommand;

    @Override
    public void initialize() {
        autoVoltageConstraint = new DifferentialDriveVoltageConstraint(
                new SimpleMotorFeedforward(
                        Constants.ksVolts,
                        Constants.kvVoltSecondsPerMeter,
                        Constants.kaVoltSecondsSquaredPerMeter),
                Constants.kDriveKinematics,
                10);

        config = new TrajectoryConfig(
                        Constants.kMaxSpeedMetersPerSecond,
                        Constants.kMaxAccelerationMetersPerSecondSquared)
                        // Add kinematics to ensure max speed is actually obeyed
                        .setKinematics(Constants.kDriveKinematics)
                        // Apply the voltage constraint
                        .addConstraint(autoVoltageConstraint);

        trajectory = TrajectoryGenerator.generateTrajectory(
                        // Start at the origin facing the +X direction
                        new Pose2d(0, 0, new Rotation2d(0)),
                        // Pass through these two interior waypoints, making an 's' curve path
                        List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
                        // End 3 meters straight ahead of where we started, facing forward
                        new Pose2d(3, 0, new Rotation2d(0)),
                        // Pass config
                        config);

        ramseteCommand =

                new RamseteCommand(

                        trajectory,

                        visionVarsAndMethods.getEstimatedPose(),

                        new RamseteController(Constants.kRamseteB, Constants.kRamseteZeta),

                        new SimpleMotorFeedforward(

                                Constants.ksVolts,

                                Constants.kvVoltSecondsPerMeter,

                                Constants.kaVoltSecondsSquaredPerMeter),

                        Constants.kDriveKinematics,

                        m_robotDrive::getWheelSpeeds,

                        new PIDController(Constants.kPDriveVel, 0, 0),

                        new PIDController(Constants.kPDriveVel, 0, 0),

                        // RamseteCommand passes volts to the callback

                        m_robotDrive::tankDriveVolts,

                        m_robotDrive);
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
