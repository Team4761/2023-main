package frc.robot.command;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Drivetrain.DrivetrainSubsystem;
import frc.robot.arm.ArmMath;
import frc.robot.arm.ArmSubsystem;
import frc.robot.controller.XboxControl;
import frc.robot.intake.IntakeSubsystem;
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
        xbox.rightStick().onTrue(Commands.runOnce(this::onPressDisablePidButton, armSubsystem));

        xbox.leftBumper().onTrue(Commands.run(this::onPressLeftBumper, DrivetrainSubsystem.getInstance()));

        xbox.leftTrigger().onTrue(Commands.runOnce(this::onPressTrigger, armSubsystem));
        xbox.rightTrigger().onTrue(Commands.runOnce(this::onPressTrigger, armSubsystem));
    }

    //current range of motion is 0.8 to -2.5 use smart dashboard to set rotation angle.
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

    private void onPressLeftBumper(){
        moveToSetPoint(Constants.NEUTRAL_POSITION);
    }

    private static void moveToSetPoint(Translation2d midRungPosition) {
        Robot.arms.enablePID();
        Robot.arms.movePID();
        Robot.arms.moveToSetRotation(midRungPosition);
    }

    private void onPressTrigger() {
        ArmSubsystem.getInstance().zeroEncoders();
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
        Translation2d curPoint = (armMath.getPoint(ArmSubsystem.getInstance().getTopRotation() + xbox.getRightY()*0.05, ArmSubsystem.getInstance().getBottomRotation() + xbox.getLeftY()*0.05));
        SmartDashboard.putNumber("ARMS: TestPointX", curPoint.getX());
        SmartDashboard.putNumber("ARMS: TestPointY", curPoint.getY());
        System.out.println(curPoint.getX() + " | " + curPoint.getY());

        boolean armBoundsChecker = Robot.m_shuffleboard.armsBoundCheker();
        if (!armBoundsChecker ||
            armMath.inBounds(
                armMath.getPoint(
                    ArmSubsystem.getInstance().getTopRotation() + xbox.getRightY()*0.05,
                    ArmSubsystem.getInstance().getBottomRotation() + xbox.getLeftY()*0.05)
            )
        ) {
            Robot.arms.setTop(xbox.getLeftY() * 0.2);
            Robot.arms.setBottom(xbox.getRightY() * 0.15);
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
}
