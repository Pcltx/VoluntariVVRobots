package util;

import com.qualcomm.robotcore.util.ElapsedTime;
//git
public class VVPDFS {
    public ElapsedTime timer = new ElapsedTime();
    private double P,D,F,S,MIN_POS,MAX_POS = 1,ERROR_DELTA,last_error;
    private boolean use_sqrt = false;

    public double update(double pos, double destination) {
        double error = destination - pos;
        double seconds = timer.seconds();
        if (seconds == 0) seconds = 0.0001;

        double derivative = (error - this.last_error) / seconds;
        timer.reset();
        this.last_error = error;
        double static_force = 0;
        if (Math.abs(error) > ERROR_DELTA) {
            static_force = Math.signum(error) * S;
        } else {
            error = 0;
        }
        double p_term;
        if (use_sqrt) {
            p_term = Math.sqrt(Math.abs(P * error)) * Math.signum(error);
        } else {
            p_term = P * error;
        }
        double output = p_term + (D * derivative) + (F * (pos - MIN_POS)/(MAX_POS - MIN_POS)) + static_force;

        return Math.min(1, Math.max(-1, output));
    }

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
