package frc.robot.Auto.EncoderAuto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.impl.placeholder.Placeholder;
import frc.robot.main.Constants;



public class TurnToGyro extends CommandBase {
    private double setAngle;

    private double leftSpeed;
    private double rightSpeed;
    private double targetVelocity;

    private Timer timer = new Timer();
    private double lastTime;
    private double output = 0;

    // degrees relative to start
    public TurnToGyro(double angle) {
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)\
        setAngle = angle;

        addRequirements();
    }

    @Override
    public void initialize() {
        timer.start();
        lastTime = timer.get();
    }

    @Override
    public void execute() {
        double maxChange = (timer.get()-lastTime) * Constants.DRIVETRAIN_MAX_ACCELERATION;
        lastTime = timer.get();
        targetVelocity = Math.min((setAngle-Placeholder.m_gyro.getAngle())/180*Constants.trackWidth/Constants.wheelRadius, Constants.DRIVETRAIN_MAX_VELOCITY);

        output = Math.max(Math.min(targetVelocity, output+maxChange), output-maxChange);


        leftSpeed = - (2.5 * output +  0.2 * (output - Placeholder.getLeftVelocity()*Constants.distancePerEncoderTick));
        rightSpeed = 2.5 * output +  0.2 * (output - Placeholder.getRightVelocity()*Constants.distancePerEncoderTick);
        
        // maybe not needed
        leftSpeed += Math.signum(leftSpeed)*0.65;
        rightSpeed += Math.signum(rightSpeed)*0.6;

        Placeholder.setVoltages(Math.max(-12, Math.min(12, leftSpeed)), Math.max(-12, Math.min(12, rightSpeed)));
        
    }

    @Override
    public boolean isFinished() {
        return Math.abs(targetVelocity)<=0.1;
    }

    @Override
    public void end(boolean interrupted) {
        Placeholder.setVoltages(0, 0);
    }
}
