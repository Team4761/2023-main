package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.controller.XboxControl;
import frc.robot.main.Robot;

public abstract class XboxDriveBase extends CommandBase {
    protected XboxControl xbox = Robot.xbox;

    @Override
    public abstract void execute();
}
