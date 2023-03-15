// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.main;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.RobocketsShuffleboard;
import frc.robot.Auto.command.MainAutoCommand;
import frc.robot.Drivetrain.DrivetrainSubsystem;
import frc.robot.Vision.getPoseData;

import frc.robot.arm.ArmSubsystem;
import frc.robot.leds.LEDSubsystem;
import frc.robot.command.*;
import frc.robot.impl.RobotImpl;
import frc.robot.impl.Paligator.Paligator;
import frc.robot.impl.terry.Terry;
import frc.robot.impl.westcoast.WestCoast;
import frc.robot.intake.IntakeSubsystem;
import frc.robot.leds.UpdateLED;

import frc.robot.Auto.PurePursuit.*;

/**
 * The VM is configured to automatically run this class, and to call the methods corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot
{
  boolean win = true;

  // Robot Selection
  private static final String TERRY = "Terry";
  private static final String WEST_COAST = "West Coast";
  private static final String PLACEHOLDER= "Placeholder Name";
  long time = 0;
  private final SendableChooser<String> chooser = new SendableChooser<>();
  public static RobotImpl impl = new Paligator();
  public final CommandScheduler commandScheduler = CommandScheduler.getInstance();
  // Subsystems
  public static LEDSubsystem leds = LEDSubsystem.getInstance();
  public static UpdateLED updateLED = new UpdateLED();

  public static DrivetrainSubsystem driveTrain = DrivetrainSubsystem.getInstance();
  public static DriveController driveController = new DriveController(Constants.DRIVE_CONTROLLER_PORT);

  public static ArmSubsystem arms = ArmSubsystem.getInstance();
  public static ArmControl armControl = new ArmControl(Constants.ARM_CONTROLLER_PORT);
  
  public static IntakeSubsystem intake = IntakeSubsystem.getInstance();
  public static RobocketsShuffleboard m_shuffleboard = new RobocketsShuffleboard();

  public static DifferentialDriveOdometry odometry;
  public static Pose2d pose;

  public double[][] path = {{3.0, 0.0}, {3.5, 1.0}};
  public PathoGen pathToFollow = new PathoGen(path);
  public PathFollower pathFollower = new PathFollower(pathToFollow.getPoints(), pathToFollow.getTargetVelocities());

  /**
   * This method is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit()
  {
    chooser.setDefaultOption("Terry", PLACEHOLDER);
    chooser.addOption("West Coast", WEST_COAST);
    chooser.addOption("Place Holder Name",PLACEHOLDER);
    SmartDashboard.putData("Robot Choices", chooser);

    Paligator.zeroEncoders();

    odometry = new DifferentialDriveOdometry(
          Paligator.m_gyro.getRotation2d(),
          Paligator.frontLeftPosition()*Constants.distancePerEncoderTick,
          Paligator.frontRightPosition()*Constants.distancePerEncoderTick,
          new Pose2d(0, 0, new Rotation2d())
    );
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
    
    SmartDashboard.putNumber("velocity left", Paligator.getLeftVelocity()*Constants.distancePerEncoderTick);
    SmartDashboard.putNumber("velocity right", Paligator.getRightVelocity()*Constants.distancePerEncoderTick);
    
    SmartDashboard.putNumber("gyro", Paligator.m_gyro.getAngle()%360);
    pose = odometry.update(
          Paligator.m_gyro.getRotation2d(),
          Paligator.frontLeftPosition()*Constants.distancePerEncoderTick,
          Paligator.frontRightPosition()*Constants.distancePerEncoderTick
    );
    initFromSelector();
  }

  @Override
  public void autonomousInit()
  {
    commandScheduler.schedule(new MainAutoCommand(getAutoSelector()));
  }

  /** This method is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic()
  {
    commandScheduler.run();

    // pathfollowing test
//    double[] volts = pathFollower.calculate(pose, Paligator.getLeftVelocity()*Constants.distancePerEncoderTick, Paligator.getRightVelocity()*Constants.distancePerEncoderTick);
//    Paligator.setVoltages(volts[0], volts[1]);
  }

  /** This method is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    UpdateLED.setText("Strout Is Bacon   ");
    leds.enableLEDs();
    armControl = new ArmControl(Constants.ARM_CONTROLLER_PORT);
    updateLED = new UpdateLED();
    driveController = new DriveController(Constants.DRIVE_CONTROLLER_PORT);

    //commandScheduler.schedule(new getPoseData());
    commandScheduler.schedule(armControl.repeatedly());
    commandScheduler.schedule(updateLED.repeatedly());
    commandScheduler.schedule(driveController.repeatedly());
  }

  long nextTime = 0;
  int curPattern = 0;
  /** This method is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    commandScheduler.run();

    m_shuffleboard.updateArms();
    m_shuffleboard.updateDrive();
    //System.out.println(m_shuffleboard.useFeedForward);
  }

  // @Override
  // public void teleopExit() {
    
  // }

  /** This method is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
    Paligator.setVoltages(0, 0);
    leds.disableLEDs();
  }

  /** This method is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
    arms.stop();
    commandScheduler.cancelAll();
  }

  /** This method is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This method is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

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
          impl = new Paligator();
          break;

        default:
        case TERRY:
          impl = new Terry();
          break;
      }
    }
  }

  private int getAutoSelector() {
    return m_shuffleboard.getStartPos();
  }

}
