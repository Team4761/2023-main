package frc.robot.Auto.command;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Vision.visionVarsAndMethods;
import frc.robot.command.MoveArmAngles;
import frc.robot.command.MoveStraightMeasuredCommand;
import frc.robot.main.Constants;
import frc.robot.main.Robot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MainAutoCommand extends SequentialCommandGroup {
    public static List<BaseAutoCommand> AUTO_COMMANDS =
        Arrays.asList(
            new AutoCommandPos1(), new AutoCommandPos2(), new AutoCommandPos3(),
            new AutoCommandPos6(), new AutoCommandPos7(), new AutoCommandPos8()
        );

    public MainAutoCommand(){
        addCommands(
            new ScoreDirectlyInFront(),
            new MoveArmAngles(Constants.NEUTRAL_POSITION),
            getCommand()
        );
    }

    private CommandBase getCommand()
    {
        if (!getAutoOnlyScoreMobility()) {
            var pose = visionVarsAndMethods.getEstimatedPose().getFirst();
            if (pose.getX() != 0 && pose.getY() != 0) {
                return closestPoint(pose.getX(), pose.getY());
            }
            var startPos = Robot.m_shuffleboard.getStartPos();
            for (BaseAutoCommand command : AUTO_COMMANDS) {
                if (command.getAprilTag() == startPos) {
                    return command;
                }
            }
            return new AutoCommandPos1();
        }
        // The goal of this is to achieve mobility ... just haul backward 4.0 meters and stay put
        var topSpeed = Robot.m_shuffleboard.getAutoMaxSpeed();
        return new MoveStraightMeasuredCommand(-topSpeed,4.0);
    }

    private CommandBase closestPoint(double x, double y) {
        Translation2d pos = new Translation2d(x, y);
        var commands = new ArrayList<>(AUTO_COMMANDS);
        commands.sort(Comparator.comparingDouble(c -> Math.abs(pos.getDistance(c.getStartPose().getTranslation()))));
        BaseAutoCommand selectedCommand = commands.get(0);
        SmartDashboard.putNumber("Closest April Tag", selectedCommand.getAprilTag());
        return selectedCommand;
    }

    public boolean getAutoOnlyScoreMobility() {
        return SmartDashboard.getBoolean("autoSimple", false);
    }
}
