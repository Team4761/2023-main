package frc.robot.impl.terry;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class CanSparkMaxRoboMap {

    public static CANSparkMax left = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
    public static CANSparkMax right = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);

    public static DifferentialDrive m_drive = new DifferentialDrive(left,right);

    public static DifferentialDrive getDrive() {
        return m_drive;
    }
}
