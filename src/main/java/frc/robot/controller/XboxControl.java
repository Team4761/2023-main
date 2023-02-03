package frc.robot.controller;

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
        return applyDeadzone(left,0.05);
    }
    
    public double getRightX()
    {
        double right = xbox.getRightX() ;
        return applyDeadzone(right,0.05);
    }
    public double getLeftY()
    {
        double left = xbox.getLeftY() * (inverted ? -1 : 1);
        return applyDeadzone(left,0.05);
    }
    public double getRightY()
    {
        double right = xbox.getRightY() * (inverted ? -1 : 1);
        return applyDeadzone(right,0.05);
    }




}
