package util;

import com.qualcomm.robotcore.util.ElapsedTime;

public class SpindexVVPDFSvel {
    public ElapsedTime timer = new ElapsedTime();
    private double P, D, F, S, ERROR_DELTA, last_error;

    public double update(double currentVel, double targetVel) {
        double error = targetVel - currentVel;
        double currentTime = timer.seconds();

        // 1. Safety: Prevent division by zero
        if (currentTime < 1e-9) currentTime = 1e-9;

        double derivative = (error - this.last_error) / currentTime;
        timer.reset();
        this.last_error = error;

        // 2. Spindexer Deceleration
        // If the spindexer is overshooting its target velocity by more than 5 rad/s
        if (error < -5) {
            // Apply only the proportional braking force. Cut Feedforward so it doesn't push forward.
            double p_term_brake = P * error;
            return Math.min(1, Math.max(-1, p_term_brake));
        }

        // 3. Deadband (Normal operation)
        if (Math.abs(error) < ERROR_DELTA) {
            error = 0;
            derivative = 0;
        }

        // 4. Calculate Terms
        double p_term = P * error;
        double f_term = F * targetVel;

        // S-term to overcome static friction when starting up
        double s_term = 0;
        if (Math.abs(targetVel) > 0.01) {
            s_term = Math.signum(targetVel) * S;
        }

        // 5. Calculate Output
        double output = p_term + (D * derivative) + f_term + s_term;

        return Math.min(1, Math.max(-1, output));
    }

    public void set_error_delta(double error_delta) {
        this.ERROR_DELTA = error_delta;
    }

    public void set_coeffs(double Kp, double Kd, double Kf, double Ks) {
        this.P = Kp;
        this.D = Kd;
        this.F = Kf;
        this.S = Ks;
    }
}