package frc.robot.arm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// RADIANS

public class ArmSubsystem extends SubsystemBase {
    AbsoluteEncoder lowEncoder;
    AbsoluteEncoder highEncoder;
    CANSparkMax bottom_left;
    CANSparkMax bottom_right;
    CANSparkMax top;


    // CONNECT USING the white, red, and black cable.
    // The WHITE cable is the signal wire.

    public ArmSubsystem()
    {
        lowEncoder = new AbsoluteEncoder(0);
        highEncoder = new AbsoluteEncoder(9);
        bottom_left = new CANSparkMax(15, CANSparkMaxLowLevel.MotorType.kBrushless);
        bottom_right = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);
        bottom_right.setInverted(true);
        top = new CANSparkMax(12, CANSparkMaxLowLevel.MotorType.kBrushless);
    }


    // Getters and Setters
    public void setBottom(double speed) {
        bottom_left.set(speed);
        bottom_right.set(speed);
    }
    public void stopBottom() {
        bottom_left.stopMotor();
        bottom_right.stopMotor();
    }

    public boolean exist() {
        return true;
    }
    public double getBottomSpeed() {
        return bottom_left.get();
    }
    public double getTopRotation() {
        return highEncoder.getRotation();
    }
    public double getBottomRotation() {
        return lowEncoder.getRotation();
    }
}