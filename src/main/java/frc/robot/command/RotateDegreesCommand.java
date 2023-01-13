package frc.robot.command;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.MainRobotStuff.Robot;

public class RotateDegreesCommand extends SequentialCommandGroup {
    private static final double ratio = 0.9;

    public RotateDegreesCommand(double speed, double degrees) {
        super(
            new MoveCommand(0, speed).withTimeout(computeTime(speed, degrees))
        );
    }

    public static double computeTime(double speed, double degrees) {
        return Robot.impl.calcTimeToRotate(degrees, speed);
    }
}
