package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.arm.ArmSubsystem;
import frc.robot.controller.XboxControl;
import frc.robot.main.Robot;

import java.util.Set;

public class ArmControl extends CommandBase {
    protected XboxControl xbox = Robot.xbox;

    public ArmControl() {
        //xbox.getController().b().onTrue(this::onPressB);

        ArmSubsystem armSubsystem = ArmSubsystem.getInstance();
        xbox.getController().b().onTrue(Commands.runOnce(this::onPressB, armSubsystem));
        xbox.getController().y().onTrue(Commands.runOnce(this::onPressY, armSubsystem));
        xbox.getController().x().onTrue(Commands.runOnce(this::onPressX, armSubsystem));
        xbox.getController().a().onTrue(Commands.runOnce(this::onPressA, armSubsystem));
    }

    private void onPressB() {
        movePID(0.7);
    }
    private void onPressA() {
        movePID(-0.2);
    }
    private void onPressX() {
        movePID(0.2);
    }
    private void onPressY() {
        movePID(0.5);
    }

    @Override
    public void execute() {
        //Robot.arms.updatePos(xbox.getRightX(), xbox.getRightY());
        //Robot.arms.movePID();
        //obot.arms.setBottomL(xbox.getRightY()/2);
        //Robot.arms.setBottomR(xbox.getLeftY()/2);
        Robot.arms.setBottom(xbox.getRightY());
        //Robot.arms.setTop(xbox.getLeftY()/2);
        
        // Emergency Stop!


        //xbox.getController().x().onTrue(Commands.runOnce(() -> { zeroEncoders(); }));
        // Debugging purposes only
        Robot.arms.debug();
    }

    public void movePID(double setRotation) {
        ArmSubsystem.setDesiredTopRotation(setRotation);
        Robot.arms.movePID();
    }
    public void emergencyStop() {
        Robot.arms.emergencyStop();
    }

    public void zeroEncoders() {
        Robot.arms.zeroEncoders();
    }
}
