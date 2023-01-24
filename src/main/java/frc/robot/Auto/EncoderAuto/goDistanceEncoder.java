package frc.robot.Auto.EncoderAuto;

import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.impl.placeholder.RobotMap.front_left_CANCoder;


public class goDistanceEncoder extends CommandBase {

    double distance;
    double distanceToGo;
    public goDistanceEncoder(double distance) {
        // each subsystem used by the command must be passed into the
        // addRequirements() method (which takes a vararg of Subsystem)\
        this.distance = distance;
        addRequirements();
    }

    @Override
    public void initialize() {
        distanceToGo = distance;
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
