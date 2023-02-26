package frc.robot.command;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.intake.IntakeSubsystem;

public class InTakeCommand extends CommandBase {
    private IntakeSubsystem m_intake;
    private Timer timer = new Timer();

    public InTakeCommand(IntakeSubsystem intake) {
        m_intake = intake;
        addRequirements(m_intake);
    }
    @Override
    public void initialize() {
        timer.start();
    }
    @Override
    public void execute() {
        m_intake.setSpeed(-1+timer.get()/4);
    }
    @Override
    public boolean isFinished() {
        return timer.get()>3;
    }
    public void end() {
        m_intake.setSpeed(0);
    }
}
