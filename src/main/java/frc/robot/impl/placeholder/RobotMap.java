// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.impl.placeholder;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;
import com.ctre.phoenix.sensors.SensorTimeBase;
import edu.wpi.first.wpilibj.CAN;
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

    //Setting encoder variables
    CANCoderConfiguration CANCoderConfig = new CANCoderConfiguration();


    // Drivetrain
    public static final MotorControllerGroup m_leftMotors = new MotorControllerGroup(front_left,back_left);
    public static final MotorControllerGroup m_rightMotors = new MotorControllerGroup(front_right,back_right);

    private static final DifferentialDrive m_drive = new DifferentialDrive(m_leftMotors,m_rightMotors);



    public RobotMap(){

        //config of the encoders

        CANCoderConfig.sensorCoefficient = 0.000244140625; // this is will be (1/4096) * gearRatio ( < 1) * wheelRadius to return meters.
        CANCoderConfig.unitString = "revs"; //defines the units
        CANCoderConfig.sensorTimeBase = SensorTimeBase.PerSecond; //defines the time period
        //the above stuff basically makes it so when you ask for a velocity from the encoder, (getVelocity), it returns revs/sec.
        //This may want to be converted into meters or yards per second to simplify the goDistanceEncoder command.

        front_left_CANCoder.configAllSettings(CANCoderConfig); //put the config into the encoders
        front_right_CANCoder.configAllSettings(CANCoderConfig);
        back_left_CANCoder.configAllSettings(CANCoderConfig);
        back_right_CANCoder.configAllSettings(CANCoderConfig);


    }





}
