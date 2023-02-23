package frc.robot.command;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.arm.ArmMath;
import frc.robot.arm.ArmSubsystem;
import frc.robot.controller.XboxControl;
import frc.robot.main.Constants;
import frc.robot.main.Robot;

public class ArmControl extends CommandBase {
    protected XboxControl xbox = Robot.xbox;
    ArmMath armMath = new ArmMath();

    public ArmControl() {
        //xbox.getController().b().onTrue(this::onPressB);



        ArmSubsystem armSubsystem = ArmSubsystem.getInstance();
        xbox.getController().b().onTrue(Commands.runOnce(this::onPressB, armSubsystem));
        xbox.getController().y().onTrue(Commands.runOnce(this::onPressY, armSubsystem));
        xbox.getController().x().onTrue(Commands.runOnce(this::onPressX, armSubsystem));
        xbox.getController().a().onTrue(Commands.runOnce(this::onPressA, armSubsystem));
        SmartDashboard.putNumber("fupper arm angle", 0);
        SmartDashboard.putNumber("flower arm angle", 0);
        SmartDashboard.putNumber("farm x togo", 0);
        SmartDashboard.putNumber("farm y togo", 0);
        SmartDashboard.putNumber("farm 1 theta calc", 0);
        SmartDashboard.putNumber("farm 2 theta calc", 0);
    }

    //current range of motion is 0.8 to -2.5 use smart dashboard to set rotation angle.
    private void onPressB() {
        movePID(SmartDashboard.getNumber("fupper arm angle",0), SmartDashboard.getNumber("flower arm angle", 0));
        Robot.arms.movePID();
    }

    private void onPressA() {
        Robot.arms.enablePID();
    }
    private void onPressX() {
        Robot.arms.disablePID();
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
    }
    private void onPressY() {
        movePID(0.5,0);
    }

    @Override
    public void execute() {
        //Robot.arms.updatePos(xbox.getRightX(), xbox.getRightY());
       // Robot.arms.movePID();
        //obot.arms.setBottomL(xbox.getRightY()/2);
        //Robot.arms.setBottomR(xbox.getLeftY()/2);
        //Robot.arms.setBottom(xbox.getRightY());
        //Robot.arms.setTop(xbox.getLeftY()/2);
        
        // Emergency Stop!


        //xbox.getController().x().onTrue(Commands.runOnce(() -> { zeroEncoders(); }));
        // Debugging purposes only
        Robot.arms.debug();
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
