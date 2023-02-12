package frc.robot.command;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.impl.placeholder.Placeholder;
import frc.robot.main.Robot;

import java.util.Collections;
import java.util.Set;

public class XboxArcadeDrive extends XboxDriveBase {

    public XboxArcadeDrive() {
        xbox.a().onTrue(this::onAButton);
    }

    private Set<Subsystem> onAButton() {
        Placeholder.doLogging = !Placeholder.doLogging;
        return Collections.emptySet();
    }

    @Override
    public void execute() {
        Robot.driveTrain.arcadeDrive(xbox.getLeftY() / 2, -xbox.getRightX() / 2) ;
    }

    @Override
    public void end(boolean interrupted) {
        Robot.driveTrain.arcadeDrive(0,0);
    }
}
