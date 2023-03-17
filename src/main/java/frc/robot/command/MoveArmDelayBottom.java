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
}
