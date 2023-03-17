package frc.robot.command;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Drivetrain.DrivetrainSubsystem;
import frc.robot.arm.ArmMath;
import frc.robot.arm.ArmSubsystem;
import frc.robot.intake.IntakeSubsystem;
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
        xbox.a().onTrue(new SequentialCommandGroup(new MoveArmAngles(Constants.NEUTRAL_POSITION), new MoveArmAngles(Constants.INTAKE_POSITION)));
        xbox.b().onTrue(new SequentialCommandGroup(new MoveArmAngles(Constants.NEUTRAL_POSITION), new MoveArmAngles(Constants.MID_RUNG_POSITION)));
        xbox.x().onTrue(new SequentialCommandGroup(new MoveArmAngles(Constants.NEUTRAL_POSITION), new MoveArmAngles(Constants.SHELF_POSITION)));
        xbox.y().onTrue(new SequentialCommandGroup(new MoveArmAngles(Constants.NEUTRAL_POSITION), new MoveArmAngles(Constants.MID_RUNG_POSITION).withTimeout(0.8), new MoveArmAngles(Constants.TOP_RUNG_POSITION)));

        // For the Wii U button board, right stick is actually the start button
        xbox.leftStick().onTrue(Commands.runOnce(this::onPressDisablePidButton, armSubsystem));

        xbox.leftStick().onTrue(Commands.runOnce(this::onPressDisablePidButton, armSubsystem));
        xbox.rightStick().onTrue(Commands.runOnce(this::onPressDisablePidButton, armSubsystem));

        xbox.leftBumper().onTrue(Commands.runOnce(this::onPressLeftBumper, DrivetrainSubsystem.getInstance()));
        xbox.rightBumper().whileTrue(Commands.run(this::onPressRightBumper, DrivetrainSubsystem.getInstance()));
        xbox.rightBumper().onFalse(Commands.runOnce(this::onPressRightBumperRelease, DrivetrainSubsystem.getInstance()));

        xbox.leftTrigger().onTrue(Commands.runOnce(this::onPressTrigger, armSubsystem));
        xbox.rightTrigger().onTrue(Commands.runOnce(this::onPressTrigger, armSubsystem));
        
        IntakeSubsystem intakeSubsystem = IntakeSubsystem.getInstance();
        xbox.leftTrigger().whileTrue(Commands.run(this::inTake, intakeSubsystem));
        xbox.rightTrigger().whileTrue(Commands.run(this::outTake, intakeSubsystem));
        xbox.leftBumper().whileFalse(Commands.run(this::disableIntake, intakeSubsystem));
        xbox.rightBumper().whileFalse(Commands.run(this::disableIntake, intakeSubsystem));
    }
    
    private void inTake() {
        IntakeSubsystem.getInstance().setSpeed(0.6);
    }
    private void outTake() {
        IntakeSubsystem.getInstance().setSpeed(-0.1);
    }
    private void disableIntake() {
        IntakeSubsystem.getInstance().setSpeed(0.15);
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
        Robot.arms.setTop(-.01);
        Robot.arms.setBottom(-.01);
    }

    public void onPressTrigger() {
        Robot.arms.enablePID();
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
                //Robot.arms.setTop(getTopArmSpeed() * (xbox.getLeftY() > 0 ? 1 : -1));
            }
            if (xbox.getRightY() != 0) {
                //Robot.arms.setBottom(getBottomArmSpeed() * (xbox.getRightY() > 0 ? 1 : -1));
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
