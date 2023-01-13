// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.main;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.command.*;
import frc.robot.impl.RobotImpl;
import frc.robot.impl.westcoast.WestCoast;

/**
 * The VM is configured to automatically run this class, and to call the methods corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot
{
  private static final String DEFAULT_AUTO = "Default";
  private static final String CUSTOM_AUTO = "My Auto";
  private final SendableChooser<String> chooser = new SendableChooser<>();

  public final CommandScheduler commandScheduler = CommandScheduler.getInstance();
  private final XboxArcadeDrive xboxArcadeDrive = new XboxArcadeDrive();

  public static RobotImpl impl = new WestCoast();

  /**
   * This method is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit()
  {
    chooser.setDefaultOption("Default Auto", DEFAULT_AUTO);
    chooser.addOption("My Auto", CUSTOM_AUTO);
    SmartDashboard.putData("Auto choices", chooser);
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
  }


  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit()
  {
    double topSpeed = .5;
    commandScheduler.schedule(
            new SequentialCommandGroup(
                    new MoveFeetForward(topSpeed, 20),
                    new RotateDegreesCommand(0.5, 180),
                    new MoveFeetForward(topSpeed, 20),
                    new RotateDegreesCommand(0.5, 180)
            )
    );
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
    commandScheduler.schedule(xboxArcadeDrive.repeatedly());
  }


  /** This method is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    commandScheduler.run();
  }


  /** This method is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}


  /** This method is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}


  /** This method is called once when test mode is enabled. */
  @Override
  public void testInit() {}


  /** This method is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
