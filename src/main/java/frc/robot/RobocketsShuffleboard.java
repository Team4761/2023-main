package frc.robot;

import java.util.Map;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.LayoutType;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.arm.ArmSubsystem;
import frc.robot.main.Constants;

public class RobocketsShuffleboard {

    ShuffleboardTab arms_tab;
    ShuffleboardTab drive_tab;
    ShuffleboardTab auto_tab;

    public RobocketsShuffleboard() {
        init();
    }
    
    public void init() {
        arms_tab = Shuffleboard.getTab("Arms");
        drive_tab = Shuffleboard.getTab("Drive Train");
        auto_tab = Shuffleboard.getTab("Auto");
        initArms();
        initDrive();
        initAuto();
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
    public void initArms() {
        ShuffleboardLayout PID_lower = arms_tab.getLayout("Bottom",BuiltInLayouts.kList).withSize(2, 6);
        ShuffleboardLayout PID_top = arms_tab.getLayout("Top",BuiltInLayouts.kList).withSize(2, 6);
        ShuffleboardLayout settings = arms_tab.getLayout("Settings",BuiltInLayouts.kList).withSize(2, 6);

        p_bottom = PID_lower.add("P_bottom", Constants.ARM_P_BOTTOM).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 3)).getEntry();
        i_bottom = PID_lower.add("I_bottom", Constants.ARM_I_BOTTOM).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 3)).getEntry();
        d_bottom = PID_lower.add("D_bottom", Constants.ARM_D_BOTTOM).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 3)).getEntry();
        bottom_desired = PID_lower.add("Desired Bottom Rotation", Constants.ARM_D_TOP).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 3.14)).getEntry();
        bottom_left_speed = PID_lower.add("Bottom Left Speed",ArmSubsystem.getInstance().getBottomSpeedL()).getEntry();;
        bottom_right_speed = PID_lower.add("Bottom Right Speed",ArmSubsystem.getInstance().getBottomSpeedR()).getEntry();
        
        p_top = PID_top.add("P_top", Constants.ARM_P_TOP).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 3)).getEntry();
        i_top = PID_top.add("I_top", Constants.ARM_I_TOP).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 3)).getEntry();
        d_top = PID_top.add("D_top", Constants.ARM_D_TOP).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 3)).getEntry();
        top_desired = PID_top.add("Desired Top Rotation", Constants.ARM_D_TOP).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 3.14)).getEntry();
        top_left_speed = PID_lower.add("Top Left Speed",ArmSubsystem.getInstance().getTopSpeedL()).getEntry();
        top_right_speed = PID_lower.add("Top Right Speed",ArmSubsystem.getInstance().getTopSpeedR()).getEntry();

        changingPIDEntry = settings.add("Is Updating PID", true).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
        manualControlArms = settings.add("Using Manual Control", true).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
    }

    public void updateArms() {
        ShuffleboardLayout lower = arms_tab.getLayout("Bottom");
        ShuffleboardLayout top = arms_tab.getLayout("Top");

        if (changingPIDEntry.getBoolean(false)) {
            if (Constants.ARM_P_BOTTOM != p_bottom.getDouble(Constants.ARM_P_BOTTOM) || Constants.ARM_I_BOTTOM != i_bottom.getDouble(Constants.ARM_I_BOTTOM) || Constants.ARM_D_BOTTOM != d_bottom.getDouble(Constants.ARM_D_BOTTOM))
                ArmSubsystem.getInstance().getBottomPID().updatePIDValues(p_bottom.getDouble(Constants.ARM_P_BOTTOM), i_bottom.getDouble(Constants.ARM_I_BOTTOM), d_bottom.getDouble(Constants.ARM_D_BOTTOM));
            if (Constants.ARM_P_TOP != p_top.getDouble(Constants.ARM_P_TOP) || Constants.ARM_I_TOP != i_top.getDouble(Constants.ARM_I_TOP) || Constants.ARM_D_TOP != d_top.getDouble(Constants.ARM_D_TOP))
                ArmSubsystem.getInstance().getTopPID().updatePIDValues(p_top.getDouble(Constants.ARM_P_TOP), i_top.getDouble(Constants.ARM_I_TOP), d_top.getDouble(Constants.ARM_D_TOP));
            Constants.ARM_P_BOTTOM = p_bottom.getDouble(Constants.ARM_P_BOTTOM);
            Constants.ARM_I_BOTTOM = i_bottom.getDouble(Constants.ARM_I_BOTTOM);
            Constants.ARM_D_BOTTOM = d_bottom.getDouble(Constants.ARM_D_BOTTOM);
            Constants.ARM_P_TOP = p_top.getDouble(Constants.ARM_P_TOP);
            Constants.ARM_I_TOP = i_top.getDouble(Constants.ARM_I_TOP);
            Constants.ARM_D_TOP = d_top.getDouble(Constants.ARM_D_TOP);
        }

        top.add("Top Rotation", ArmSubsystem.getInstance().getTopRotation()).getEntry();
        top.add("Desired Rotation", ArmSubsystem.getInstance().getDesiredTopRotation()).getEntry();
        top.add("Top Speed", ArmSubsystem.getInstance().getTopSpeed()).getEntry();

        lower.add("Bottom Rotation", ArmSubsystem.getInstance().getBottomRotation()).getEntry();
        lower.add("Desired Rotation", ArmSubsystem.getInstance().getBottomRotation()).getEntry();
        lower.add("Bottom Rotation", ArmSubsystem.getInstance().getBottomSpeed()).getEntry();
    }



    GenericEntry manualControlDrive;
    GenericEntry driveSpeed;
    GenericEntry rotationSpeed;
    public void initDrive() {
        ShuffleboardLayout settings = drive_tab.getLayout("Settings",BuiltInLayouts.kList).withSize(2, 6);
        
        manualControlDrive = settings.add("Using Manual Control", true).withWidget(BuiltInWidgets.kToggleSwitch).getEntry();
        driveSpeed = settings.add("Speed", Constants.DRIVETRAIN_SPEED).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 12)).getEntry();
        rotationSpeed = settings.add("Rotation Speed", Constants.DRIVETRAIN_ROTATION_SPEED).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1)).getEntry();
    }

    public void updateDrive() {
        Constants.DRIVETRAIN_SPEED = driveSpeed.getDouble(Constants.DRIVETRAIN_SPEED);
        Constants.DRIVETRAIN_ROTATION_SPEED = driveSpeed.getDouble(Constants.DRIVETRAIN_ROTATION_SPEED);
    }




    GenericEntry alliance;
    GenericEntry startingPos;
    public void initAuto() {
        ShuffleboardLayout settings = auto_tab.getLayout("Settings",BuiltInLayouts.kList).withSize(2, 6);

        alliance = settings.add("Alliance", false).withWidget(BuiltInWidgets.kToggleSwitch).withProperties(Map.of("min", 0, "max", 1)).getEntry();
        startingPos = settings.add("Starting Position (0 is left)", 0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 2)).getEntry();
    }




    /* GETTERS */
    public boolean usingManualArms() { return manualControlArms.getBoolean(true); }
    public boolean usingManualDrive() { return manualControlDrive.getBoolean(true); }
    public boolean getAlliance() { return alliance.getBoolean(true); }
    public int getStartPos() { return (int)startingPos.getDouble(0); }
}
