package frc.robot.leds;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.main.Robot;

public class UpdateLED extends CommandBase {
    static int curPattern = 0;
    static long nextTime = 0;

    @Override
    public void execute() {
    }

    public static void setPattern() {
        if (nextTime <= System.currentTimeMillis()) {
            Robot.leds.setLEDs(curPattern);
            curPattern++;
            curPattern %= 4;
            nextTime = System.currentTimeMillis() + 10000;
        }
    }
}
