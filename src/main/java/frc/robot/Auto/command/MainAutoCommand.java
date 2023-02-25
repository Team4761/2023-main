package frc.robot.Auto.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class MainAutoCommand extends SequentialCommandGroup {

    public static final String POS_1= "1";
    public static final String POS_2= "2";
    public static final String POS_3 = "3";
    public static final String POS_6 = "6";
    public static final String POS_7 = "7";
    public static final String POS_8 = "8";



    public MainAutoCommand(String autoSelector){
        super(getCommand(autoSelector));
    }

    private static CommandBase getCommand(String autoSelector)
    {
        switch(autoSelector)
        {
            case POS_1: return new AutoCommandPos1();
            case POS_2: return new AutoCommandPos2();
            case POS_3: return new AutoCommandPos3();

            case POS_6: return new AutoCommandPos6();
            case POS_7: return new AutoCommandPos7();
            case POS_8: return new AutoCommandPos8();

        }
        return new AutoCommandPos6();
    }
}
