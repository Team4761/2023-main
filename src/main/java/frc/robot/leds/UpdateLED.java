package frc.robot.leds;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.main.Robot;
import java.util.ArrayList;

public class UpdateLED extends CommandBase {
    // Hardcoded Pattern Values:
    private final int SOLID_COLOR = 0;
    private final int TEXT_DISPLAY = 1;

    // Decides Whether To Display Colors or Text
    private int currentState = 0;

    // String Display Variables
    private ArrayList<Character> charactersToDisplay = new ArrayList<Character>();
    private boolean textRepeating = false;
    private char curChar;
    private long nextTime = 0;
    private int textSpeed = 500;    // Milliseconds
    private int textStage = 0;
    

    @Override
    public void execute() {
        if (currentState == TEXT_DISPLAY) {
            if (nextTime <= System.currentTimeMillis()) {
                if (textStage == 0)
                    curChar = charactersToDisplay.get(0);
                Robot.leds.drawText(curChar, textStage);

                textStage = (textStage+1) % 6;
                nextTime = System.currentTimeMillis() + textSpeed;
            }
        }
    }

    public static void setPattern(int pattern) {
        Robot.leds.setLEDs(pattern);
    }
}
