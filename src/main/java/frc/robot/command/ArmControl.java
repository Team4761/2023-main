package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.controller.XboxControl;
import frc.robot.main.Robot;

public class ArmControl extends CommandBase {
    protected XboxControl xbox = Robot.xbox;

    @Override
    public void execute() {
        //Robot.arms.updatePos(xbox.getRightX(), xbox.getRightY());
        Robot.arms.move();
        //obot.arms.setBottomL(xbox.getRightY()/2);
        //Robot.arms.setBottomR(xbox.getLeftY()/2);
        Robot.arms.setBottom(xbox.getRightY());
        //Robot.arms.setTop(xbox.getLeftY()/2);
        
        // Emergency Stop!
        xbox.getController().a().onTrue(Commands.runOnce(() -> { emergencyStop(); }));
        xbox.getController().x().onTrue(Commands.runOnce(() -> { zeroEncoders(); }));
        // Debugging purposes only
        Robot.arms.debug();
    }

    public void emergencyStop() {
        Robot.arms.emergencyStop();
    }

    public void zeroEncoders() {
        Robot.arms.zeroEncoders();
    }
}
