package util;

import com.qualcomm.robotcore.util.ElapsedTime;

public class VVPDFSvel {
    public ElapsedTime timer = new ElapsedTime();
    private double P,D,F,S,MIN_POS,MAX_POS = 1,ERROR_DELTA,last_error;
    private boolean use_sqrt = false;

    public double update(double currentVel, double targetVel) {
        double error = targetVel - currentVel;
        double currentTime = timer.seconds();

        // 1. Safety: Prevent division by zero
        if (currentTime < 1e-9) currentTime = 1e-9;

        double derivative = (error - this.last_error) / currentTime;
        timer.reset();
        this.last_error = error;

        // --- NEW DECELERATION OVERRIDE ---
        // If the shooter is spinning much faster than the target (e.g., dropping from High to Low)
        // Adjust the -250 threshold depending on how noisy your velocity reads are.
        if (error < -250) {
            // We only apply the Proportional term (which will be negative).
            // We completely cut F and S so they don't push the motor forward.

            double p_term_brake = P * error;

            // OPTIONAL: If it STILL doesn't slow down fast enough, you can multiply
            // the braking power here to make it actively reverse harder.
            // p_term_brake *= 2.0;

            return Math.min(1, Math.max(-1, p_term_brake));
        }

        // 2. Deadband (Normal operation)
        if (Math.abs(error) < ERROR_DELTA) {
            error = 0;
            derivative = 0;
        }

        // 3. P Term
        double p_term = P * error;

        // 4. Feedforward Terms
        double f_term = F * targetVel;

        double s_term = 0;
        if (Math.abs(targetVel) > 0.01) {
            s_term = Math.signum(targetVel) * S;
        }

        // 5. Calculate Output
        double output = p_term + (D * derivative) + f_term + s_term;

        return Math.min(1, Math.max(-1, output));
    }
    //git

    public void set_min_pos(double pos) {
        this.MIN_POS = pos;
    }

    public void set_max_pos(double pos) {
        this.MAX_POS = pos;
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

    public void set_sqrt(boolean enabled) {
        this.use_sqrt = enabled;
    }
}