package frc.robot.Auto.command;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Auto.EncoderAuto.GoMetersBackwards;
import frc.robot.command.MoveArmAngles;
import frc.robot.Vision.visionVarsAndMethods;
import frc.robot.command.MoveStraightMeasuredCommand;

import java.util.Arrays;
import java.util.Comparator;
import frc.robot.main.Constants;
import frc.robot.main.Robot;

public class MainAutoCommand extends SequentialCommandGroup {
    public static final int POS_1 = 1;
    public static final int POS_2 = 2;
    public static final int POS_3 = 3;
    public static final int POS_6 = 6;
    public static final int POS_7 = 7;
    public static final int POS_8 = 8;

    public MainAutoCommand(int autoSelector){
        addCommands(
            new ScoreDirectlyInFront(),
            new MoveArmAngles(Constants.NEUTRAL_POSITION),
            getCommand(autoSelector)
            //new GoMetersBackwards(-3),

        );
    }

    private CommandBase getCommand(int autoSelector)
    {
        if (!getAutoOnlyScoreMobility()) {
            var pose = visionVarsAndMethods.getEstimatedPose().getFirst();
            if (pose.getX() != 0 && pose.getY() != 0) {
                return closestPoint(pose.getX(), pose.getY());
            }
            switch (autoSelector) {
                case POS_1: return new AutoCommandPos1();
                case POS_2: return new AutoCommandPos2();
                case POS_3: return new AutoCommandPos3();

                case POS_6: return new AutoCommandPos6();
                case POS_7: return new AutoCommandPos7();
                case POS_8: return new AutoCommandPos8();
            }
        }
        // The goal of this is to achieve mobility ... just haul backward 4.0 meters and stay put
        var topSpeed = Robot.m_shuffleboard.getAutoMaxSpeed();
        return new MoveStraightMeasuredCommand(-topSpeed,4.0);
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

    public boolean getAutoOnlyScoreMobility() {
        return SmartDashboard.getBoolean("autoSimple", false);
    }
}
