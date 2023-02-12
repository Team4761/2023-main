package frc.robot.command;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.impl.placeholder.Placeholder;
import frc.robot.main.Varyings;

public class MoveToSpeedCommand extends CommandBase {
    final int kCountsPerRev = 4096;  //Encoder counts per revolution of the motor shaft.
    final double kGearRatio = 1.0;
    final double kWheelRadiusInches = 2;
    final double kTrackWidth = .4953;

    private Distances start;
    private double ticks;

    private PIDController pidLeftFront = new PIDController(Varyings.drivetrainpid.getP(), 0, Varyings.drivetrainpid.getD());
    private PIDController pidRightFront = new PIDController(Varyings.drivetrainpid.getP(), 0, Varyings.drivetrainpid.getD());
    private final SimpleMotorFeedforward m_feedforward = new SimpleMotorFeedforward(1, 3);
    private final DifferentialDriveKinematics m_kinematics = new DifferentialDriveKinematics(kTrackWidth);

    private int count = 0;
    private double speed;

    public MoveToSpeedCommand(double speed) {
        this.speed = 2;
//        pidLeftFront.setTolerance(5, 10);
//        pidRightFront.setTolerance(5, 10);
    }

    @Override
    public void execute() {
        var rightNow = Distances.fromPlaceholder();
        var speeds = m_kinematics.toWheelSpeeds(new ChassisSpeeds(speed, 0.0, 0.0));

        final double leftFeedforward = m_feedforward.calculate(speeds.leftMetersPerSecond);
        final double rightFeedforward = m_feedforward.calculate(speeds.rightMetersPerSecond);

        final double leftSpeed = getMetersPerSecond(Placeholder.front_left);
        final double rightSpeed = getMetersPerSecond(Placeholder.front_left);

        double leftFrontSpeed = pidLeftFront.calculate(leftSpeed, speeds.leftMetersPerSecond);
        double rightFrontSpeed = pidRightFront.calculate(rightSpeed, speeds.rightMetersPerSecond);
        if ((count++ % 10) == 0) {
            System.out.println("count=" + count + " speed=" + leftFrontSpeed + ", " + rightFrontSpeed);
        }
        Placeholder.m_leftMotors.setVoltage(leftFrontSpeed + leftFeedforward);
        Placeholder.m_rightMotors.setVoltage(rightFrontSpeed + rightFeedforward);
    }

    @Override
    public boolean isFinished() {
        if ((count % 10) == 0) {
            System.out.println("count=" + count + " error=" + pidLeftFront.getPositionError());
        }
//        return pidLeftFront.atSetpoint() && pidRightFront.atSetpoint();
        return false;
    }

    public double getMetersPerSecond(WPI_TalonFX front_left) {
        double sensorCounts = front_left.getSelectedSensorPosition();
        double motorRotations = (double)sensorCounts / kCountsPerRev;
        double wheelRotations = motorRotations / kGearRatio;
        double positionMeters = wheelRotations * (2 * Math.PI * Units.inchesToMeters(kWheelRadiusInches));
        return positionMeters;
    }
}
