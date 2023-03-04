package frc.robot.command;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
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
    protected XboxControl xbox;
    ArmMath armMath = new ArmMath();

    public ArmControl(int port) {
        xbox = new XboxControl(port);

        ArmSubsystem armSubsystem = ArmSubsystem.getInstance();
        xbox.b().onTrue(Commands.runOnce(this::onPressB, armSubsystem));
        xbox.y().onTrue(Commands.runOnce(this::onPressY, armSubsystem));
        xbox.x().onTrue(Commands.runOnce(this::onPressX, armSubsystem));
        xbox.a().onTrue(Commands.runOnce(this::onPressA, armSubsystem));
        

        SmartDashboard.putNumber("fupper arm angle", 0);
        SmartDashboard.putNumber("flower arm angle", 0);
        SmartDashboard.putNumber("farm x togo", 0);
        SmartDashboard.putNumber("farm y togo", 0);
        SmartDashboard.putNumber("farm 1 theta calc", 0);
        SmartDashboard.putNumber("farm 2 theta calc", 0);
    }

    //current range of motion is 0.8 to -2.5 use smart dashboard to set rotation angle.
    private void onPressB() {
        //movePID(SmartDashboard.getNumber("fupper arm angle",0), SmartDashboard.getNumber("flower arm angle", 0));
        //Robot.arms.movePID();
        //Robot.arms.moveToSetRotation(Constants.INTAKE_POSITION);
    }

    private void onPressA() {
        //Robot.arms.enablePID();
        //Robot.arms.movePID();
        //Robot.arms.moveToSetRotation(Constants.MID_RUNG_POSITION);
    }

    private void onPressX() {
        //Robot.arms.disablePID();
/* 
        SmartDashboard.putNumber("farm x togo", armMath.getPoint(
                SmartDashboard.getNumber("farm 1 theta calc",0),
                SmartDashboard.getNumber("farm 2 theta calc", 0)
        ).getX());

        SmartDashboard.putNumber("farm y togo", armMath.getPoint(
                SmartDashboard.getNumber("farm 1 theta calc",0),
                SmartDashboard.getNumber("farm 2 theta calc", 0)
        ).getY());
*/
        //Robot.arms.moveToSetRotation(Constants.MID_RUNG_POSITION);
    }
    private void onPressY() {
        //movePID(0.3,0.3);
        //Robot.arms.moveToSetRotation(Constants.TOP_RUNG_POSITION);
    }

    @Override
    public void execute() {
        //Robot.arms.updatePos(xbox.getRightX(), xbox.getRightY());
        //Robot.arms.movePID();
        //Robot.arms.setBottomL(xbox.getRightY()/2);
        //Robot.arms.setBottomR(xbox.getLeftY()/2);
        manualControl();
        
        // Emergency Stop!


        //xbox.getController().x().onTrue(Commands.runOnce(() -> { zeroEncoders(); }));
        // Debugging purposes only
        xbox.leftBumper().whileTrue(Commands.run(this::inTake, DrivetrainSubsystem.getInstance()));
        xbox.rightBumper().whileTrue(Commands.run(this::outTake, DrivetrainSubsystem.getInstance()));
        
        xbox.leftBumper().whileFalse(Commands.run(this::disableIntake, DrivetrainSubsystem.getInstance()));
        xbox.rightBumper().whileFalse(Commands.run(this::disableIntake, DrivetrainSubsystem.getInstance()));
        Robot.arms.debug();

    }

    public void manualControl() {
        Translation2d curPoint = (armMath.getPoint(ArmSubsystem.getInstance().getTopRotation() + xbox.getRightY()*0.05, ArmSubsystem.getInstance().getBottomRotation() + xbox.getLeftY()*0.05));
        SmartDashboard.putNumber("ARMS: TestPointX", curPoint.getX());
        SmartDashboard.putNumber("ARMS: TestPointY", curPoint.getY());
        System.out.println(curPoint.getX() + " | " + curPoint.getY());
        
        //if (armMath.inBounds(armMath.getPoint(ArmSubsystem.getInstance().getTopRotation() + xbox.getRightY()*0.05, ArmSubsystem.getInstance().getBottomRotation() + xbox.getLeftY()*0.05))) {
            Robot.arms.setTop(xbox.getLeftY()*0.2);
            Robot.arms.setBottom(xbox.getRightY()*0.15);
        //}
        //else {
        //    Robot.arms.setTop(0.0);
        //    Robot.arms.setBottom(0.0);
        //}
        
        
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

    public void movePID(double setTopRotation, double setBottomRotation) {
        //top is joint 2
        //if(setTopRotation > Constants.JOINT_2_MIN && setTopRotation < Constants.JOINT_2_MAX){
            ArmSubsystem.setDesiredTopRotation(setTopRotation);
       /* }else if(setTopRotation > Constants.JOINT_2_MAX){
            ArmSubsystem.setDesiredTopRotation(Constants.JOINT_2_MAX);
        }else if(setTopRotation < Constants.JOINT_2_MIN){
            ArmSubsystem.setDesiredTopRotation(Constants.JOINT_2_MIN);
        }

        */

        //bototm in joint 1
       // if(setBottomRotation > Constants.JOINT_2_MIN && setBottomRotation < Constants.JOINT_2_MAX){
            ArmSubsystem.setDesiredBottomRotation(setBottomRotation);
        /*}else if(setBottomRotation > Constants.JOINT_1_MAX){
            ArmSubsystem.setDesiredBottomRotation(Constants.JOINT_2_MAX);
        }else if(setBottomRotation < Constants.JOINT_1_MIN){
            ArmSubsystem.setDesiredBottomRotation(Constants.JOINT_2_MIN);
        }

         */
       // Robot.arms.movePID();
    }
    public void emergencyStop() {
        Robot.arms.emergencyStop();
    }

    public void zeroEncoders() {
        Robot.arms.zeroEncoders();
    }
}
