package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.controller.XboxControl;

public abstract class XboxDriveBase extends CommandBase {
    protected XboxControl xbox = new XboxControl(0);

    @Override
    public abstract void execute();
}
