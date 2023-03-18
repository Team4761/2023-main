package frc.robot.Auto.command;

import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.field.Field;
import frc.robot.main.Constants;

public class AutoCommandPos2 extends BaseAutoCommand {
    public AutoCommandPos2(){
        super(Field.STARTING_POSE_2, 2);

        var goalPosition = Field.CHARGING_STATION_OTHER_ON_TOP.getCenter().
                plus(new Translation2d(Constants.ROBOT_LENGTH / 2, 0));

        // from edge of charge station to center is around 104.7 cm accounting for angle
        addCommands(
            new MoveToPointCommand(goalPosition)
        );
    }
}
