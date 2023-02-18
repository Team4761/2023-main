package frc.robot.Drivetrain;


import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.impl.placeholder.Placeholder;
import frc.robot.main.Robot;

public class DrivetrainSubsystem extends SubsystemBase {
    private final double WHEEL_DIAMETER_METERS = Units.inchesToMeters(2);
    private final double TICKS_PER_ROTATION = 2048;
    private final double GEAR_RATIO = 10.90909090; // 120 / 11
    private final double TICKS_PER_METER = (double)(TICKS_PER_ROTATION * GEAR_RATIO) * (double)(1 / (WHEEL_DIAMETER_METERS * Math.PI)); //46644.183
    private final double METERS_PER_TICK = 1 / TICKS_PER_METER; //2.149e-5

    // With eager singleton initialization, any static variables/fields used in the
    // constructor must appear before the "INSTANCE" variable so that they are initialized
    // before the constructor is called when the "INSTANCE" variable initializes.

    /**
     * The Singleton instance of this DrivetrainSubsystem. Code should use
     * the {@link #getInstance()} method to get the single instance (rather
     * than trying to construct an instance of this class.)
     */
    private final static DrivetrainSubsystem INSTANCE = new DrivetrainSubsystem();

    public final static Placeholder test = new Placeholder();
    private final DifferentialDriveOdometry m_odometry;


    /**
     * Returns the Singleton instance of this DrivetrainSubsystem. This static method
     * should be used, rather than the constructor, to get the single instance
     * of this class. For example: {@code DrivetrainSubsystem.getInstance();}
     */
    @SuppressWarnings("WeakerAccess")
    public static DrivetrainSubsystem getInstance() {
        return INSTANCE;
    }

    /**
     * Creates a new instance of this DrivetrainSubsystem. This constructor
     * is private since this class is a Singleton. Code should use
     * the {@link #getInstance()} method to get the singleton instance.
     */
    private DrivetrainSubsystem() {
        m_odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(0), 0, 0);

    }

    @Override
    public void periodic() {
        super.periodic();
        m_odometry.update(
                Rotation2d.fromDegrees(-getAngle()),
                -Placeholder.front_left.getSelectedSensorPosition() * METERS_PER_TICK,
                Placeholder.front_right.getSelectedSensorPosition() * METERS_PER_TICK);
    }

    public void resetOdometry() {
        resetAngle();
        Placeholder.front_left.setSelectedSensorPosition(0);
        Placeholder.front_right.setSelectedSensorPosition(0);
        m_odometry.resetPosition(Rotation2d.fromDegrees(0), 0, 0, new Pose2d());
    }

    private void resetAngle() {
        Placeholder.gyro.reset();
    }

    private Double getAngle() {
        return Placeholder.gyro.getAngle();
    }

    public void arcadeDrive(double speed, double rotation){
        Robot.impl.getDrive().arcadeDrive(speed, rotation);
    }
    public void tankDrive(double rightSpeed, double leftSpeed){
        Robot.impl.getDrive().tankDrive(rightSpeed, leftSpeed);
    }
}

