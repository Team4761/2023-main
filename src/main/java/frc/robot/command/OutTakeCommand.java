package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.intake.IntakeSubsystem;

public class OutTakeCommand extends CommandBase {
    private IntakeSubsystem m_intake;

    public OutTakeCommand(IntakeSubsystem intake) {
        m_intake = intake;
        addRequirements(m_intake);
    }
    @Override
    public void initialize() {
        
    }
    @Override
    public void execute() {
        m_intake.setSpeed(0.9);
    }
    @Override
    public boolean isFinished() {
        return true;
    }
    
    public void end() {
        m_intake.setSpeed(0);
    }
}
