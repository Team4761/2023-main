package frc.robot.Auto.command;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Auto.EncoderAuto.GoMetersBackwards;
import frc.robot.Auto.EncoderAuto.GoMetersEncoder;
import frc.robot.Auto.EncoderAuto.Balance;
import frc.robot.field.Field;
import frc.robot.main.Constants;

public class AutoCommandPos2 extends BaseAutoCommand {
    public AutoCommandPos2(){
        super(Field.STARTING_POSE_2, 2);

        var goalPosition = Field.CHARGING_STATION_OTHER_ON_TOP.getCenter().
                plus(new Translation2d(Constants.ROBOT_LENGTH / 2, 0));

        // from edge of charge station to center is around 104.7 cm accounting for angle
        addCommands(
            //new MoveToPointCommand(goalPosition)
            // 40 inches from edge to center of charge station + 60.69 from edge to charge - 16 inches half of robot
            new GoMetersBackwards(5.1), // about 2.15 meters to get to charge station
            new GoMetersEncoder(2.9-0.3)  // goes out of community first for mobility adjusted by .3 cause didn't balance
            ,new Balance()
        );
    }

    public static void main(String[] args) {
        var goalPosition = Field.CHARGING_STATION_OTHER_ON_TOP.getCenter().
            plus(new Translation2d(Constants.ROBOT_LENGTH / 2, 0));
        var startPose =         Field.STARTING_POSE_2;

        double distanceX = Units.inchesToMeters(Math.abs(goalPosition.getX() - startPose.getX()));
        System.out.println("DistanceX=" + distanceX);
        System.out.println("Other=" + (5.1-(2.9)));
    }
}
