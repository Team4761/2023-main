// Copyright (c) FIRST and other WPILib contributors.

// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.main;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Auto.EncoderAuto.GoMetersEncoder;
import frc.robot.Auto.PurePursuit.PathFollower;
import frc.robot.Auto.PurePursuit.PathoGen;
import frc.robot.Drivetrain.DrivetrainSubsystem;
import frc.robot.arm.ArmPIDSubsystem;
import frc.robot.Vision.visionVarsAndMethods;
import frc.robot.arm.ArmSubsystem;
import frc.robot.leds.LEDSubsystem;
import frc.robot.command.*;
import frc.robot.controller.XboxControl;
import frc.robot.impl.RobotImpl;
import frc.robot.impl.placeholder.Placeholder;
import frc.robot.impl.terry.Terry;
import frc.robot.impl.westcoast.WestCoast;
import frc.robot.intake.IntakeSubsystem;

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
  public static XboxControl xbox = new XboxControl(1);
  // Commands
  public final CommandScheduler commandScheduler = CommandScheduler.getInstance();
  //private final XboxArcadeDrive xboxArcadeDrive = new XboxArcadeDrive();
  private final ArmControl armControl = new ArmControl();
  private final UpdateLED updateLED = new UpdateLED();
  private final XboxArcadeDrive xboxArcadeDrive = new XboxArcadeDrive();
  // Subsystems
  public static DrivetrainSubsystem driveTrain = DrivetrainSubsystem.getInstance();
  public static ArmSubsystem arms = ArmSubsystem.getInstance();
  public static IntakeSubsystem intake = IntakeSubsystem.getInstance();
  public static LEDSubsystem leds = LEDSubsystem.getInstance();

  public static DifferentialDriveOdometry odometry;
  public static Pose2d pose;
  
  // where x is forwards
  //public double[][] pathPoints = {{0, 0}, {3.048, 0}};//, {1, 3}};
  public double[][] pathPoints = {{0, 0}, {2.048, 0.2}, {3.048, 1}, {3.6, 1.9}};//, {1, 3}};
  public PathoGen path;
  public PathFollower follower;

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

    path = new PathoGen(pathPoints);
    follower = new PathFollower(path.getPoints(), path.getTargetVelocities());

    for(double[] i:path.getPoints()) {
      System.out.println(i[0]+", "+i[1]);
    }

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
    timer.start();

    for(double i:path.getTargetVelocities()) {
      System.out.println(i);
    }
    commandScheduler.schedule(new GoMetersEncoder(4));
  }

  /** This method is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic()
  {
    // uses vision
    // double[] voltages = follower.calculate(visionVarsAndMethods.getEstimatedPose().getFirst().toPose2d(), Placeholder.averageMotorGroupVelocity(Placeholder.front_left, Placeholder.back_left)*Constants.distancePerEncoderTick, Placeholder.averageMotorGroupVelocity(Placeholder.front_right, Placeholder.back_right)*Constants.distancePerEncoderTick, timer.get());
    
    SmartDashboard.putNumber("encoders left", Placeholder.frontLeftPosition());
    SmartDashboard.putNumber("encoders right", Placeholder.frontRightPosition());
    SmartDashboard.putNumber("left m/s", Placeholder.getLeftVelocity()*Constants.distancePerEncoderTick);
    SmartDashboard.putNumber("right m/s", Placeholder.getRightVelocity()*Constants.distancePerEncoderTick);

    // uses encoders
    double[] voltages = follower.calculate(pose, Placeholder.getLeftVelocity()*Constants.distancePerEncoderTick, Placeholder.getRightVelocity()*Constants.distancePerEncoderTick, timer.get());
   
    //SmartDashboard.putNumber("volts left", voltages[0]);
    //SmartDashboard.putNumber("volts right", voltages[1]);

    //Placeholder.setVoltages(Math.max(-12, Math.min(12, voltages[0])), Math.max(-12, Math.min(12, voltages[1])));

    commandScheduler.run();
  }


  /** This method is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    leds.enableLEDs();

    //commandScheduler.schedule(armControl.repeatedly());
    commandScheduler.schedule(updateLED.repeatedly());
    commandScheduler.schedule(xboxArcadeDrive.repeatedly());
    /*
    SmartDashboard.putNumber("fupper arm angle", 0);
    SmartDashboard.putNumber("flower arm angle", 0);
    SmartDashboard.putNumber("farm x togo", 0);
    SmartDashboard.putNumber("farm y togo", 0);*/

    //arms.bottom.setGoal(SmartDashboard.getNumber("ARMS[02]: Bottom rotation", 0.2));
    //arms.top.setGoal(SmartDashboard.getNumber("ARMS[01]: Top rotation", 0.2));
  }


  /** This method is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    intake.setSpeed(0);

    xbox.leftBumper().onTrue(new InTakeCommand(intake));

    if (xbox.rightBumper().getAsBoolean()) {
      intake.setSpeed(0.9);
    }

    commandScheduler.run();
    /*
    SmartDashboard.putNumber("CONTROLLER[00] Right Axis X", xbox.getRightX());
    SmartDashboard.putNumber("CONTROLLER[01] Right Axis Y", xbox.getRightY());
    SmartDashboard.putNumber("CONTROLLER[02] Left Axis X", xbox.getLeftX());
    SmartDashboard.putNumber("CONTROLLER[03] Left Axis Y", xbox.getLeftY());*/
    //driveTrain.arcadeDrive(xbox.getLeftY() * 0.5 , xbox.getRightX() * 0.5);
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
