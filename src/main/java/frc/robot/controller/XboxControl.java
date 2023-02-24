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
        SmartDashboard.putNumber("CONTROLLER[02] Left Axis X", left);
        return applyDeadzone(left, 0.05);
    }

    @Override
    public double getRightX() {
        double right = super.getRightX() ;
        SmartDashboard.putNumber("CONTROLLER[00] Right Axis X", right);
        return applyDeadzone(right,0.05);
    }

    @Override
    public double getLeftY() {
        double left = super.getLeftY() * (inverted ? -1 : 1);
        SmartDashboard.putNumber("CONTROLLER[03] Left Axis Y", left);
        return applyDeadzone(left,0.05);
    }

    @Override
    public double getRightY() {
        double right = super.getRightY() * (inverted ? -1 : 1);
        SmartDashboard.putNumber("CONTROLLER[01] Right Axis Y", right);
        return applyDeadzone(right,0.05);
    }
}