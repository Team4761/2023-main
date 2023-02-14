package frc.robot.controller;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.main.Robot;

public class XboxControl {
    private CommandXboxController xbox;
    private boolean inverted = true;
    public XboxControl(int port)
    {
        xbox = new CommandXboxController(port);
    }


    double applyDeadzone (double value, double deadzone) {
        if (Math.abs(value) > deadzone) {
            if (value > 0.0) {
                return (value - deadzone) / (1.0 - deadzone);
            } else {
                return (value + deadzone) / (1.0 - deadzone);
            }
        } else {
            return 0.0;
        }

    }
    public double getLeftX()
    {
        double left = xbox.getLeftX() * (inverted ? -1 : 1);
        SmartDashboard.putNumber("CONTROLLER[02] Left Axis X", left);
        return applyDeadzone(left,0.05);
    }
    
    public double getRightX()
    {
        double right = xbox.getRightX() ;
        SmartDashboard.putNumber("CONTROLLER[00] Right Axis X", right);
        return applyDeadzone(right,0.05);
    }
    public double getLeftY()
    {
        double left = xbox.getLeftY() * (inverted ? -1 : 1);
        SmartDashboard.putNumber("CONTROLLER[03] Left Axis Y", left);
        return applyDeadzone(left,0.05);
    }
    public double getRightY()
    {
        double right = xbox.getRightY() * (inverted ? -1 : 1);
        SmartDashboard.putNumber("CONTROLLER[01] Right Axis Y", right);
        return applyDeadzone(right,0.05);
    }
    public CommandXboxController getController()
    {
        return xbox;
    }



}
