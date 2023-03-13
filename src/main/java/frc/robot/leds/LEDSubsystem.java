    package frc.robot.leds;

    import edu.wpi.first.wpilibj.AddressableLED;
    import edu.wpi.first.wpilibj.AddressableLEDBuffer;
    import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
    import edu.wpi.first.wpilibj2.command.SubsystemBase;
    import frc.robot.main.Constants;

    public class LEDSubsystem extends SubsystemBase {
    // Create an instance
    private final static LEDSubsystem INSTANCE = new LEDSubsystem(Constants.LED_PORT);
    // LED Specific information:
    int led_PORT;                           // The port connected to the LED strip
    int led_HEIGHT = 1;                     // The height of the LED strip
    int led_WIDTH = Constants.LED_NUMBER;    // The width of the LED strip
    int led_NumOfBuffers = 4;               // The number of preloaded buffers

    AddressableLED m_led = new AddressableLED(led_PORT);
    AddressableLEDBuffer m_ledBuffer = new AddressableLEDBuffer(led_HEIGHT * led_WIDTH);
    AddressableLEDBuffer[] m_ledBuffers = new AddressableLEDBuffer[led_NumOfBuffers];


    @SuppressWarnings("WeakerAccess")
    public static LEDSubsystem getInstance() {
        return INSTANCE;
    }

    public LEDSubsystem(int portPWM) {
        super();
        this.led_PORT = portPWM;

        // Functionality
        loadBuffers();
        m_led.setLength(m_ledBuffer.getLength());
        setLEDs(1);
    }


    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void enableLEDs () {
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
}