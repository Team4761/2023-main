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
import frc.robot.leds.UpdateLED;
import frc.robot.main.Constants;

public class DriveController extends CommandBase {

    private XboxControl xbox;
    private boolean isArcade = true;
    private double multiplier = 1;
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

        // fast medium slow current is medium
        // default as medium

        IntakeSubsystem intakeSubsystem = IntakeSubsystem.getInstance();
        xbox.leftBumper().onTrue(Commands.runOnce(this::inTake, intakeSubsystem));
        xbox.rightBumper().onTrue(Commands.runOnce(this::outTake, intakeSubsystem));
        xbox.leftBumper().onFalse(Commands.runOnce(this::disableIntake, intakeSubsystem));
        xbox.rightBumper().onFalse(Commands.runOnce(this::disableIntake, intakeSubsystem));

        xbox.leftTrigger().onFalse(Commands.runOnce(this::medium));
        xbox.rightTrigger().onFalse(Commands.runOnce(this::medium));
        xbox.leftTrigger().onTrue(Commands.runOnce(this::slow));
        xbox.rightTrigger().onTrue(Commands.runOnce(this::fast));
        //xbox.leftBumper().onTrue(Commands.runOnce(this::onLeftBumper, drivetrainSubsystem));
        //xbox.rightBumper().onTrue(Commands.runOnce(this::onRightBumper, drivetrainSubsystem));
    }

    private void slow() {
        multiplier = 0.35;
    }
    private void medium() {
        multiplier = 1;
    }
    private void fast() {
        multiplier = 2;
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
        CommandScheduler.getInstance().schedule(new TurnDegreesGyro(90));
    }

    private void onPressB() {
        CommandScheduler.getInstance().schedule(new TurnDegreesGyro(-90));
    }

    private void onPressX() {
        UpdateLED.displayCone();
        //CommandScheduler.getInstance().schedule(new GoMetersEncoder(3));
    }

    private void onPressY() {
        UpdateLED.displayCube();
        //CommandScheduler.getInstance().schedule(new GoMetersEncoder(-3));
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

    // 90 degree turns are conflicting with this
    private void arcadeDrive() {
        DifferentialDrive.WheelSpeeds wheelSpeeds = arcadeDriveIK((xbox.getLeftY()+Math.signum(xbox.getLeftY())*0.25)/1.25, -(xbox.getRightX())*Constants.DRIVETRAIN_ROTATION_SPEED);
        
        // changing to make acceleration only take as long as the side that accelerates the least
        // so when going from straight to turning, one side would not be limited while the other isn't

        // the time it would take for the lower change side is t = lowerSpeedNeeded/MAXACCEL
        // the maxChange to get the higher change side to that is higherSpeedNeeded/t*(timer.get()-lastTime)

        double lowerMaxChange = Math.abs((timer.get()-lastTime) * Constants.DRIVETRAIN_MAX_ACCELERATION * 1); // lower max change for lower desired-current
        double higherMaxChange;

        if (wheelSpeeds.left*1.5-outputL != 0 && wheelSpeeds.right*1.5-outputR!=0) {
            // higherNeeded*maxAccel/lowerneeded*deltaT
            higherMaxChange = Math.max(Math.abs(wheelSpeeds.left*1.5-outputL),Math.abs(wheelSpeeds.right*1.5-outputR)) * Constants.DRIVETRAIN_MAX_ACCELERATION / Math.min(Math.abs(wheelSpeeds.left*1.5-outputL),Math.abs(wheelSpeeds.right*1.5-outputR)) * (timer.get()-lastTime);
        } else {
            // very high
            higherMaxChange = 10000;
        }
        
        // if left needs less change
        if (Math.abs(outputL-wheelSpeeds.left*1.5)<Math.abs(outputR-wheelSpeeds.right*1.5)) {
            outputL = MathUtil.clamp(wheelSpeeds.left*1.5, outputL-lowerMaxChange, outputL+lowerMaxChange);
            outputR = MathUtil.clamp(wheelSpeeds.right*1.5, outputR-higherMaxChange, outputR+higherMaxChange);
        } else {
            outputL = MathUtil.clamp(wheelSpeeds.left*1.5, outputL-higherMaxChange, outputL+higherMaxChange);
            outputR = MathUtil.clamp(wheelSpeeds.right*1.5, outputR-lowerMaxChange, outputR+lowerMaxChange);
        }

        //          kV                                    kS
        double vL = outputL*Constants.DRIVETRAIN_SPEED*multiplier + Math.signum(outputL)*0.3 + 0.1 * (outputL-Paligator.getLeftVelocity()*Constants.distancePerEncoderTick);
        double vR = outputR*Constants.DRIVETRAIN_SPEED*multiplier + Math.signum(outputR)*0.2 + 0.1 * (outputR-Paligator.getRightVelocity()*Constants.distancePerEncoderTick);

        // trying feed forward with numbers from sysid, need new sysid measurements                     
        // ks, kv, ka
        //double vL = 0.1768 * Math.signum(outputL) + 4.676/1.5 * outputL;// + 0.1287 * (outputL-Paligator.getLeftVelocity()*Constants.distancePerEncoderTick);
        //double vR = 0.1008 * Math.signum(outputR) + 4.784/1.5 * outputR;// + 0.137 * (outputR-Paligator.getRightVelocity()*Constants.distancePerEncoderTick);

        lastTime = timer.get();

        Paligator.setVoltages(vL, vR);
    }

    @Override
    public void end(boolean interrupted) {
    }

    // math from differentialDrive
    DifferentialDrive.WheelSpeeds arcadeDriveIK(double xSpeed, double zRotation) {
        // removed deadzone here because applied in xboxcontrol
        xSpeed = MathUtil.clamp(xSpeed, -1.0, 1.0);
        zRotation = MathUtil.clamp(zRotation, -1.0, 1.0);

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
