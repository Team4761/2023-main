package frc.robot.Auto.command;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Vision.visionVarsAndMethods;
import frc.robot.field.Field;

import java.util.Arrays;
import java.util.Comparator;

public class MainAutoCommand extends SequentialCommandGroup {

    public static final String POS_1= "1";
    public static final String POS_2= "2";
    public static final String POS_3 = "3";
    public static final String POS_6 = "6";
    public static final String POS_7 = "7";
    public static final String POS_8 = "8";



    public MainAutoCommand(String autoSelector){
        addCommands(getCommand(autoSelector));
    }

    private CommandBase getCommand(String autoSelector)
    {
        var pose = visionVarsAndMethods.getEstimatedPose().getFirst();
        if (pose.getX() != 0 && pose.getY() == 0) {
            return closestPoint(pose.getX(), pose.getY());
        }
        switch(autoSelector)
        {
            case POS_1: return new AutoCommandPos1();
            case POS_2: return new AutoCommandPos2();
            case POS_3: return new AutoCommandPos3();

            case POS_6: return new AutoCommandPos6();
            case POS_7: return new AutoCommandPos7();
            case POS_8: return new AutoCommandPos8();

        }
        return new AutoCommandPos6();
    }

    private CommandBase closestPoint(double x, double y) {
        Translation2d pos = new Translation2d(x, y);
        var commands =
            Arrays.asList(
                new AutoCommandPos1(), new AutoCommandPos2(), new AutoCommandPos3(),
                new AutoCommandPos6(), new AutoCommandPos7(), new AutoCommandPos8()
            );
        commands.sort(Comparator.comparingDouble(c -> Math.abs(pos.getDistance(c.getStartPose().getTranslation()))));
        return commands.get(0);
    }
}
