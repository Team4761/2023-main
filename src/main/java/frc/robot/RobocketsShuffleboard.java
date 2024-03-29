package frc.robot;

import java.util.Map;
import java.util.Optional;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.arm.ArmSubsystem;
import frc.robot.leds.LEDSubsystem;
import frc.robot.leds.UpdateLED;
import frc.robot.main.Constants;
import frc.robot.main.Robot;

public class RobocketsShuffleboard {

    ShuffleboardTab arms_tab;
    ShuffleboardTab drive_tab;
    ShuffleboardTab auto_tab;
    ShuffleboardTab led_tab;

    public RobocketsShuffleboard() {
        init();
    }
    
    public void init() {
        drive_tab = Shuffleboard.getTab("Drive Train");
        auto_tab = Shuffleboard.getTab("Auto");
        led_tab = Shuffleboard.getTab("LEDs");
        
        initDrive();
        initAuto();
        initArms();
        initLEDs();
    }


    GenericEntry p_bottom;
    GenericEntry i_bottom;
    GenericEntry d_bottom;
    GenericEntry p_top;
    GenericEntry i_top;
    GenericEntry d_top;
    GenericEntry top_desired;
    GenericEntry bottom_desired;
    GenericEntry bottom_left_speed;
    GenericEntry bottom_right_speed;
    GenericEntry top_left_speed;
    GenericEntry top_right_speed;
    GenericEntry changingPIDEntry;
    GenericEntry manualControlArms;
    GenericEntry armsBoundsChecker;
    GenericEntry useFeedForward;
    GenericEntry joystickPortArm;
    GenericEntry top_rotation;
    GenericEntry top_speed;
    GenericEntry bottom_rotation;
    GenericEntry bottom_speed;
    GenericEntry manual_top_arm_speed;
    GenericEntry manual_bottom_arm_speed;
    GenericEntry top_ff;
    GenericEntry bottom_ff;

