package frc.robot.intake;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
    
    private final static IntakeSubsystem INSTANCE = new IntakeSubsystem();

    private CANSparkMax intakeMotor = new CANSparkMax(4, MotorType.kBrushless);

    public IntakeSubsystem () {
    }
    @Override
    public void periodic() {
        //intakeMotor.set(0.15);
    }

    public void setSpeed (double speed) {
        intakeMotor.set(speed);
    }
    public static IntakeSubsystem getInstance() {
        return INSTANCE;
    }
}
