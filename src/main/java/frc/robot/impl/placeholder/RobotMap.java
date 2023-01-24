// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.impl.placeholder;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

/** This the class for mapping the physical parts of the robot to code.
 *  Motors/servos/gyros/all other components added here. -Owen */
public class RobotMap {

    // Drivetrain motors. The Talons already have encoders inside them
    public static WPI_TalonFX front_left = new WPI_TalonFX(0);
    public static WPI_TalonFX front_right = new WPI_TalonFX(1);
    public static WPI_TalonFX back_left = new WPI_TalonFX(2);
    public static WPI_TalonFX back_right = new WPI_TalonFX(3);

    //Encoders that are pre-built into the talons
    public static CANCoder front_left_CANCoder = new CANCoder(0);
    public static CANCoder front_right_CANCoder = new CANCoder(1);
    public static CANCoder back_left_CANCoder = new CANCoder(2);
    public static CANCoder back_right_CANCoder = new CANCoder(3);

    // Drivetrain
    public static final MotorControllerGroup m_leftMotors = new MotorControllerGroup(front_left,back_left);
    public static final MotorControllerGroup m_rightMotors = new MotorControllerGroup(front_right,back_right);

    private static final DifferentialDrive m_drive = new DifferentialDrive(m_leftMotors,m_rightMotors);









}
