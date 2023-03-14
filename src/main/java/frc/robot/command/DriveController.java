package frc.robot.command;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Drivetrain.DrivetrainSubsystem;
import frc.robot.controller.XboxControl;
import frc.robot.impl.Paligator.Paligator;
import frc.robot.Auto.EncoderAuto.*;
import frc.robot.intake.IntakeSubsystem;
import frc.robot.leds.LEDSubsystem;
import frc.robot.main.Constants;

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
        //xbox.leftTrigger().onTrue(Commands.runOnce(this::onLeftTrigger, drivetrainSubsystem));
        //xbox.rightTrigger().onTrue(Commands.runOnce(this::onRightTrigger, drivetrainSubsystem));

        IntakeSubsystem intakeSubsystem = IntakeSubsystem.getInstance();
        xbox.leftBumper().whileTrue(Commands.run(this::inTake, intakeSubsystem));
        xbox.rightBumper().whileTrue(Commands.run(this::outTake, intakeSubsystem));
        xbox.leftBumper().whileFalse(Commands.run(this::disableIntake, intakeSubsystem));
        xbox.rightBumper().whileFalse(Commands.run(this::disableIntake, intakeSubsystem));
        //xbox.leftBumper().onTrue(Commands.runOnce(this::onLeftBumper, drivetrainSubsystem));
        //xbox.rightBumper().onTrue(Commands.runOnce(this::onRightBumper, drivetrainSubsystem));
    }

    private void inTake() {
        IntakeSubsystem.getInstance().setSpeed(-0.5);
    }
    private void outTake() {
        IntakeSubsystem.getInstance().setSpeed(0.5);
    }
    private void disableIntake() {
        IntakeSubsystem.getInstance().setSpeed(0.0);
    }

    private void onPressA() {
        CommandScheduler.getInstance().schedule(new TurnToGyro(90));
    }

    private void onPressB() {
        CommandScheduler.getInstance().schedule(new TurnToGyro(-90));
    }

    private void onPressX() {
        CommandScheduler.getInstance().schedule(new GoMetersEncoder(3));
    }

    private void onPressY() {
        CommandScheduler.getInstance().schedule(new GoMetersEncoder(-3));
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
        DifferentialDrive.WheelSpeeds wheelSpeeds = arcadeDriveIK(xbox.getLeftY(), -(xbox.getRightX())*Constants.DRIVETRAIN_ROTATION_SPEED);

        double maxChange = Math.abs((timer.get()-lastTime) * Constants.DRIVETRAIN_MAX_ACCELERATION * 1);
        outputL = MathUtil.clamp(wheelSpeeds.left*1.5, outputL-maxChange, outputL+maxChange);
        outputR = MathUtil.clamp(wheelSpeeds.right*1.5, outputR-maxChange, outputR+maxChange);

        //          kV                                    kS
        double vL = outputL*Constants.DRIVETRAIN_SPEED + Math.signum(outputL)*0.25 + 0.1 * (outputL-Paligator.getLeftVelocity()*Constants.distancePerEncoderTick);
        double vR = outputR*Constants.DRIVETRAIN_SPEED + Math.signum(outputR)*0.15 + 0.1 * (outputR-Paligator.getRightVelocity()*Constants.distancePerEncoderTick);

        // trying feed forward with numbers from sysid, need new sysid                      
        // ks, kv, ka
        //double vL = 0.1768 * Math.signum(outputL) + 4.676/1.5 * outputL;// + 0.1287 * (outputL-Paligator.getLeftVelocity()*Constants.distancePerEncoderTick);
        //double vR = 0.1008 * Math.signum(outputR) + 4.784/1.5 * outputR;// + 0.137 * (outputR-Paligator.getRightVelocity()*Constants.distancePerEncoderTick);

        lastTime = timer.get();

        // would be nice to account for static friction somehow
        Paligator.setVoltages(vL, vR);
    }

    @Override
    public void end(boolean interrupted) {
    }

    // math from differentialDrive
    DifferentialDrive.WheelSpeeds arcadeDriveIK(double xSpeed, double zRotation) {
        xSpeed = MathUtil.clamp(MathUtil.applyDeadband(xSpeed, Constants.CONTROLLER_DEADZONE), -1.0, 1.0);
        zRotation = MathUtil.clamp(MathUtil.applyDeadband(zRotation, Constants.CONTROLLER_DEADZONE), -1.0, 1.0);

        // Square the inputs (while preserving the sign) to increase fine control
        zRotation = Math.copySign(Math.pow(zRotation, 3), zRotation);
        xSpeed = Math.copySign(xSpeed * xSpeed, xSpeed);

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
