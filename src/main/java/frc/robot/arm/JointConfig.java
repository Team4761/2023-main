package frc.robot.arm;

import edu.wpi.first.math.system.plant.DCMotor;

public class JointConfig{
    double mass;
    double length;
    double moi;
    double cgRadius;
    //should be different motor type??
    DCMotor motor;
    public JointConfig(double mass, double length, double moi, double cgRadius, DCMotor motor) {
        this.mass = mass;
        this.length = length;
        this.moi = moi;
        this.cgRadius = cgRadius;
        this.motor = motor;
    }
}
