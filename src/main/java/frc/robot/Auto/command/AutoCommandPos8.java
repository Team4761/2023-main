package frc.robot.Auto.command;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Auto.EncoderAuto.TurnToGyro;
import frc.robot.field.Field;
import frc.robot.main.Robot;

import static frc.robot.Auto.command.AutoCommandPos1.PAST_ITEM;

public class AutoCommandPos8 extends SequentialCommandGroup implements StartPoseProvider  {
    public AutoCommandPos8(){
        var startPose = getStartPose();
        var item = Field.ItemInlineWithZone8;
        var goalPosition = Field.ZONE_8.bottomShelfMid.getCenterRight();

        Robot.impl.setPose(startPose);
        addCommands(
            new MoveToPointCommand(item.getX() + PAST_ITEM, startPose.getY()),
            new TurnToGyro(-90),
            new MoveToPointCommand(item.getX(), item.getY() - Robot.impl.getLength() / 2.0),
            new TurnToGyro(0),
            new MoveToPointCommand(goalPosition)
        );

    }

    @Override
    public Pose2d getStartPose() {
        return Field.STARTING_POSE_8;
    }
}
