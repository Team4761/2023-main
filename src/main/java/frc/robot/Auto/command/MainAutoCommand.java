package frc.robot.Auto.command;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.command.MoveArmAngles;
import frc.robot.command.MoveStraightMeasuredCommand;
import frc.robot.main.Constants;

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
            getCommand(autoSelector),
            new MoveArmAngles(Constants.NEUTRAL_POSITION)
        );
    }

    private CommandBase getCommand(int autoSelector)
    {
        if (!getAutoOnlyScoreMobility()) {
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
        return new MoveStraightMeasuredCommand(-1.0,4.0);
    }

    public boolean getAutoOnlyScoreMobility() {
        return SmartDashboard.getBoolean("autoSimple", true);
    }
}
