package frc.robot.impl.placeholder;

public class SimpleMotorFeedforward {
    public final double ks;
    public final double kv;
    public final double ka;

    /**
     * Creates a new SimpleMotorFeedforward with the specified gains. Units of the gain values will
     * dictate units of the computed feedforward.
     *
     * @param ks The static gain.
     * @param kv The velocity gain.
     * @param ka The acceleration gain.
     */
    public SimpleMotorFeedforward(double ks, double kv, double ka) {
        this.ks = ks;
        this.kv = kv;
        this.ka = ka;
    }

    /**
     * Creates a new SimpleMotorFeedforward with the specified gains. Acceleration gain is defaulted
     * to zero. Units of the gain values will dictate units of the computed feedforward.
     *
     * @param ks The static gain.
     * @param kv The velocity gain.
     */
    public SimpleMotorFeedforward(double ks, double kv) {
        this(ks, kv, 0);
    }

    /**
     * Calculates the feedforward from the gains and setpoints.
     *
     * @param velocity The velocity setpoint.
     * @param acceleration The acceleration setpoint.
     * @return The computed feedforward.
     */
    public double calculate(double velocity, double acceleration) {
        return ks * Math.signum(velocity) + kv * velocity + ka * acceleration;
    }

    /**
     * Calculates the feedforward from the gains and velocity setpoint (acceleration is assumed to be
     * zero).
     *
     * @param velocity The velocity setpoint.
     * @return The computed feedforward.
     */
    public double calculate(double velocity) {
        return calculate(velocity, 0);
    }


}