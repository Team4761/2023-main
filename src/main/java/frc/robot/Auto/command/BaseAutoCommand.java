package frc.robot.Auto.command;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.command.InTakeCommand;
import frc.robot.command.MoveArmAngles;
import frc.robot.command.MoveStraightMeasuredCommand;
import frc.robot.intake.IntakeSubsystem;
import frc.robot.main.Constants;
import frc.robot.main.Robot;

public class BaseAutoCommand extends SequentialCommandGroup implements StartPoseProvider {
    private Pose2d startPose;
    private int aprilTag;

    public BaseAutoCommand(Pose2d startPose, int aprilTag) {
        Robot.impl.setPose(startPose);
//        addCommands(Commands.runOnce(this::setPose));
        this.startPose = startPose;
        this.aprilTag = aprilTag;
    }

    private void setPose() {
        Robot.impl.setPose(startPose);
    }

    @Override
    public Pose2d getStartPose() {
        return startPose;
    }

    public int getAprilTag() {
        return aprilTag;
    }

    protected void addAfterSideCommands() {
        addCommands(
            new MoveArmAngles(Constants.INTAKE_POSITION),
            new ParallelCommandGroup(
                new InTakeCommand(IntakeSubsystem.getInstance(), 3),
                new MoveStraightMeasuredCommand(0.4, .5)
            )
        );
    }
}
