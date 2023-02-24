package frc.robot.Auto.tagAuto.command;

import frc.robot.command.MoveCommand;

public class RotateCommand extends MoveCommand {
    public RotateCommand(double radians) {
        super(0, (radians / Math.PI) * 90.0);
    }
}
