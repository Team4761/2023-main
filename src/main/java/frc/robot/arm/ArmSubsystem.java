package frc.robot.arm;

import java.util.Map;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.main.Constants;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;

public class ArmSubsystem extends SubsystemBase {
    private final static ArmSubsystem INSTANCE = new ArmSubsystem();

    AbsoluteEncoder bottomEncoder;
    AbsoluteEncoder topEncoder;
    CANSparkMax bottom_left;
    CANSparkMax bottom_right;
    CANSparkMax top_motor_left;
    CANSparkMax top_motor_right;
    public ArmPIDSubsystem bottom;
    public ArmPIDSubsystem top;

    ArmMath inverseKinematics;
    Translation2d pos;
    private static double desiredTopRotation = 0.0;
    private static double desiredBottomRotation = 0.0;

    private boolean goingToSetPosition = false;
    // CONNECT USING the white, red, and black cable.
    // The WHITE cable is the signal wire.
    
    //JointConfig stuff
    private JointConfig top_joint = new JointConfig(Constants.TOP_MASS, Constants.ARM_LENGTH_TOP, Constants.TOP_MOI, Constants.TOP_CGRADIUS, Constants.TOP_MOTOR);
    private JointConfig bottom_joint = new JointConfig(Constants.BOTTOM_MASS, Constants.ARM_LENGTH_BOTTOM, Constants.BOTTOM_MOI, Constants.BOTTOM_CGRADIUS, Constants.BOTTOM_MOTOR);
    private DJArmFeedforward djArmFeedforward = new DJArmFeedforward(bottom_joint, top_joint);

