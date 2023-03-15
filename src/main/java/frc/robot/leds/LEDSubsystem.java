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
    public int led_WIDTH = 64;                      // The width of the LED strip
    public int led_NumOfBuffers = 4;                // The number of preloaded buffers

    AddressableLED m_led;
    AddressableLEDBuffer m_ledBuffer;
    AddressableLEDBuffer[] m_ledBuffers = new AddressableLEDBuffer[led_NumOfBuffers];

    // Text display
    boolean[][] currentText = new boolean[led_WIDTH][led_HEIGHT];
    EncodedCharacters encChars;
    AddressableLEDBuffer textBuffer = new AddressableLEDBuffer(led_HEIGHT * led_WIDTH);


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

    public void drawText(char nextChar, int stage) {
        // Shift all of the text over by 1 column
        for (int i = led_WIDTH-3; i > 0; i--) {
            currentText[i] = currentText[i-1];
        }
        // Add the new text (column) that should appear
        if (stage <= 4)
            currentText[0] = encChars.getLetter(nextChar)[4-stage];
        else if (stage == 5)
            currentText[0] = encChars.SPACE;
        // Add the current text information to the led buffer
        for (int x = 0; x < led_WIDTH; x++) {
            for (int y = 0; y < led_HEIGHT; y++) {
                if (currentText[x][y])
                    textBuffer.setRGB(y*led_WIDTH+led_WIDTH-1-x,255,255,255);
                else
                    textBuffer.setRGB(y*led_WIDTH+led_WIDTH-1-x,0,0,0);
            }
        }
        m_led.setData(textBuffer);
    }
}