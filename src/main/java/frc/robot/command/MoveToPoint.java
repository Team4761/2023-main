package frc.robot.command;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static java.lang.Math.sin;
import static java.lang.Math.asin;
import static java.lang.Math.sqrt;
import static java.lang.Math.acos;

public class MoveToPoint extends SequentialCommandGroup {

    public double AbsVal(double n) {
        if (n<0) {
            n *= -1;
            return n;
        }
        else
            return n;
    }

    public Command PointA_To_PointB (double x1, double y1, double x2, double y2){
        double a= AbsVal(x1-x2);
        double b= AbsVal(y1-y2);
        double c= Math.sqrt((a*a)+(b*b));
        double angleA= asin(a/c);
        double angleB=acos(b/c);
        angleA*=100;
        angleB*=100;
        Math.round(angleA);
        Math.round(angleB);
        angleA/=100;
        angleB/=100;

        return new SequentialCommandGroup(
            new RotateDegreesCommand(.05,angleA),
            new MoveFeetForward(0.05, c)
        );
    }
}


