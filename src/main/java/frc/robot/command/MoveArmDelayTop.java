package frc.robot.command;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.arm.ArmSubsystem;
import edu.wpi.first.math.geometry.Translation2d;

// point, delay in seconds, bottom goes first
public class MoveArmDelayTop extends SequentialCommandGroup {
    public MoveArmDelayTop (Translation2d pt, double delay) {
        addCommands(
            new MoveArmAngles(new Translation2d((pt.getX()+3*ArmSubsystem.getInstance().getTopRotation())/4, pt.getY())).withTimeout(delay),
            new MoveArmAngles(pt)
        );
    }
}
