package frc.robot.command;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.intake.IntakeSubsystem;

public class OutTakeCommand extends CommandBase {
    private IntakeSubsystem m_intake;
    private Timer timer = new Timer();
    private double seconds;

    public OutTakeCommand(IntakeSubsystem intake, double s) {
        m_intake = intake;
        seconds = s;
        addRequirements(m_intake);
    }
    @Override
    public void initialize() {
        timer.reset();
        timer.start();
    }
    @Override
    public void execute() {
        m_intake.setSpeed(-0.1);
    }
    @Override
    public boolean isFinished() {
        return timer.get()>seconds;
    }
    @Override
    public void end(boolean interrupted) {
        m_intake.setSpeed(0.15);
    }
}
