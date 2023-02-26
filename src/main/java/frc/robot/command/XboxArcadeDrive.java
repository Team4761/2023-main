package frc.robot.command;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive.WheelSpeeds;
import frc.robot.Drivetrain.DrivetrainSubsystem;
import frc.robot.impl.placeholder.Placeholder;
import frc.robot.main.Constants;
import frc.robot.main.Robot;
public class XboxArcadeDrive extends XboxDriveBase {
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
    @Override
    public void execute() {
        WheelSpeeds wheelSpeeds = arcadeDriveIK(xbox.getLeftY(), xbox.getRightX());

        double maxChange = (timer.get()-lastTime) * Constants.DRIVETRAIN_MAX_ACCELERATION;
        outputL = Math.max(Math.min(wheelSpeeds.left*1.5, outputL+maxChange), outputL-maxChange);        
        outputR = Math.max(Math.min(wheelSpeeds.right*1.5, outputR+maxChange), outputR-maxChange);
        lastTime = timer.get();
        
        outputL = - (2.5 * outputL +  0.2 * (outputL - Placeholder.getLeftVelocity()*Constants.distancePerEncoderTick));
        outputR = 2.5 * outputR +  0.2 * (outputR - Placeholder.getRightVelocity()*Constants.distancePerEncoderTick);
        
        outputL += Math.signum(outputL)*0.65;
        outputR += Math.signum(outputR)*0.6;

        Placeholder.setVoltages(outputL, outputR);
    }

    @Override
    public void end(boolean interrupted) {
        //Robot.driveTrain.arcadeDrive(0,0);
    }

    // math from differentialDrive
    public static WheelSpeeds arcadeDriveIK(double xSpeed, double zRotation) {
        xSpeed = MathUtil.clamp(xSpeed, -1.0, 1.0);
        zRotation = MathUtil.clamp(zRotation, -1.0, 1.0);
    
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
          return new WheelSpeeds(0.0, 0.0);
        }
        double saturatedInput = (greaterInput + lesserInput) / greaterInput;
        leftSpeed /= saturatedInput;
        rightSpeed /= saturatedInput;
    
        return new WheelSpeeds(leftSpeed, rightSpeed);
      }
}
