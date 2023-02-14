package frc.robot.arm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.main.Constants;

public class ArmSubsystem extends SubsystemBase {
    private final static ArmSubsystem INSTANCE = new ArmSubsystem();

    AbsoluteEncoder lowEncoder;
    AbsoluteEncoder highEncoder;
    CANSparkMax bottom_left;
    CANSparkMax bottom_right;
    CANSparkMax top_motor;
    ArmPIDSubsystem bottom;
    ArmPIDSubsystem top;

    ArmMath inverseKinematics;
    Translation2d pos;


    // CONNECT USING the white, red, and black cable.
    // The WHITE cable is the signal wire.

    public ArmSubsystem()
    {
        lowEncoder = new AbsoluteEncoder(Constants.ARM_ENCODER_BOTTOM_PORT);
        highEncoder = new AbsoluteEncoder(Constants.ARM_ENCODER_TOP_PORT);
        bottom_left = new CANSparkMax(Constants.ARM_MOTOR_BOTTOM_LEFT_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        bottom_right = new CANSparkMax(Constants.ARM_MOTOR_BOTTOM_RIGHT_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        top_motor = new CANSparkMax(Constants.ARM_MOTOR_TOP_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);

        bottom_right.setInverted(true);

        //bottom = new ArmPIDSubsystem(lowEncoder, bottom_left, "low");
        top = new ArmPIDSubsystem(highEncoder, top_motor, "high");

        inverseKinematics = new ArmMath();
        pos = inverseKinematics.getPoint(getBottomRotation(), getTopRotation());
    }

    @SuppressWarnings("WeakerAccess")
    public static ArmSubsystem getInstance() {
        return INSTANCE;
    }


    /* Setters */
    // PID control
    public void move() {
        top.setGoal(getDesiredTopRotation());
        //bottom.setGoal(getDesiredBottomRotation());

        top.enable();
        // bottom.enable();
    }
    // Updating positions
    public void updatePos(double deltaX, double deltaY) {
        if (inverseKinematics.inBounds(pos.plus(new Translation2d(deltaX, deltaY)))) {}
        else {
            pos.minus(new Translation2d(deltaX, deltaY));
        }
    }
    public void updatePos(Translation2d delta) {
        if (inverseKinematics.inBounds(pos.plus(delta))) {}
        else {
            pos.minus(delta);
        }
    }
    // Bottom Motor Control
    public void setBottom(double speed) {
        bottom_left.set(speed);
        bottom_right.set(speed);
    }
    public void setBottomL(double speed) {
        bottom_left.set(speed);
    }
    public void setBottomR(double speed) {
        bottom_right.set(speed);
    }
    public void stopBottom() {
        bottom_left.stopMotor();
        bottom_right.stopMotor();
    }
    // Top Motor Control
    public void setTop(double speed) {
        top_motor.set(speed);
    }
    public void stopTop() {
        top_motor.stopMotor();
    }
    // All Motor Control
    public void stop() {
        stopBottom();
        stopTop();
    }
    public void zeroEncoders() {
        lowEncoder.reset();
        highEncoder.reset();
    }

    /* Getters */
    public double getBottomSpeed() {
        return (bottom_left.get() + bottom_right.get()) / 2;
    }
    public double getTopSpeed() {
        return top_motor.get();
    }
    public double getBottomRotation() {
        return lowEncoder.getRotation() - Constants.ENCODER_ZERO_VALUE_BOTTOM;
    }
    public double getTopRotation() {
        return highEncoder.getRotation() - Constants.ENCODER_ZERO_VALUE_TOP;
    }
    public double getDesiredBottomRotation() {
        return inverseKinematics.arm1Theta(pos.getX(),pos.getY());
    }
    public double getDesiredTopRotation() {
        //return inverseKinematics.arm2Theta(pos.getX(),pos.getY());
        return 0.2;
    }

    // Emergencies
    public void emergencyStop() {
        //top.disable();
        //bottom.disable();
        stop();
    }


    /* Debugging Info */
    public void debug() {
        System.out.println(getTopRotation() + " | " + getBottomRotation());
        SmartDashboard.putBoolean("ARMS[00]: Is debugging", true);
        SmartDashboard.putNumber("ARMS[01]: Top rotation", getTopRotation());
        SmartDashboard.putNumber("ARMS[02]: Bottom rotation", getBottomRotation());
        SmartDashboard.putNumber("ARMS[03]: Desired top rotation", getDesiredTopRotation());
        SmartDashboard.putNumber("ARMS[04]: Desired bottom rotation", getDesiredBottomRotation());
        SmartDashboard.putNumber("ARMS[05]: Top Speed", getTopSpeed());
        SmartDashboard.putNumber("ARMS[06]: Bottom Speed", getBottomSpeed());
        SmartDashboard.putNumberArray("ARMS[07]: Position to get to", new double[]{pos.getX(),pos.getY()});
    }
}