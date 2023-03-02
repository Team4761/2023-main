package frc.robot.leds;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.main.Robot;

public class UpdateLED extends CommandBase {
    static int curPattern = 0;
    static long nextTime = 0;

    @Override
    public void execute() {
        SmartDashboard.putBoolean("LEDS[00] Is changing", false);
    }

    public static void setPattern() {
        if (nextTime <= System.currentTimeMillis()) {
            Robot.leds.setLEDs(curPattern);
            curPattern++;
            curPattern %= 4;
            SmartDashboard.putBoolean("LEDS[00] Is changing", true);
            SmartDashboard.putNumber("LEDS[01] Current Pattern", curPattern);
            nextTime = System.currentTimeMillis() + 500;
        }
    }
}
