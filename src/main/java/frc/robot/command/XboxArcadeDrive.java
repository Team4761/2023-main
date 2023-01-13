package frc.robot.command;

import frc.robot.main.Robot;

public class XboxArcadeDrive extends XboxDriveBase {
    @Override
    public void execute() {
        Robot.impl.getDrive().arcadeDrive(xbox.getLeftX(), xbox.getRightX());
    }

    @Override
    public void end(boolean interrupted) {
        Robot.impl.getDrive().arcadeDrive(0,0);
    }
}
