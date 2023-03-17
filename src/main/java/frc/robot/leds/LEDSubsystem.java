    package frc.robot.leds;

    import edu.wpi.first.wpilibj.AddressableLED;
    import edu.wpi.first.wpilibj.AddressableLEDBuffer;
    import edu.wpi.first.wpilibj2.command.SubsystemBase;
    import frc.robot.main.Constants;

    public class LEDSubsystem extends SubsystemBase {
    // Create an instance
    private final static LEDSubsystem INSTANCE = new LEDSubsystem(Constants.LED_PORT);
    
    // LED Specific Information
    private int led_PORT;                           // The port connected to the LED strip
    public int led_HEIGHT = 8;                      // The height of the LED strip
    public int led_WIDTH = 32;                      // The width of the LED strip
    public int led_NumOfBuffers = 4;                // The number of preloaded buffers

    AddressableLED m_led;
    AddressableLEDBuffer m_ledBuffer;
    AddressableLEDBuffer[] m_ledBuffers = new AddressableLEDBuffer[led_NumOfBuffers];

    // Text display
    boolean[][] currentText = new boolean[led_WIDTH][led_HEIGHT];
    EncodedCharacters encChars;
    AddressableLEDBuffer textBuffer = new AddressableLEDBuffer(led_HEIGHT * led_WIDTH);
    AddressableLEDBuffer letterBuffer = new AddressableLEDBuffer(led_HEIGHT * led_WIDTH);
    Color[] textColors = new Color[led_WIDTH];
    Color curColor = new Color(0,0,0);
    public static boolean textInverted = false;


    @SuppressWarnings("WeakerAccess")
    public static LEDSubsystem getInstance() {
        return INSTANCE;
    }

    public LEDSubsystem(int portPWM) {
        super();
        this.led_PORT = portPWM;
        m_led = new AddressableLED(led_PORT);
        m_ledBuffer = new AddressableLEDBuffer(led_HEIGHT * led_WIDTH);

        // Functionality
        loadBuffers();
        int ledLength = led_HEIGHT * led_WIDTH;
        m_led.setLength(ledLength);
        m_led.setData(m_ledBuffer);

        // Text
        encChars = new EncodedCharacters();
    }


    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void enableLEDs () {
        // Clear text buffer
        for (int x = 0; x < led_WIDTH; x++) {
            for (int y = 0; y < led_HEIGHT; y++) {
                currentText[x][y] = false;
            }
        }
        m_led.start();
    }

    public void disableLEDs () {
        setAllLEDs(0,0,0);
    }

    private void loadBuffers () {
        for (int buffer = 0; buffer < led_NumOfBuffers; buffer++)
        m_ledBuffers[buffer] = new AddressableLEDBuffer(led_HEIGHT * led_WIDTH);

        for (int i = 0; i < led_HEIGHT * led_WIDTH; i++)
        m_ledBuffers[0].setRGB(i,200,0,0);
        for (int i = 00; i < led_HEIGHT * led_WIDTH; i++)
        m_ledBuffers[1].setRGB(i,0,0,200);
        for (int i = 0; i < led_HEIGHT * led_WIDTH; i++)
        m_ledBuffers[2].setRGB(i,200,255,0);
        for (int i = 0; i < led_HEIGHT * led_WIDTH; i++)
        m_ledBuffers[3].setRGB(i,100,0,200);
    }

    public void setLEDs (int pattern) {
        m_led.setData(m_ledBuffers[pattern]);
    }

    public void setLEDs (AddressableLEDBuffer bufferToUse) {
        m_led.setData(bufferToUse);
    }

    public void setAllLEDs (int r, int g, int b) {
        AddressableLEDBuffer buff = new AddressableLEDBuffer(led_HEIGHT * led_WIDTH);
        for (int i = 0; i < led_HEIGHT * led_WIDTH; i++)
            buff.setRGB(i, r, g, b);
        m_led.setData(buff);
    }

    public void drawStaticText(Character[] characters, Color color) {
        int charOffset = characters.length*3-1 + led_WIDTH/2;
        for (int i = 0; i < characters.length; i++) {
            if (i*6-charOffset <= led_WIDTH && i*6-charOffset >= 0) {
                drawLetter(characters[i], i*6-charOffset, color);
            }
        }
    }

    public void drawLetter(char c, int row, Color color) {   // row is measured as the left most row
        for (int i = 0; i < led_WIDTH * led_HEIGHT; i++) {
            if (textInverted)
                letterBuffer.setRGB(i, color.r, color.g, color.b);
            else
                letterBuffer.setRGB(i, 0, 0, 0);
        }
        boolean[][] chars = encChars.getLetter(c);
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < led_HEIGHT; y++) {
                if (chars[x][y] && !textInverted) {
                    letterBuffer.setRGB(x*led_HEIGHT+y, color.r, color.g, color.b);
                }
                else if (chars[x][y] && textInverted) {
                    letterBuffer.setRGB(x*led_HEIGHT+y, 0, 0, 0);
                }
            }
        }
    }

    public void drawText(char nextChar, int stage, Color c) {
        // Shift all of the text over by 1 column
        for (int i = led_WIDTH-1; i > 0; i--) {
            textColors[i] = textColors[i-1];
            currentText[i] = currentText[i-1];
        }
        // Add the new text (column) that should appear
        if (stage <= 4) {
            textColors[0] = c;
            currentText[0] = encChars.getLetter(nextChar)[4-stage];
        }
        else if (stage == 5)
            currentText[0] = encChars.SPACE;
        // Add the current text information to the led buffer
        int count = 0;
        for (int x = led_WIDTH-1; x >= 0; x--) {
            count++;
            for (int y = 0; y < led_HEIGHT; y++) {
                if (!textInverted) {
                    if (currentText[x][y] && count % 2 == 0)
                        textBuffer.setRGB(x*led_HEIGHT+led_HEIGHT-y-1, textColors[x].r, textColors[x].g, textColors[x].b);
                    else if (count % 2 == 0)
                        textBuffer.setRGB(x*led_HEIGHT+led_HEIGHT-y-1,0,0,0);
                    else if (currentText[x][y] && count % 2 == 1)
                        textBuffer.setRGB(x*led_HEIGHT+y, textColors[x].r, textColors[x].g, textColors[x].b);
                    else
                        textBuffer.setRGB(x*led_HEIGHT+y,0,0,0);
                }
                else if (textColors[x] != null){
                    if (currentText[x][y] && count % 2 == 0)
                        textBuffer.setRGB(x*led_HEIGHT+led_HEIGHT-y-1,0,0,0);
                    else if (count % 2 == 0)
                        textBuffer.setRGB(x*led_HEIGHT+led_HEIGHT-y-1, textColors[x].r, textColors[x].g, textColors[x].b);
                    else if (currentText[x][y] && count % 2 == 1)
                        textBuffer.setRGB(x*led_HEIGHT+y,0,0,0);
                    else
                        textBuffer.setRGB(x*led_HEIGHT+y, textColors[x].r, textColors[x].g, textColors[x].b);
                }
            }
        }
        m_led.setData(textBuffer);
    }

    public void clearText() {
        textColors = new Color[led_HEIGHT];
        currentText = new boolean[led_HEIGHT][led_WIDTH];
    }
}