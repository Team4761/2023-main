package frc.robot.Auto.command;

import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.field.Field;
import frc.robot.main.Constants;

public class AutoCommandPos7 extends BaseAutoCommand {
    public AutoCommandPos7(){
        super(Field.STARTING_POSE_7, 7);

        var goalPosition = Field.CHARGING_STATION_ON_TOP.getCenter().
                minus(new Translation2d(Constants.ROBOT_LENGTH / 2, 0));

        addCommands(
            new MoveToPointCommand(goalPosition)
        );
    }
}
