package frc.robot.arm;

import edu.wpi.first.wpilibj.DutyCycleEncoder;
import frc.robot.main.Constants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// RADIANS

public class AbsoluteEncoder extends SubsystemBase {
    private DutyCycleEncoder m_dutyCycleEncoder;
    // CONNECT USING the white, red, and black cable.
    // The WHITE cable is the signal wire.
    double offset;
    
    //1.72, 0.35, upper, bottom
    AbsoluteEncoder(int dioPORT, double offset) {
        this.offset = offset;
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
        return m_dutyCycleEncoder.getAbsolutePosition() * 2*Math.PI + offset;    // Math can only use POSITIVE rotations, negative rotations don't work.
    }
    public double getRotationDegrees() {
        return (m_dutyCycleEncoder.get() * 360 + offset*180/Math.PI) % 360;
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
    
    //private values to save info
    private double lastRotationValue = 0.0;
    private double lastVelocityValue = 0.0;
    
    //needs to run after all encoder code in perodic
    public void updateEncoder(){
        lastRotationValue = getRotation();
        lastVelocityValue = getVelocity();
    }
    public double getVelocity(){
        return (lastRotationValue - getRotation()) / 0.020;
    }
    public double getAcceleration(){
        return (lastVelocityValue - getVelocity()) / 0.020;
    }
}
