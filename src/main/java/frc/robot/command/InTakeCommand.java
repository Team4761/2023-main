package frc.robot.command;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.intake.IntakeSubsystem;

public class InTakeCommand extends CommandBase {
    private IntakeSubsystem m_intake;
    private Timer timer = new Timer();
    double t;
    public InTakeCommand(IntakeSubsystem intake, double time) {
        m_intake = intake;
        t=time;
        addRequirements(m_intake);
    }
    @Override
    public void initialize() {
        timer.reset();
        timer.start();
    }
    @Override
    public void execute() {
        m_intake.setSpeed(1-timer.get()/4);
    }
    @Override
    public boolean isFinished() {
        return timer.get()>t;
    }
    @Override
    public void end(boolean interrupted) {
        m_intake.setSpeed(0);
        this.cancel();
    }
}
