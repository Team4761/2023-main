package frc.robot.Auto.command;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Auto.EncoderAuto.TurnDegreesGyro;
import frc.robot.command.InTakeCommand;
import frc.robot.command.MoveArmAngles;
import frc.robot.command.MoveStraightMeasuredCommand;
import frc.robot.main.Constants;
import frc.robot.field.Field;
import frc.robot.intake.IntakeSubsystem;
import frc.robot.main.Robot;

public class AutoCommandPos1 extends SequentialCommandGroup implements StartPoseProvider {
    public static final double PAST_ITEM = 18; // inches

    public AutoCommandPos1(){
        var startPose = getStartPose();
        var item = Field.ItemInlineWithZone1;
        var goalPosition = Field.ZONE_1.bottomShelfMid.getCenterRight();

        Robot.impl.setPose(startPose);
        System.out.println("Running auto 1");

        addCommands(
            new MoveToPointCommand(item.getX() + PAST_ITEM, startPose.getY()),
            new TurnDegreesGyro(180),
            new MoveArmAngles(Constants.INTAKE_POSITION),
            new ParallelCommandGroup(
                new InTakeCommand(IntakeSubsystem.getInstance(), 3),
                new MoveStraightMeasuredCommand(0.4, 1)
            )
        );
    }

    @Override
    public Pose2d getStartPose() {
        return Field.STARTING_POSE_1;
    }
}