    private ArmSubsystem()
    {
        bottomEncoder = new AbsoluteEncoder(Constants.ARM_ENCODER_BOTTOM_PORT);
        topEncoder = new AbsoluteEncoder(Constants.ARM_ENCODER_TOP_PORT);
        bottom_left = new CANSparkMax(Constants.ARM_MOTOR_BOTTOM_LEFT_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        bottom_right = new CANSparkMax(Constants.ARM_MOTOR_BOTTOM_RIGHT_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        top_motor_left = new CANSparkMax(Constants.ARM_MOTOR_TOP_LEFT_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);
        top_motor_right = new CANSparkMax(Constants.ARM_MOTOR_TOP_RIGHT_PORT, CANSparkMaxLowLevel.MotorType.kBrushless);

        bottom_left.setInverted(true);
        top_motor_left.setInverted(true);
        //bottom_right.setInverted(true);
        //top_motor_right.setInverted(true);

        // Do find '//REMOVABLE' and replace all with nothing to activate bottom control
        bottom = new ArmPIDSubsystem(bottomEncoder, bottom_left, "bottom", Constants.ARM_P_BOTTOM, Constants.ARM_I_BOTTOM, Constants.ARM_D_BOTTOM, this);
        top = new ArmPIDSubsystem(topEncoder, top_motor_left, "top", Constants.ARM_P_TOP, Constants.ARM_I_TOP, Constants.ARM_D_TOP, this);

        inverseKinematics = new ArmMath();
        pos = inverseKinematics.getPoint(getBottomRotation(), getTopRotation());

    }

    @Override
    public void register() {
        super.register();
        top.enable();
        bottom.enable();
    }

    @SuppressWarnings("WeakerAccess")
    public static ArmSubsystem getInstance() {
        return INSTANCE;
    }


    /* Setters */
    // PID control
    public void movePID() {
        if (getDesiredBottomRotation() - getDesiredTopRotation() < Math.PI*0.5 + 0.1)
            top.setGoal(getDesiredTopRotation());
        bottom.setGoal(getDesiredBottomRotation());
    }
    // Updating positions
    public void updatePos(double deltaX, double deltaY) {
        if (deltaX > Constants.CONTROLLER_DEADZONE || deltaX < 0-Constants.CONTROLLER_DEADZONE || deltaY > Constants.CONTROLLER_DEADZONE || deltaY < 0-Constants.CONTROLLER_DEADZONE) {
            if (goingToSetPosition) {
                pos = inverseKinematics.getPoint(getBottomRotation(), getTopRotation());
                goingToSetPosition = false;
            }
            if (inverseKinematics.inBounds(pos.plus(new Translation2d(deltaX, deltaY))))
                pos = pos.plus(new Translation2d(deltaX, deltaY));
            setDesiredBottomRotation(inverseKinematics.arm1Theta(pos.getX(),pos.getY()));
            setDesiredTopRotation(inverseKinematics.arm2Theta(pos.getX(),pos.getY()));
        }
    }
    //FEEDFORWARD IMPL //Needs to be based off 0,0 as straight extensions (intial arm math)
    public Vector<N2> calculateFeedforwards() {
        double inputUpper = -desiredTopRotation + Math.toRadians(Constants.FLAT_ARM_TOP_OFFSET);
        double inputLower = desiredBottomRotation + Math.toRadians(Constants.FLAT_ARM_BOTTOM_OFFSET);
        Vector<N2> angles = VecBuilder.fill(inputLower, inputUpper);

        Vector<N2> vectorFF = djArmFeedforward.feedforward(angles);
        return vectorFF;
    }//check with alistair to implement this into pid and check subsystem class linked
    public void updatePos(Translation2d delta) {
        if (delta.getX() > Constants.CONTROLLER_DEADZONE || delta.getX() < 0-Constants.CONTROLLER_DEADZONE || delta.getY() > Constants.CONTROLLER_DEADZONE || delta.getY() < 0-Constants.CONTROLLER_DEADZONE) {
            if (goingToSetPosition) {
                pos = inverseKinematics.getPoint(getBottomRotation(), getTopRotation());
                goingToSetPosition = false;
            }
            if (inverseKinematics.inBounds(pos.plus(delta)))
                pos = pos.plus(delta);
            setDesiredBottomRotation(inverseKinematics.arm1Theta(pos.getX(),pos.getY()));
            setDesiredTopRotation(inverseKinematics.arm2Theta(pos.getX(),pos.getY()));
        }
    }
    public void moveToSetPos(Translation2d pos) {
        setDesiredBottomRotation(inverseKinematics.arm1Theta(pos.getX(),pos.getY()));
        setDesiredTopRotation(inverseKinematics.arm2Theta(pos.getX(),pos.getY()));
        goingToSetPosition = true;
    }
    public void moveToSetRotation(Translation2d rotations) {
        setDesiredBottomRotation(rotations.getY());
        setDesiredTopRotation(rotations.getX());
        goingToSetPosition = true;
        enablePID();
        movePID();
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
        top_motor_left.set(speed);
        top_motor_right.set(speed);
    }
    public void stopTop() {
        top_motor_left.stopMotor();
        top_motor_right.stopMotor();
    }
    // All Motor Control
    public void stop() {
        stopBottom();
        stopTop();
    }
    public void zeroEncoders() {
        System.out.println("[ZERO ENCODERS] Please note that zero-ing encoders only works for the current run, and the next run it will default back to official values!");
        bottomEncoder.reset();
        topEncoder.reset();
    }
    public void enablePID() {
        top.enable();
        bottom.enable();
    }
    public void disablePID() {
        top.disable();
        bottom.disable();
    }

    /* Getters */
    public double getBottomSpeed() {
        return (bottom_left.get() + bottom_right.get()) / 2;
    }
    public double getBottomSpeedL() {
        return bottom_left.get();
    }
    public double getBottomSpeedR() {
        return bottom_right.get();
    }
    public double getTopSpeed() {
        return (top_motor_left.get() + top_motor_right.get()) / 2;
    }
    public double getTopSpeedL() {
        return top_motor_left.get();
    }
    public double getTopSpeedR() {
        return top_motor_right.get();
    }
    public double getBottomRotation() {
        double bottomRotation = (Math.PI*2) - bottomEncoder.getRotation();
        if (bottomRotation >= Constants.ENCODER_ZERO_VALUE_BOTTOM)
            return bottomRotation - Constants.ENCODER_ZERO_VALUE_BOTTOM;
        else
            return bottomRotation + (Math.PI * 2) - Constants.ENCODER_ZERO_VALUE_BOTTOM;
    }
    public double getTopRotation() {
        if (topEncoder.getRotation() >= Constants.ENCODER_ZERO_VALUE_TOP)
            return topEncoder.getRotation() - Constants.ENCODER_ZERO_VALUE_TOP;
        else
            return topEncoder.getRotation() + (Math.PI * 2) - Constants.ENCODER_ZERO_VALUE_TOP;
    }
    public double getDesiredBottomRotation() {
        //return inverseKinematics.arm1Theta(pos.getX(),pos.getY());
        return desiredBottomRotation;
    }
    public double getDesiredTopRotation() {
        //return inverseKinematics.arm2Theta(pos.getX(),pos.getY());
        return desiredTopRotation;
    }
    public static void setDesiredTopRotation(double rot)
    {
        desiredTopRotation = rot;
    }
    public static void setDesiredBottomRotation(double rot){
        desiredBottomRotation = rot;
    }
    public ArmPIDSubsystem getTopPID() {
        return top;
    }
    public ArmPIDSubsystem getBottomPID() {
        return bottom;
    }

    // Emergencies
    public void emergencyStop() {
        top.disable();
        bottom.disable();
        stop();
    }




    /* Debugging Info */
    private boolean finishedDebugInit = false;
    long nextTime = System.currentTimeMillis() + 1000;
    //GenericEntry p_bottom;
    //GenericEntry i_bottom;
    //GenericEntry d_bottom;
    //GenericEntry p_top;
    //GenericEntry i_top;
    //GenericEntry d_top;
    //GenericEntry top_desired;
    //GenericEntry bottom_desired;
    //GenericEntry bottom_left_speed;
    //GenericEntry bottom_right_speed;

    public void debug() {
        //if (!finishedDebugInit) {
            //ShuffleboardTab tab = Shuffleboard.getTab("Arms");
            //p_bottom = tab.add("P_bottom", Constants.ARM_P_BOTTOM).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 3)).getEntry();
            //i_bottom = tab.add("I_bottom", Constants.ARM_I_BOTTOM).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 3)).getEntry();
            //d_bottom = tab.add("D_bottom", Constants.ARM_D_BOTTOM).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 3)).getEntry();
            //p_top = tab.add("P_top", Constants.ARM_P_TOP).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 20)).getEntry();
            //i_top = tab.add("I_top", Constants.ARM_I_TOP).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 3)).getEntry();
            //d_top = tab.add("D_top", Constants.ARM_D_TOP).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 3)).getEntry();
            //top_desired = tab.add("Desired Top Rotation", Constants.ARM_D_TOP).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 3.14)).getEntry();
            //bottom_desired = tab.add("Desired Bottom Rotation", Constants.ARM_D_TOP).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 3.14)).getEntry();
            //bottom_left_speed = tab.add("Bottom Left Speed",bottom_left.get()).getEntry();;
            //bottom_right_speed = tab.add("Bottom Right Speed",bottom_right.get()).getEntry();

            //finishedDebugInit = true;
        //}
        //System.out.println("TOP ROTATION: " + getTopRotation() + " | " + "BOTTOM ROTATION: " + getBottomRotation());
        //SmartDashboard.putBoolean("ARMS[00]: Is debugging", true);
        //SmartDashboard.putNumber("ARMS[01]: Top rotation", getTopRotation());
        //SmartDashboard.putNumber("ARMS[02]: Bottom rotation", getBottomRotation());
        //SmartDashboard.putNumber("ARMS[03]: Desired top rotation", getDesiredTopRotation());
        //SmartDashboard.putNumber("ARMS[04]: Desired bottom rotation", getDesiredBottomRotation());
        //SmartDashboard.putNumber("ARMS[05]: Top Speed", getTopSpeed());
        //SmartDashboard.putNumber("ARMS[06]: Bottom Speed", getBottomSpeed());
        //SmartDashboard.putNumberArray("ARMS[07]: Position to get to", new double[]{pos.getX(),pos.getY()});

        // Widgets (sliders!) [For tuning PID cause yay]
        //if (Constants.ARM_P_BOTTOM != p_bottom.getDouble(Constants.ARM_P_BOTTOM) || Constants.ARM_I_BOTTOM != i_bottom.getDouble(Constants.ARM_I_BOTTOM) || Constants.ARM_D_BOTTOM != d_bottom.getDouble(Constants.ARM_D_BOTTOM))
        //    bottom.updatePIDValues(p_bottom.getDouble(Constants.ARM_P_BOTTOM), i_bottom.getDouble(Constants.ARM_I_BOTTOM), d_bottom.getDouble(Constants.ARM_D_BOTTOM));
        //if (Constants.ARM_P_TOP != p_top.getDouble(Constants.ARM_P_TOP) || Constants.ARM_I_TOP != i_top.getDouble(Constants.ARM_I_TOP) || Constants.ARM_D_TOP != d_top.getDouble(Constants.ARM_D_TOP))
        //    top.updatePIDValues(p_top.getDouble(Constants.ARM_P_TOP), i_top.getDouble(Constants.ARM_I_TOP), d_top.getDouble(Constants.ARM_D_TOP));
        //desiredTopRotation = top_desired.getDouble(0.0);
        //desiredBottomRotation = bottom_desired.getDouble(0.0);
        // Constants.ARM_P_BOTTOM = p_bottom.getDouble(Constants.ARM_P_BOTTOM);
        // Constants.ARM_I_BOTTOM = i_bottom.getDouble(Constants.ARM_I_BOTTOM);
        // Constants.ARM_D_BOTTOM = d_bottom.getDouble(Constants.ARM_D_BOTTOM);
        // Constants.ARM_P_TOP = p_top.getDouble(Constants.ARM_P_TOP);
        // Constants.ARM_I_TOP = i_top.getDouble(Constants.ARM_I_TOP);
        // Constants.ARM_D_TOP = d_top.getDouble(Constants.ARM_D_TOP);
        //bottom_left_speed.setDouble(bottom_left.get());
        //bottom_right_speed.setDouble(bottom_right.get());

        if (nextTime < System.currentTimeMillis()) {
            System.out.println("[TOP] P) " + Constants.ARM_P_TOP + "  I) " + Constants.ARM_I_TOP + "  D) " + Constants.ARM_D_TOP);
            System.out.println("[BOTTOM] P) " + Constants.ARM_P_BOTTOM + "  I) " + Constants.ARM_I_BOTTOM + "  D) " + Constants.ARM_D_BOTTOM);
            nextTime = System.currentTimeMillis() + 1000;
        }
    }
}
