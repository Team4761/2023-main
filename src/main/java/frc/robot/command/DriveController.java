package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Drivetrain.DrivetrainSubsystem;
import frc.robot.controller.XboxControl;
import frc.robot.main.Robot;

public class DriveController extends CommandBase {
    private XboxControl xbox;
    private boolean isArcade = true;
    DrivetrainSubsystem drivetrainSubsystem = DrivetrainSubsystem.getInstance();

    public DriveController(int port) {
        xbox = new XboxControl(port);
        xbox.a().onTrue(Commands.runOnce(this::onPressA, drivetrainSubsystem));
        xbox.b().onTrue(Commands.runOnce(this::onPressB, drivetrainSubsystem));
        xbox.x().onTrue(Commands.runOnce(this::onPressX, drivetrainSubsystem));
        xbox.y().onTrue(Commands.runOnce(this::onPressY, drivetrainSubsystem));
        xbox.leftTrigger().onTrue(Commands.runOnce(this::onLeftTrigger, drivetrainSubsystem));
        xbox.rightTrigger().onTrue(Commands.runOnce(this::onRightTrigger, drivetrainSubsystem));
        xbox.leftBumper().onTrue(Commands.runOnce(this::onLeftBumper, drivetrainSubsystem));
        xbox.rightBumper().onTrue(Commands.runOnce(this::onRightBumper, drivetrainSubsystem));
    }


    private void onPressA() {
    }

    private void onPressB() {
    }

    private void onPressX() {

    }

    private void onPressY() {

    }

    private void onLeftTrigger() {
        isArcade = false;
    }

    private void onRightTrigger() {
        isArcade = true;
    }

    private void onLeftBumper() {

    }

    private void onRightBumper() {

    }

    @Override
    public void execute() {
        if (isArcade) {
            drivetrainSubsystem.arcadeDrive(xbox.getLeftY() / 2, xbox.getLeftX() / 2);
        } else {
            drivetrainSubsystem.tankDrive(xbox.getLeftY(), xbox.getRightY());
        }
    }

    @Override
    public void end(boolean interrupted) {
    }
}
