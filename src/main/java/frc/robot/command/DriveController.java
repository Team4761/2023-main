package frc.robot.command;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.Drivetrain.DrivetrainSubsystem;
import frc.robot.controller.XboxControl;
import frc.robot.impl.placeholder.Placeholder;
import frc.robot.leds.LEDSubsystem;
import frc.robot.main.Constants;
import frc.robot.main.Robot;

public class DriveController extends CommandBase {
    private XboxControl xbox;
    private boolean isArcade = true;
    DrivetrainSubsystem drivetrainSubsystem = DrivetrainSubsystem.getInstance();
    Timer timer = new Timer();
    double lastTime;
    double outputL = 0;
    double outputR = 0;

    @Override
    public void initialize(){
        timer.start();
        lastTime = timer.get();
    }

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
        LEDSubsystem.getInstance().setAllLEDs(190
                ,0, 0);
    }

    private void onRightTrigger() {
        isArcade = true;
    }

    private void onLeftBumper() {
        //LEDSubsystem.getInstance().setAllLEDs(240, 136, 10);
    }

    private void onRightBumper() {
        LEDSubsystem.getInstance().setAllLEDs(31, 28, 189);
    }

    @Override
    public void execute() {
        if (isArcade) {
            arcadeDrive();
        } else {
            drivetrainSubsystem.tankDrive(xbox.getLeftY(), xbox.getRightY());
        }
    }

    private void arcadeDrive() {
        DifferentialDrive.WheelSpeeds wheelSpeeds = arcadeDriveIK(xbox.getLeftY(), (0-xbox.getRightX())*0.5);

        double maxChange = Math.abs((timer.get()-lastTime) * Constants.DRIVETRAIN_MAX_ACCELERATION * 2);
        outputL = MathUtil.clamp(wheelSpeeds.left*1.5, outputL-maxChange, outputL+maxChange);
        outputR = MathUtil.clamp(wheelSpeeds.right*1.5, outputR-maxChange, outputR+maxChange);

        lastTime = timer.get();

        Placeholder.setVoltages(-outputL*4, outputR*4);
    }

    @Override
    public void end(boolean interrupted) {
    }

    // math from differentialDrive
    DifferentialDrive.WheelSpeeds arcadeDriveIK(double xSpeed, double zRotation) {
        xSpeed = MathUtil.clamp(MathUtil.applyDeadband(xSpeed, 0.1), -1.0, 1.0);
        zRotation = MathUtil.clamp(MathUtil.applyDeadband(zRotation, 0.1), -1.0, 1.0);

        // Square the inputs (while preserving the sign) to increase fine control
        // while permitting full power.
        /*if (squareInputs) {
          xSpeed = Math.copySign(xSpeed * xSpeed, xSpeed);
          zRotation = Math.copySign(zRotation * zRotation, zRotation);
        }*/

        double leftSpeed = xSpeed - zRotation;
        double rightSpeed = xSpeed + zRotation;

        // Find the maximum possible value of (throttle + turn) along the vector
        // that the joystick is pointing, then desaturate the wheel speeds
        double greaterInput = Math.max(Math.abs(xSpeed), Math.abs(zRotation));
        double lesserInput = Math.min(Math.abs(xSpeed), Math.abs(zRotation));
        if (greaterInput == 0.0) {
            return new DifferentialDrive.WheelSpeeds(0.0, 0.0);
        }
        double saturatedInput = (greaterInput + lesserInput/*  /1.2  */) / greaterInput; //see if should divide lesserInput by ~1.2 to slightly increase speed of corner joystic inputs
        leftSpeed /= saturatedInput;
        rightSpeed /= saturatedInput;

        return new DifferentialDrive.WheelSpeeds(leftSpeed, rightSpeed);
    }
}
