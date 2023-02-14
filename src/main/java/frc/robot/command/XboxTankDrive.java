package frc.robot.command;

import frc.robot.main.Robot;

public class XboxTankDrive extends XboxDriveBase {

    @Override
    public void execute() {
        //Robot.driveTrain.tankDrive(xbox.getLeftY(), xbox.getRightY());
    }

    @Override
    public void end(boolean interrupted) {
        //Robot.driveTrain.tankDrive(0, 0);
    }

}
