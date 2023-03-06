package frc.robot.controller;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class XboxControl extends CommandXboxController {
    private boolean inverted = true;
    public XboxControl(int port)
    {
        super(port);
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

    @Override
    public double getLeftX() {
        double left = super.getLeftX() * (inverted ? -1 : 1);
        return applyDeadzone(left, 0.05);
    }

    @Override
    public double getRightX() {
        double right = super.getRightX() ;
        return applyDeadzone(right,0.05);
    }

    @Override
    public double getLeftY() {
        double left = super.getLeftY() * (inverted ? -1 : 1);
        return applyDeadzone(left,0.05);
    }

    @Override
    public double getRightY() {
        double right = super.getRightY() * (inverted ? -1 : 1);
        return applyDeadzone(right,0.05);
    }
}