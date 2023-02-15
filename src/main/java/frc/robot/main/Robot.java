// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.main;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Drivetrain.DrivetrainSubsystem;
import frc.robot.arm.ArmSubsystem;
import frc.robot.leds.LEDSubsystem;
import frc.robot.command.*;
import frc.robot.controller.XboxControl;
import frc.robot.impl.RobotImpl;
import frc.robot.impl.placeholder.Placeholder;
import frc.robot.impl.terry.Terry;
import frc.robot.impl.westcoast.WestCoast;

/**
 * The VM is configured to automatically run this class, and to call the methods corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot
{
  // Robot Selection
  private static final String TERRY = "Terry";
  private static final String WEST_COAST = "West Coast";
  private static final String PLACEHOLDER= "Placeholder Name";
  long time = 0;
  private final SendableChooser<String> chooser = new SendableChooser<>();
  public static RobotImpl impl = new Placeholder();
  // Joystick (XBox) Input
  public static XboxControl xbox = new XboxControl(0);
  // Commands
  public final CommandScheduler commandScheduler = CommandScheduler.getInstance();
  //private final XboxArcadeDrive xboxArcadeDrive = new XboxArcadeDrive();
  private final ArmControl armControl = new ArmControl();
  private final UpdateLED updateLED = new UpdateLED();
  // Subsystems
  //public static DrivetrainSubsystem driveTrain = DrivetrainSubsystem.getInstance();
  public static ArmSubsystem arms = ArmSubsystem.getInstance();
  public static LEDSubsystem leds = LEDSubsystem.getInstance();

  /**
   * This method is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit()
  {
    chooser.setDefaultOption("Terry", TERRY);
    chooser.addOption("West Coast", WEST_COAST);
    chooser.addOption("Place Holder Name",PLACEHOLDER);
    SmartDashboard.putData("Robot Choices", chooser);
  }

  /**
   * This method is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic methods, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    initFromSelector();
  }

  @Override
  public void autonomousInit()
  {
    double topSpeed = .5;
    /*commandScheduler.schedule(
            new SequentialCommandGroup(
                    new MoveFeetForward(topSpeed, 20),
                    new RotateDegreesCommand(0.5, 90),
                    new MoveFeetForward(topSpeed, 2),
                    new RotateDegreesCommand(0.5, 90),
                    new MoveFeetForward(topSpeed, 20)
            )
    );*/

  }

  /** This method is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic()
  {
    commandScheduler.run();
  }


  /** This method is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    //commandScheduler.schedule(xboxArcadeDrive.repeatedly());
    leds.enableLEDs();

    commandScheduler.schedule(armControl.repeatedly());
    commandScheduler.schedule(updateLED.repeatedly());
  }


  /** This method is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    commandScheduler.run();
    SmartDashboard.putNumber("CONTROLLER[00] Right Axis X", xbox.getRightX());
    SmartDashboard.putNumber("CONTROLLER[01] Right Axis Y", xbox.getRightY());
    SmartDashboard.putNumber("CONTROLLER[02] Left Axis X", xbox.getLeftX());
    SmartDashboard.putNumber("CONTROLLER[03] Left Axis Y", xbox.getLeftY());
    int pattern = 0;
    if (time <= System.currentTimeMillis()) {
      leds.setLEDs(pattern);
      pattern++;
      pattern%=4;
      time = System.currentTimeMillis() + 1000;
    }
  }


  /** This method is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}


  /** This method is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
    arms.stop();
    leds.disableLEDs();
  }


  /** This method is called once when test mode is enabled. */
  @Override
  public void testInit() {}


  /** This method is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /**
   * This shows how to select between different modes using the dashboard. The sendable chooser code
   * works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser
   * code and uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional  modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  private void initFromSelector()
  {
    if (impl == null) {
      // String autoSelected = SmartDashboard.getString("Robot Choices", TERRY);
      String autoSelected = chooser.getSelected();
      if (autoSelected == null) {
        autoSelected = TERRY;
      }

      switch (autoSelected) {
        case WEST_COAST:
          impl = new WestCoast();
          break;

        case PLACEHOLDER:
          impl = new Placeholder();
          break;

        default:
        case TERRY:
          impl = new Terry();
          break;
      }
    }
  }
}
