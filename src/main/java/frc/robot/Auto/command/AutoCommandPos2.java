package frc.robot.Auto.command;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Auto.EncoderAuto.GoMetersBackwards;
import frc.robot.Auto.EncoderAuto.GoMetersEncoder;
import frc.robot.Auto.EncoderAuto.Balance;
import frc.robot.field.Field;
import frc.robot.main.Constants;
import frc.robot.main.Robot;

public class AutoCommandPos2 extends SequentialCommandGroup {

    public AutoCommandPos2(){
        var startPose = Field.STARTING_POSE_2;

        var goalPosition = Field.CHARGING_STATION_OTHER_ON_TOP.getCenter().
                plus(new Translation2d(Constants.ROBOT_LENGTH / 2, 0));

        Robot.impl.setPose(startPose);
        
        // from edge of charge station to center is around 104.7 cm accounting for angle
        addCommands(
            //new MoveToPointCommand(goalPosition)
            // 40 inches from edge to center of charge station + 60.69 from edge to charge - 16 inches half of robot
            new GoMetersBackwards(5.1), // about 2.15 meters to get to charge station
            new GoMetersEncoder(2.95)  // goes out of community first for mobility
            ,new Balance()
        );
    }
}
