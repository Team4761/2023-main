package frc.robot.arm;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// RADIANS

public class AbsoluteEncoder extends SubsystemBase {
    private DutyCycleEncoder m_dutyCycleEncoder;
    // CONNECT USING the white, red, and black cable.
    // The WHITE cable is the signal wire.

    public AbsoluteEncoder() {
        m_dutyCycleEncoder = new DutyCycleEncoder(0);   //Default port is 0
    }
    AbsoluteEncoder(int dioPORT) {
        try {
            m_dutyCycleEncoder = new DutyCycleEncoder(dioPORT);     // Connects to a DIO port
        } catch (Exception e) {
            System.out.println("Encoder on point [" + dioPORT + "] failed to connect.");
        }
    }

    // Getters and Setters
    public boolean isConnected() {
        return m_dutyCycleEncoder.isConnected();
    }
    // Returns radians
    public double getRotation() {
        return m_dutyCycleEncoder.getAbsolutePosition() * 2*Math.PI;    // Math can only use POSITIVE rotations, negative rotations don't work.
    }
    public double getRotationDegrees() {
        return (m_dutyCycleEncoder.get() * 360) % 360;
    }
    // 1 is a full revolution. 0 is nothing.
    public double getOutputRaw() {
        return m_dutyCycleEncoder.get();
    }
    public double getTotalRotation() {
        return m_dutyCycleEncoder.getDistancePerRotation();
    }
    public void reset() {
        m_dutyCycleEncoder.reset();
    }


    // DEBUGGING AND SPECIFIC INFO FOR NERDS //
    public String toString() {
        String name = "[Encoder Port {" + m_dutyCycleEncoder.getSourceChannel() + "}]";
        return
            name + " isConnected: " + m_dutyCycleEncoder.isConnected() + "\n" +
            name + " rotation: " + m_dutyCycleEncoder.get() * 2*Math.PI + "\n" + 
            name + " rawValue: " + m_dutyCycleEncoder.get();
    }

    public DutyCycleEncoder getDutyCycleEncoder() { return m_dutyCycleEncoder; }
    public void setDutyCycleEncoder(DutyCycleEncoder newEncoder) { this.m_dutyCycleEncoder = newEncoder; }

 //   public double getVelocity(){
 //       return m_dutyCycleEncoder.
 //   }
}
