package frc.robot.command;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Drivetrain.DrivetrainSubsystem;
import frc.robot.arm.ArmMath;
import frc.robot.arm.ArmSubsystem;
import frc.robot.controller.XboxControl;
import frc.robot.main.Constants;
import frc.robot.main.Robot;

public class ArmControl extends CommandBase {
    public XboxControl xbox;
    public int port;
    ArmMath armMath = new ArmMath();

    public ArmControl(int port) {
        xbox = new XboxControl(port);
        this.port = port;

        ArmSubsystem armSubsystem = ArmSubsystem.getInstance();
        xbox.a().onTrue(Commands.runOnce(this::onPressA, armSubsystem));
        xbox.b().onTrue(Commands.runOnce(this::onPressB, armSubsystem));
        xbox.x().onTrue(Commands.runOnce(this::onPressX, armSubsystem));
        xbox.y().onTrue(Commands.runOnce(this::onPressY, armSubsystem));

        // For the Wii U button board, right stick is actually the start button
        xbox.leftStick().onTrue(Commands.runOnce(this::onPressDisablePidButton, armSubsystem));
        xbox.rightStick().onTrue(Commands.runOnce(this::onPressDisablePidButton, armSubsystem));

        xbox.leftBumper().onTrue(Commands.runOnce(this::onPressLeftBumper, DrivetrainSubsystem.getInstance()));
        xbox.rightBumper().whileTrue(Commands.run(this::onPressRightBumper, DrivetrainSubsystem.getInstance()));
        xbox.rightBumper().onFalse(Commands.runOnce(this::onPressRightBumperRelease, DrivetrainSubsystem.getInstance()));

//        xbox.leftTrigger().onTrue(Commands.runOnce(this::onPressTrigger, armSubsystem));
//        xbox.rightTrigger().onTrue(Commands.runOnce(this::onPressTrigger, armSubsystem));
    }

    private void onPressA() {
        moveToSetPoint(Constants.INTAKE_POSITION);
    }

    private void onPressB() {
        moveToSetPoint(Constants.MID_RUNG_POSITION);
    }

    private void onPressX() {
        moveToSetPoint(Constants.SHELF_POSITION);
    }
    private void onPressY() {
        moveToSetPoint(Constants.TOP_RUNG_POSITION);
    }

    private void onPressLeftBumper() {
        moveToSetPoint(Constants.NEUTRAL_POSITION);
    }

    private void onPressRightBumper() {
        Robot.arms.disablePID();
        Robot.arms.setTop(.05);
        Robot.arms.setBottom(.05);
    }

    private void onPressRightBumperRelease() {
        moveToSetPoint(Constants.NEUTRAL_POSITION);
    }

    private static void moveToSetPoint(Translation2d midRungPosition) {
        Robot.arms.enablePID();
        Robot.arms.movePID();
        Robot.arms.moveToSetRotation(midRungPosition);
    }

    private void onPressDisablePidButton() {
        Robot.arms.disablePID();
    }

    @Override
    public void execute() {
        if (!Robot.arms.isPidEnabled()) {
            manualControl();
        }
        Robot.arms.debug();
    }

    public void manualControl() {
        boolean armBoundsChecker = Robot.m_shuffleboard.armsBoundChecker();
        if (!armBoundsChecker ||
            armMath.inBounds(
                armMath.getPoint(
                    ArmSubsystem.getInstance().getTopRotation() + xbox.getRightY()*0.05,
                    ArmSubsystem.getInstance().getBottomRotation() + xbox.getLeftY()*0.05)
            )
        ) {
            if (xbox.getLeftY() != 0) {
                Robot.arms.setTop(getTopArmSpeed() * (xbox.getLeftY() > 0 ? 1 : -1));
            }
            if (xbox.getRightY() != 0) {
                Robot.arms.setBottom(getBottomArmSpeed() * (xbox.getRightY() > 0 ? 1 : -1));
            }
        }
    }

    // TODO: map this to a button?
    public void emergencyStop() {
        Robot.arms.emergencyStop();
    }

    public void reinitController(int port) {
        xbox = new XboxControl(port);
        this.port = port;
        System.out.println("Changed Controller Port To " + port);
    }

    private double getTopArmSpeed() {
        return Robot.m_shuffleboard.getManualTopArmSpeed();
    }

    private double getBottomArmSpeed() {
        return Robot.m_shuffleboard.getManualBottomArmSpeed();
    }}
