package frc.robot.Auto.EncoderAuto;

import edu.wpi.first.wpilibj2.command.CommandBase;


public class goDistanceEncoder extends CommandBase {

    double distance;
    public goDistanceEncoder(double distance) {
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)\
        this.distance = distance;
        addRequirements();
    }

    @Override
    public void initialize() {
        //encoder magic lol
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }

    @Override
    public void end(boolean interrupted) {

    }
}
