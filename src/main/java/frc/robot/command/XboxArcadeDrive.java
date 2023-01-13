package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.MainRobotStuff.Robot;
import frc.robot.controller.XboxControl;

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
