package frc.robot.command;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static java.lang.Math.acos;

public class MoveToPointDirectlyCommand extends SequentialCommandGroup {

    public MoveToPointDirectlyCommand(Translation2d from, Translation2d to) {
        double x1 = from.getX();
        double y1 = from.getY();
        double x2 = to.getX();
        double y2 = to.getY();

        double a = Math.abs(x1-x2);
        double b = Math.abs(y1-y2);
        double c = Math.sqrt((a*a)+(b*b));
        if (c != 0) {
            double angleB = Units.radiansToDegrees(acos(b / c));
            addCommands(
                    new RotateDegreesCommand(.05, angleB),
                    new MoveFeetForward(0.05, c)
            );
        }
    }
}


