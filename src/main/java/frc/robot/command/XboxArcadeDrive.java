package frc.robot.command;

import frc.robot.main.Robot;

public class XboxArcadeDrive extends XboxDriveBase {
    @Override
    public void execute() {
        Robot.driveTrain.arcadeDrive(xbox.getRightX(), xbox.getLeftY());
    }

    @Override
    public void end(boolean interrupted) {
        Robot.driveTrain.arcadeDrive(0,0);
    }
}
