package frc.robot.arm;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem;
import frc.robot.main.Constants;
import frc.robot.main.Robot;

public class ArmPIDSubsystem extends ProfiledPIDSubsystem {

    AbsoluteEncoder encoder;
    CANSparkMax motor;
    String motorType;
    // <https://www.reca.lc/arm> has a very useful calculator!!!
    // ArmFeedforward feedforward = new ArmFeedforward(0.01, 0.45, 1.95, 0.02);


    public ArmPIDSubsystem(AbsoluteEncoder e, CANSparkMax m, String type) {
        // Makes a new ProfiledPIDSubsystem with PID values of ARM_P, ARM_I, and ARM_D.
        // TrapezoidProfile makes it so that the motors know they can't go beyond ARM_MAX_ROTATION_SPEED and that they can accelerate up to ARM_MAX_ACCELERATION_SPEED
        // The last 0 defines a period of 0. This would only matter if we weren't using TimedRobot as our robot base.
        super(new ProfiledPIDController(Constants.ARM_P, Constants.ARM_I, Constants.ARM_D, 
            new TrapezoidProfile.Constraints( Constants.ARM_MAX_ROTATION_SPEED, Constants.ARM_MAX_ACCELERATION_SPEED)), 
        0);

        encoder = e;
        motor = m;
        motorType = type;
        setGoal(e.getRotation());
    }


    @Override
    public void useOutput(double output, TrapezoidProfile.State setpoint) {
        double ff = 0.0;//feedforward.calculate(setpoint.position, setpoint.velocity);
        System.out.println("CURRENT OUTPUT: " + output);
        if (motorType.equalsIgnoreCase("top"))
            Robot.arms.setTop(output + ff);
        if (motorType.equalsIgnoreCase("bottom"))
            Robot.arms.setBottom(output + ff);
    }

    @Override
    public double getMeasurement() {
        System.out.println("Our current rotation is | " + Robot.arms.getTopRotation() + " | and our desired rotation is | " + Robot.arms.getDesiredTopRotation());
        if (motorType.equalsIgnoreCase("top"))
            return Robot.arms.getTopRotation();
        else
            return Robot.arms.getBottomRotation();
    }
}
