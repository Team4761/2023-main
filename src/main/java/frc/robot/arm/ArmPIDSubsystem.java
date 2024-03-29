package frc.robot.arm;

import com.revrobotics.CANSparkMax;

//import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem;
import frc.robot.main.Constants;
import frc.robot.main.Robot;

public class ArmPIDSubsystem extends ProfiledPIDSubsystem {

    AbsoluteEncoder encoder;
    CANSparkMax motor;
    String motorType;
    ArmSubsystem subsystem;
    public double ff = 0.0;

    private boolean onlyFF = false;
    // <https://www.reca.lc/arm> has a very useful calculator!!!

    //was commneted
    //ArmFeedforward feedforward = new ArmFeedforward(0.01, 0.45, 1.95, 0.02);


    public ArmPIDSubsystem(AbsoluteEncoder e, CANSparkMax m, String type, double p, double i, double d, double maxSpeed, ArmSubsystem armSubsystem) {
        // Makes a new ProfiledPIDSubsystem with PID values of ARM_P, ARM_I, and ARM_D.
        // TrapezoidProfile makes it so that the motors know they can't go beyond ARM_MAX_ROTATION_SPEED and that they can accelerate up to ARM_MAX_ACCELERATION_SPEED
        // The last 0 defines a period of 0. This would only matter if we weren't using TimedRobot as our robot base.
        super(new ProfiledPIDController(p, i, d,
            new TrapezoidProfile.Constraints(maxSpeed, Constants.ARM_MAX_ACCELERATION_SPEED)), 
        0);
        
        encoder = e;
        motor = m;
        motorType = type;
        subsystem = armSubsystem;
        setGoal(e.getRotation());
    }


    @Override
    public void useOutput(double output, TrapezoidProfile.State setpoint) {
        ff = 0.0;
        //System.out.println("Calculation: " + m_controller.getSetpoint().position + " | " + m_controller.getGoal().position);

        if (motorType.equalsIgnoreCase("top"))
            Robot.m_shuffleboard.setTopFF(subsystem.calculateFeedforwards().get(1,0) / 12.0);
            SmartDashboard.putNumber("top ff", subsystem.calculateFeedforwards().get(1,0) / 12.0);
        if (motorType.equalsIgnoreCase("bottom"))
            Robot.m_shuffleboard.setBottomFF(subsystem.calculateFeedforwards().get(0,0) / 12.0);
            SmartDashboard.putNumber("botto ff", subsystem.calculateFeedforwards().get(0,0) / 12.0);
        if(motorType.equalsIgnoreCase("top"))// && ArmSubsystem.getInstance().useFeedForward)
             ff = subsystem.calculateFeedforwards().get(1,0) / 12.0 / 3;
         if(motorType.equalsIgnoreCase("bottom"))// & ArmSubsystem.getInstance().useFeedForward)
             ff = subsystem.calculateFeedforwards().get(0,0) / 12.0 / 2.8;
    
        //System.out.println("CURRENT OUTPUT: " + output);
        //System.out.println("ERROR: " + (ArmSubsystem.getInstance().getDesiredTopRotation()-ArmSubsystem.getInstance().getTopRotation()));

        if(onlyFF == true) output = 0;

        if (motorType.equalsIgnoreCase("top")) {
            SmartDashboard.putNumber("pid top", output);
            ArmSubsystem.getInstance().setTop(-(output+ff));
        }
        if (motorType.equalsIgnoreCase("bottom")) {
            SmartDashboard.putNumber("pid bottom", output);
            ArmSubsystem.getInstance().setBottom(-(output-ff));
        }
        //System.out.println(motorType + " | " + getController().getP() + " , " + getController().getI() + " , " + getController().getD());
    }

    @Override
    public double getMeasurement() {
        //System.out.println("Our current rotation is | " + ArmSubsystem.getInstance().getTopRotation() + " | and our desired rotation is | " + ArmSubsystem.getInstance().getDesiredTopRotation());
        ArmSubsystem instance = ArmSubsystem.getInstance();
        if (motorType.equalsIgnoreCase("top")) {
            //System.out.println(ArmSubsystem.getInstance().getTopRotation());
            return ArmSubsystem.getInstance().getTopRotation();
        }
        else {
            //System.out.println(ArmSubsystem.getInstance().getBottomRotation());
            return ArmSubsystem.getInstance().getBottomRotation();
        }

    }

    public void onlyFF(boolean onlyff) {
        onlyFF = onlyff;
    }

    public void updatePIDValues(double p, double i, double d) {
        if (motorType.equalsIgnoreCase("bottom")) {
            super.getController().setPID(p, i, d);
        }
        if (motorType.equalsIgnoreCase("top"))
            super.getController().setPID(p, i, d);
        //System.out.println(motorType + " | " + getController().getP() + " , " + getController().getI() + " , " + getController().getD());
    }

    public void updateMaxSpeed(double speed) {
        super.getController().setConstraints(new TrapezoidProfile.Constraints(speed, Constants.ARM_MAX_ACCELERATION_SPEED));
        
    }

    @Override
    public void periodic() {
        if (m_enabled) {
            //System.out.println("Periodic: " + m_controller.getSetpoint() + " | " + m_controller.getGoal());
            useOutput(m_controller.calculate(getMeasurement()) /*+ feedforward.calculate(encoder.getRotation(), encoder.getVelocity(), encoder.getAcceleration())*/, m_controller.getSetpoint());
        }
        encoder.updateEncoder();
    }

    public boolean isAtSetpoint() {
        return m_controller.atSetpoint();
    }
}
