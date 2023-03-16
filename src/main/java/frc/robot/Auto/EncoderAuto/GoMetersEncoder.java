package frc.robot.Auto.EncoderAuto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.impl.Paligator.Paligator;
import frc.robot.main.Constants;



public class GoMetersEncoder extends CommandBase {
    private double distance;
    private double backwardsFactor = 0;
    private double backwards = 1;

    private double leftSpeed;
    private double rightSpeed;
    private double targetVelocity;

    private Timer timer = new Timer();
    private double lastTime;
    private double output = 0;

    private DifferentialDriveOdometry odometry;
    private Pose2d pose;

    // can't negative yet
    public GoMetersEncoder(double distance) {
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)\
        if(Math.signum(distance) < 0) {
            backwardsFactor = Math.PI;
            backwards = -1;
            System.out.println("going back");
        }
        this.distance = Math.abs(distance);

        odometry = new DifferentialDriveOdometry(new Rotation2d(Paligator.m_gyro.getAngle()*0.0174533+backwardsFactor), Paligator.frontLeftPosition()*Constants.distancePerEncoderTick, Paligator.frontRightPosition()*Constants.distancePerEncoderTick);
        pose = odometry.getPoseMeters();


        addRequirements();
    }

    @Override
    public void initialize() {
        timer.start();
        lastTime = timer.get();
    }

    @Override
    public void execute() {
        pose = odometry.update(new Rotation2d(Paligator.m_gyro.getAngle()*0.0174533+backwardsFactor), Paligator.frontLeftPosition()*Constants.distancePerEncoderTick, Paligator.frontRightPosition()*Constants.distancePerEncoderTick);
        SmartDashboard.putNumber("forward command", pose.getX());
        targetVelocity = Math.max(Math.min((distance-pose.getX())*1.5, Constants.DRIVETRAIN_MAX_VELOCITY), -Constants.DRIVETRAIN_MAX_VELOCITY);

        double maxChange = (timer.get()-lastTime) * Constants.DRIVETRAIN_MAX_ACCELERATION;
        lastTime = timer.get();

        output = Math.max(Math.min(targetVelocity, output+maxChange), output-maxChange);


        leftSpeed =  2.5 * output +  0.2 * (output - Paligator.getLeftVelocity()*Constants.distancePerEncoderTick)*backwards;
        rightSpeed = 2.5 * output +  0.2 * (output - Paligator.getRightVelocity()*Constants.distancePerEncoderTick)*backwards;
        
        // maybe not needed
        leftSpeed += Math.signum(leftSpeed)*0.35;
        rightSpeed += Math.signum(rightSpeed)*0.25;

        Paligator.setVoltages(Math.max(-12, Math.min(12, leftSpeed)), Math.max(-12, Math.min(12, rightSpeed)));
        
    }

    @Override
    public boolean isFinished() {
        return targetVelocity<=0;
    }

    @Override
    public void end(boolean interrupted) {
        Paligator.setVoltages(0, 0);
    }
}
