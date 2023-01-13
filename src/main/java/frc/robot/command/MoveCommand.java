package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.MainRobotStuff.Robot;

public class MoveCommand extends CommandBase {
    private final double speed;
    private final double rotation;

    public MoveCommand(double speed, double rotation) {
        this.speed = speed;
        this.rotation = rotation;
    }

    @Override
    public void execute() {
        Robot.impl.getDrive().arcadeDrive(speed, rotation);
    }
}
