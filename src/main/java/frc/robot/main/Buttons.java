package frc.robot.main;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Auto.tagAuto.moveToPointCommands.goToPoseAprilTag;

public class Buttons {

    XboxController xboxController = new XboxController(1);
    JoystickButton button_1 = new JoystickButton(xboxController,1);



    public Buttons(){
        button_1.onTrue(new goToPoseAprilTag());
    }


}
