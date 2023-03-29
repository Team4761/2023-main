package frc.robot.command;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.arm.ArmSubsystem;
import edu.wpi.first.math.geometry.Translation2d;

// point, delay in seconds, top goes first
public class MoveArmDelayBottom extends SequentialCommandGroup {
    public MoveArmDelayBottom (Translation2d pt, double delay) {
        addCommands(
            new MoveArmAngles(new Translation2d(pt.getX(), (pt.getY()+3*ArmSubsystem.getInstance().getBottomRotation())/4)).withTimeout(delay),
            new MoveArmAngles(pt)
        );
    }
    // 0-1 for weight, is percentage that the setpoint for delayed part gets during delay, 1 should not move one joint
    // default weight is 0.75
    public MoveArmDelayBottom (Translation2d pt, double delay, double weight) {
        addCommands(
            new MoveArmAngles(new Translation2d(pt.getX(), ((1-weight)*pt.getY()+weight*ArmSubsystem.getInstance().getBottomRotation()))).withTimeout(delay),
            new MoveArmAngles(pt)
        );
    }
}
