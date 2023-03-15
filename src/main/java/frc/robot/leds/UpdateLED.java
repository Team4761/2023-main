package frc.robot.leds;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.main.Robot;
import java.util.ArrayList;

public class UpdateLED extends CommandBase {
    // Hardcoded Pattern Values:
    private final int SOLID_COLOR = 0;
    private final int TEXT_DISPLAY = 1;

    // Decides Whether To Display Colors or Text
    private int currentState = TEXT_DISPLAY;

    // String Display Variables
    private static ArrayList<Character> charactersToDisplay = new ArrayList<Character>();
    private boolean textRepeating = true;
    private char curChar;
    private long nextTime = 0;
    private int textSpeed = 100;    // Milliseconds
    private int textStage = 0;
    

    @Override
    public void execute() {
        if (currentState == TEXT_DISPLAY) {
            if (nextTime <= System.currentTimeMillis()) {
                if (textStage == 0) {
                    curChar = charactersToDisplay.get(0);
                    if (textRepeating)
                        charactersToDisplay.add(charactersToDisplay.get(0));
                    charactersToDisplay.remove(0);
                }
                Robot.leds.drawText(curChar, textStage);

                textStage = (textStage+1) % 6;
                nextTime = System.currentTimeMillis() + textSpeed;
            }
        }
    }

    public static void setText(String m) {
        charactersToDisplay.clear();
        for (int i = 0; i < m.length(); i++) {
            charactersToDisplay.add(m.charAt(i));
        }
    }

    public static void setPattern(int pattern) {
        Robot.leds.setLEDs(pattern);
    }
}