    public void initArms() {
        arms_tab = Shuffleboard.getTab("Arms");
        ShuffleboardLayout PID_lower = arms_tab.getLayout("Bottom",BuiltInLayouts.kList).withSize(2, 6);
        ShuffleboardLayout PID_top = arms_tab.getLayout("Top",BuiltInLayouts.kList).withSize(2, 6);
        ShuffleboardLayout settings = arms_tab.getLayout("Settings",BuiltInLayouts.kList).withSize(2, 6);

        p_bottom = PID_lower.add("P_bottom", Constants.ARM_P_BOTTOM).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 3)).getEntry();
        i_bottom = PID_lower.add("I_bottom", Constants.ARM_I_BOTTOM).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 3)).getEntry();
        d_bottom = PID_lower.add("D_bottom", Constants.ARM_D_BOTTOM).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 3)).getEntry();
        bottom_desired = PID_lower.add("Desired Bottom Rotation", Constants.ARM_D_TOP).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 3.14)).getEntry();
        bottom_left_speed = PID_lower.add("Bottom Left Speed",ArmSubsystem.getInstance().getBottomSpeedL()).getEntry();;
        bottom_right_speed = PID_lower.add("Bottom Right Speed",ArmSubsystem.getInstance().getBottomSpeedR()).getEntry();
        bottom_ff = PID_lower.add("Feedforward", ArmSubsystem.getInstance().getBottomPID().ff).getEntry();
        bottom_rotation = PID_lower.add("Bottom Rotation", ArmSubsystem.getInstance().getDesiredBottomRotation()).getEntry();
        bottom_speed = PID_lower.add("Bottom Speed", ArmSubsystem.getInstance().getDesiredBottomRotation()).getEntry();

        p_top = PID_top.add("P_top", Constants.ARM_P_TOP).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 3)).getEntry();
        i_top = PID_top.add("I_top", Constants.ARM_I_TOP).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 3)).getEntry();
        d_top = PID_top.add("D_top", Constants.ARM_D_TOP).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 3)).getEntry();
        top_desired = PID_top.add("Desired Top Rotation", Constants.ARM_D_TOP).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 3.14)).getEntry();
        top_left_speed = PID_top.add("Top Left Speed",ArmSubsystem.getInstance().getTopSpeedL()).getEntry();
        top_right_speed = PID_top.add("Top Right Speed",ArmSubsystem.getInstance().getTopSpeedR()).getEntry();
        top_ff = PID_top.add("Feedforward", ArmSubsystem.getInstance().getTopPID().ff).getEntry();
        top_rotation = PID_top.add("Top Rotation", ArmSubsystem.getInstance().getDesiredTopRotation()).getEntry();
        top_speed = PID_top.add("Top Speed", ArmSubsystem.getInstance().getDesiredTopRotation()).getEntry();

        manual_top_arm_speed = settings.add("Manual Top Speed", .2).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", .1, "max", 1.0)).getEntry();
        manual_bottom_arm_speed = settings.add("Manual Bottom Speed", .15).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", .1, "max", 1.0)).getEntry();

        changingPIDEntry = settings.add("Is Updating PID", true).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
        manualControlArms = settings.add("Using Manual Control", true).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
        armsBoundsChecker = settings.add("Arms Bound Checker", true).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
        useFeedForward = settings.add("Using Feed Forward", false).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
        joystickPortArm = settings.add("Joystick Port", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 10, "block increment", 1)).getEntry();
    }

    public void updateArms() {
        arms_tab = Shuffleboard.getTab("Arms");

        if (changingPIDEntry.getBoolean(false)) {
            if (Constants.ARM_P_BOTTOM != p_bottom.getDouble(Constants.ARM_P_BOTTOM) || Constants.ARM_I_BOTTOM != i_bottom.getDouble(Constants.ARM_I_BOTTOM) || Constants.ARM_D_BOTTOM != d_bottom.getDouble(Constants.ARM_D_BOTTOM))
                //ArmSubsystem.getInstance().getBottomPID().updatePIDValues(p_bottom.getDouble(Constants.ARM_P_BOTTOM), i_bottom.getDouble(Constants.ARM_I_BOTTOM), d_bottom.getDouble(Constants.ARM_D_BOTTOM));
            if (Constants.ARM_P_TOP != p_top.getDouble(Constants.ARM_P_TOP) || Constants.ARM_I_TOP != i_top.getDouble(Constants.ARM_I_TOP) || Constants.ARM_D_TOP != d_top.getDouble(Constants.ARM_D_TOP))
                //ArmSubsystem.getInstance().getTopPID().updatePIDValues(p_top.getDouble(Constants.ARM_P_TOP), i_top.getDouble(Constants.ARM_I_TOP), d_top.getDouble(Constants.ARM_D_TOP));
            Constants.ARM_P_BOTTOM = p_bottom.getDouble(Constants.ARM_P_BOTTOM);
            Constants.ARM_I_BOTTOM = i_bottom.getDouble(Constants.ARM_I_BOTTOM);
            Constants.ARM_D_BOTTOM = d_bottom.getDouble(Constants.ARM_D_BOTTOM);
            Constants.ARM_P_TOP = p_top.getDouble(Constants.ARM_P_TOP);
            Constants.ARM_I_TOP = i_top.getDouble(Constants.ARM_I_TOP);
            Constants.ARM_D_TOP = d_top.getDouble(Constants.ARM_D_TOP);
            //feedforward_bottom.setDouble(ArmSubsystem.getInstance().getBottomPID().ff);
            //feedforward_top.setDouble(ArmSubsystem.getInstance().getTopPID().ff);
            ArmSubsystem.getInstance().useFeedForward = useFeedForward.getBoolean(false);
        }

        top_desired.setDouble(ArmSubsystem.getInstance().getDesiredTopRotation());
        top_rotation.setDouble(ArmSubsystem.getInstance().getTopRotation());
        top_speed.setDouble(ArmSubsystem.getInstance().getTopSpeed());

        bottom_desired.setDouble(ArmSubsystem.getInstance().getDesiredBottomRotation());
        bottom_rotation.setDouble(ArmSubsystem.getInstance().getBottomRotation());
        bottom_speed.setDouble(ArmSubsystem.getInstance().getBottomSpeed());

        if (Robot.armControl.port != (int)joystickPortArm.getInteger(0))
            Robot.armControl.reinitController((int)joystickPortArm.getInteger(0));
    }

    public void setTopFF(double ff) {
        top_ff.setDouble(ff);
    }
    public void setBottomFF(double ff) {
        bottom_ff.setDouble(ff);
    }




    GenericEntry manualControlDrive;
    GenericEntry driveSpeed;
    GenericEntry rotationSpeed;
    GenericEntry joystickPortDrive;
    public void initDrive() {
        ShuffleboardLayout settings = drive_tab.getLayout("Settings",BuiltInLayouts.kList).withSize(2, 6);
        
        manualControlDrive = settings.add("Using Manual Control", true).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
        driveSpeed = settings.add("Speed", Constants.DRIVETRAIN_SPEED).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 12)).getEntry();
        rotationSpeed = settings.add("Rotation Speed", Constants.DRIVETRAIN_ROTATION_SPEED).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1)).getEntry();
        
        joystickPortDrive = settings.add("Joystick Port", 1).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 10, "block increment", 1)).getEntry();
    }

    public void updateDrive() {
        // commented cause keeps resetting and i don't know where to set default
       // Constants.DRIVETRAIN_SPEED = driveSpeed.getDouble(Constants.DRIVETRAIN_SPEED);
        //Constants.DRIVETRAIN_ROTATION_SPEED = driveSpeed.getDouble(Constants.DRIVETRAIN_ROTATION_SPEED);
        //if (Robot.driveController.port != (int)joystickPortArm.getInteger(1))
        //    Robot.driveController.reinitController((int)joystickPortArm.getInteger(1));
    }




    GenericEntry alliance;
    GenericEntry allianceColor;
    GenericEntry autoSpeed;
    SendableChooser<Integer> startingPos;
    public void initAuto() {
        ShuffleboardLayout settings = auto_tab.getLayout("Settings",BuiltInLayouts.kList).withSize(2, 6);

        alliance = settings.addPersistent("Alliance", false).withWidget(BuiltInWidgets.kToggleSwitch).withProperties(Map.of("min", 0, "max", 1)).getEntry();
        allianceColor = settings.addPersistent("Alliance Color",false).withWidget(BuiltInWidgets.kBooleanBox).withProperties(Map.of("color when true", 1717145)).getEntry();
        autoSpeed = settings.addPersistent("Max Speed", 0.8).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min",0,"max",1)).getEntry();

        startingPos = new SendableChooser<>();
        startingPos.setDefaultOption(Integer.toString(1), 1);
        for (int i = 2; i <= 3; ++i) {
            startingPos.addOption(Integer.toString(i), i);
        }
        for (int i = 6; i <= 8; ++i) {
            startingPos.addOption(Integer.toString(i), i);
        }
        settings.add("Starting Position (1 is red, 6 is blue)", startingPos);
    }

    public void updateAuto() {
        if (allianceColor.getBoolean(false) != alliance.getBoolean(false)) {
            allianceColor.setBoolean(alliance.getBoolean(false));
        }
    }


    
    GenericEntry isTextDisplay;
    GenericEntry isForcingText;
    GenericEntry isRepeatingLED;
    GenericEntry isInversedLEDs;
    GenericEntry isStaticDisplay;
    GenericEntry coneText;
    GenericEntry cubeText;
    GenericEntry forceDisplayText;
    GenericEntry ledSpeed;
    public void initLEDs() {
        ShuffleboardLayout presets = led_tab.getLayout("Presets",BuiltInLayouts.kList).withSize(2,6);
        ShuffleboardLayout settings = led_tab.getLayout("Settings", BuiltInLayouts.kList).withSize(2,6);

        isTextDisplay = settings.add("Displaying Text", true).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
        isForcingText = settings.add("Display Force Text", false).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
        isRepeatingLED = settings.add("Text Repeating", true).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
        isInversedLEDs = settings.add("Inverse LEDs", false).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
        isStaticDisplay = settings.add("Static LEDs", false).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
        ledSpeed = settings.add("Text Speed", 100).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min",20,"max",2000)).getEntry();

        coneText = presets.add("Text For Cone", "%(200,255,0)CONE ").withWidget(BuiltInWidgets.kTextView).getEntry();
        cubeText = presets.add("Text For Cube", "%(5,0,10)CUBE ").withWidget(BuiltInWidgets.kTextView).getEntry();
        forceDisplayText = presets.add("Forced Text", "%(255,0,0)4761 ").withWidget(BuiltInWidgets.kTextView).getEntry();
    }

    public void updateLEDs() {
        if (isForcingText.getBoolean(false)) {
            UpdateLED.setText(forceDisplayText.getString("UHHH"));
            isForcingText.setBoolean(false);
        }
        if (UpdateLED.textSpeed != ledSpeed.getInteger(100)) {
            UpdateLED.textSpeed = (int)ledSpeed.getInteger(100);
        }
        if (UpdateLED.textRepeating != isRepeatingLED.getBoolean(true)) {
            UpdateLED.textRepeating = isRepeatingLED.getBoolean(true);
        }
        if (LEDSubsystem.textInverted != isInversedLEDs.getBoolean(false)) {
            LEDSubsystem.textInverted = isInversedLEDs.getBoolean(false);
        }
        
        UpdateLED.isStatic = isStaticDisplay.getBoolean(false);

        if (isTextDisplay.getBoolean(true)) {
            UpdateLED.currentState = UpdateLED.TEXT_DISPLAY;
        } else {
            UpdateLED.currentState = UpdateLED.SOLID_COLOR;
        }
    }



    /* GETTERS */
    public boolean usingManualArms() { return manualControlArms.getBoolean(true); }
    public boolean usingManualDrive() { return manualControlDrive.getBoolean(true); }
    public boolean armsBoundChecker() { return armsBoundsChecker.getBoolean(true); }
    public boolean getAlliance() { return alliance.getBoolean(true); }
    public int getStartPos() { return Optional.of(startingPos.getSelected()).orElse(1); }
    public double getManualTopArmSpeed() { return manual_top_arm_speed.getDouble(.2); }
    public double getManualBottomArmSpeed() { return manual_bottom_arm_speed.getDouble(.15); }
    public double getAutoMaxSpeed() { return autoSpeed.getDouble(0.8); }
    public boolean usingTextDisplay() { return isTextDisplay.getBoolean(true); }
}
