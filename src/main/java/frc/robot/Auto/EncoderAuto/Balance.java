package frc.robot.Auto.EncoderAuto;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.impl.Paligator.Paligator;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import frc.robot.main.Constants;

public class Balance extends CommandBase {
    private DifferentialDriveOdometry odometry;
    private Pose2d pose;

    public Balance() {
        //addRequirements
    }
    @Override
    public void initialize() {
        odometry = new DifferentialDriveOdometry(new Rotation2d(Paligator.m_gyro.getAngle()*0.0174533), Paligator.frontLeftPosition()*Constants.distancePerEncoderTick, Paligator.frontRightPosition()*Constants.distancePerEncoderTick);
        pose = odometry.getPoseMeters();
    }

    @Override
    public void execute() {
        if(pose.getX()<0.1) Paligator.setVoltages(3.2, 3);
        else if (pose.getX()>0.1) Paligator.setVoltages(-1.95, -1.8);
        else Paligator.setVoltages(0, 0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    public void end(boolean interrupted) {
        Paligator.setVoltages(0, 0);
    }
}
