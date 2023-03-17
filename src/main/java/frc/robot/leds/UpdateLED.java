package frc.robot.leds;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.main.Robot;
import java.util.ArrayList;

public class UpdateLED extends CommandBase {
    // Hardcoded Pattern Values:
    public static final int SOLID_COLOR = 0;
    public static final int TEXT_DISPLAY = 1;

    // Decides Whether To Display Colors or Text
    public static int currentState = TEXT_DISPLAY;

    // String Display Variables
    private static ArrayList<Character> charactersToDisplay = new ArrayList<Character>();
    private static ArrayList<Color> colorBuffer = new ArrayList<Color>();
    public static boolean textRepeating = true;
    private char curChar;
    private long nextTime = 0;
    public static int textSpeed = 100;    // Milliseconds
    private int textStage = 0;
    private Color color = new Color(100,100,100);
    public static boolean isStatic = false;
    

    @Override
    public void execute() {
        Robot.m_shuffleboard.updateLEDs();

        if (currentState == TEXT_DISPLAY && !isStatic) {
            if (nextTime <= System.currentTimeMillis()) {
                if (textStage == 0 && colorBuffer.size() != 0 && charactersToDisplay.size() != 0) {
                    color = colorBuffer.get(0);
                    curChar = charactersToDisplay.get(0);
                    if (textRepeating) {
                        colorBuffer.add(colorBuffer.get(0));
                        charactersToDisplay.add(charactersToDisplay.get(0));
                    }
                    colorBuffer.remove(0);
                    charactersToDisplay.remove(0);
                }
                Robot.leds.drawText(curChar, textStage, color);
                textStage = (textStage+1) % 6;
                nextTime = System.currentTimeMillis() + textSpeed;
            }
        }
        else if (currentState == TEXT_DISPLAY && isStatic) {
            Character[] chars = new Character[charactersToDisplay.size()];
            chars = charactersToDisplay.toArray(chars);
            Robot.leds.drawStaticText(chars, colorBuffer.get(0));
        }
    }

    // These methods are run in the DriveController command
    public static void displayCone() {
        if (currentState == SOLID_COLOR) {
            Robot.leds.setLEDs(2);
        }
        else if (currentState == TEXT_DISPLAY) {
            setText("%(20,25,0)CONE ");
        }
    }

    public static void displayCube() {
        if (currentState == SOLID_COLOR) {
            Robot.leds.setLEDs(3);
        }
        else if (currentState == TEXT_DISPLAY) {
            setText("%(5,0,10)CUBE ");
        }
    }

    public static void setText(String m) {
        colorBuffer.clear();
        charactersToDisplay.clear();
        for (int i = 0; i < m.length(); i++) {
            // Check for color, format is "%(0,255,20)"
            if (m.charAt(i) == '%') {
                int[] rgb = getNumber(m, i+2); // Start after parenthesis
                colorBuffer.add(new Color(rgb[0], rgb[1], rgb[2]));
                i += rgb[3]; // The size value
            }
            else {
                colorBuffer.add(colorBuffer.get(colorBuffer.size()-1));
            }
            charactersToDisplay.add(m.charAt(i));
        }
    }

    private static int[] getNumber(String s, int index) {
        int r = 0;
        int g = 0;
        int b = 0;
        int size = 3;   // Starting with 2 parenthesis and 1 comma
        int start_i = index+1;
        int num_len = 0;
        for (int i = index; i < index+12; i++) {
            if (s.charAt(i) == ',') {
                b = Integer.valueOf(s.substring(start_i-1, start_i+num_len-1));
                r = g;
                g = b;
                start_i += num_len+1;
                num_len = 0;
            }
            else if (s.charAt(i) == ')') {
                b = Integer.valueOf(s.substring(start_i-1, start_i+num_len-1));
                return new int[]{r, g, b, size};
            }
            else {
                num_len++;
            }
            size++;
        }
        return null;    // Should never get to this point
    }

    public static void setPattern(int pattern) {
        Robot.leds.setLEDs(pattern);
    }
}
