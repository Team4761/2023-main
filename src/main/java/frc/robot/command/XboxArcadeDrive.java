package frc.robot.command;

import frc.robot.Drivetrain.DrivetrainSubsystem;
import frc.robot.main.Robot;
public class XboxArcadeDrive extends XboxDriveBase {

    DrivetrainSubsystem drivetrainSubsystem = DrivetrainSubsystem.getInstance();

    @Override
    public void initialize(){


    }
    @Override
    public void execute() {
        drivetrainSubsystem.arcadeDrive(xbox.getLeftY() / 2, xbox.getLeftX() / 2);
    }

    @Override
    public void end(boolean interrupted) {
        //Robot.driveTrain.arcadeDrive(0,0);
    }
}
