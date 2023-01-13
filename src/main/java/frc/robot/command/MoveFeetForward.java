package frc.robot.command;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.main.Robot;

public class MoveFeetForward extends SequentialCommandGroup {
    private static final double ratio = 0.6;

    public MoveFeetForward(double speed, double feet) {
        super(
            new MoveCommand(speed, 0).withTimeout(computeTime(speed, feet))
        );
    }

    public static double computeTime(double speed, double feet) {
        return Robot.impl.calcTimeToMoveFeet(feet, speed);
    }
}
