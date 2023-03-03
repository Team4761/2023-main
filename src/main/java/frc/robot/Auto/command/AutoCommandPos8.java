package frc.robot.Auto.command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Auto.EncoderAuto.TurnToGyro;
import frc.robot.field.Field;
import frc.robot.main.Robot;

public class AutoCommandPos8 extends SequentialCommandGroup {
    private static final double PAST_ITEM = 12; // inches

    public AutoCommandPos8(){
        var startPose = Field.STARTING_POSE_8;
        var item = Field.ItemInlineWithZone8;
        var goalPosition = Field.ZONE_8.bottomShelfMid.getCenterRight();

        // TODO: implement a simple pose on the robot as a setter/getter
        Robot.impl.setPose(startPose);

        // TODO: create each of these commands and make sure they make sense for position 6
        addCommands(
                new ScoreDirectlyInFront(),
                new MoveToPointCommand(item.getX() + PAST_ITEM, startPose.getY()),
                new TurnToGyro(-90),
                new MoveToPointCommand(item.getX(), item.getY() - Robot.impl.getLength() / 2.0),
                new TurnToGyro(0),
                //  new MoveToPointCommand(item.getX(), item.getY())
                // new PickUpItem()
                new MoveToPointCommand(goalPosition)
        );

    }
}
