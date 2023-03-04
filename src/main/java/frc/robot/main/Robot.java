// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.main;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Auto.command.MainAutoCommand;
import frc.robot.Drivetrain.DrivetrainSubsystem;
import frc.robot.Vision.getPoseData;

import frc.robot.Auto.EncoderAuto.GoMetersEncoder;
import frc.robot.Auto.PurePursuit.PathFollower;
import frc.robot.Auto.PurePursuit.PathoGen;

import frc.robot.arm.ArmSubsystem;
import frc.robot.leds.LEDSubsystem;
import frc.robot.command.*;
import frc.robot.controller.XboxControl;
import frc.robot.impl.RobotImpl;
import frc.robot.impl.placeholder.Placeholder;
import frc.robot.impl.terry.Terry;
import frc.robot.impl.westcoast.WestCoast;
import frc.robot.intake.IntakeSubsystem;
import frc.robot.leds.UpdateLED;

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
  public final CommandScheduler commandScheduler = CommandScheduler.getInstance();
  private final UpdateLED updateLED = new UpdateLED();
  // Subsystems
  public static DrivetrainSubsystem driveTrain = DrivetrainSubsystem.getInstance();
  private final DriveController driveController = new DriveController(1);
  public static ArmSubsystem arms = ArmSubsystem.getInstance();
  private final ArmControl armControl = new ArmControl(0);
  public static IntakeSubsystem intake = IntakeSubsystem.getInstance();
  public static LEDSubsystem leds = LEDSubsystem.getInstance();

  public static DifferentialDriveOdometry odometry;
  public static Pose2d pose;

  public Timer timer = new Timer();

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

    Placeholder.zeroEncoders();

    odometry = new DifferentialDriveOdometry(Placeholder.m_gyro.getRotation2d(), Placeholder.frontLeftPosition()*Constants.distancePerEncoderTick, Placeholder.frontRightPosition()*Constants.distancePerEncoderTick, new Pose2d(0, 0, new Rotation2d()));
    pose = odometry.getPoseMeters();
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
    SmartDashboard.putNumber("odometry x", pose.getX());
    SmartDashboard.putNumber("odometry y", pose.getY());
    
    SmartDashboard.putNumber("gyro", Placeholder.m_gyro.getAngle());
    pose = odometry.update(Placeholder.m_gyro.getRotation2d(), Placeholder.frontLeftPosition()*Constants.distancePerEncoderTick, Placeholder.frontRightPosition()*Constants.distancePerEncoderTick);
    initFromSelector();
  }

  @Override
  public void autonomousInit()
  {
    //commandScheduler.schedule(new MainAutoCommand(getAutoSelector()));
    commandScheduler.schedule( new SequentialCommandGroup(
      // TODO: move arm down
      new ArmMoveCommand().withTimeout(1.0),
      new OutTakeCommand(IntakeSubsystem.getInstance(), 2),
      new MoveStraightMeasuredCommand(-1.0,4.0)
    ));
    timer.start();
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
    leds.enableLEDs();

    commandScheduler.schedule(new getPoseData());
    commandScheduler.schedule(armControl.repeatedly());
    commandScheduler.schedule(updateLED.repeatedly());
    commandScheduler.schedule(driveController.repeatedly());
  }

  /** This method is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    commandScheduler.run();
  }

  /** This method is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
    Placeholder.setVoltages(0, 0);
  }

  /** This method is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
    arms.stop();
    leds.disableLEDs();
    commandScheduler.cancelAll();
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
        autoSelected = PLACEHOLDER;
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

  private String getAutoSelector() {
    return SmartDashboard.getString("Auto", "2");
  }
}
