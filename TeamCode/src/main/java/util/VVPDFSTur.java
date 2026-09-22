package util;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.util.ElapsedTime;

public class VVPDFSTur {
    public ElapsedTime timer = new ElapsedTime();
    private double P, I, D, F, S;
    private double MIN_POS, MAX_POS = 1, ERROR_DELTA, last_error, ERROR_POS;
    private static Follower flw;
    private double lastPos;
    private double integralSum = 0, static_force=0;
    private boolean use_sqrt = false;

    public double update(double pos, double destination) {
        double error = destination - pos;
        double dt = timer.seconds();

        if (dt <= 0) dt = 0.0001;

        // 1. Calculate Integral
        integralSum += (error * dt);

        // 2. Calculate Derivative (Change in error)
        double derivative = -(pos - lastPos) / dt;
        lastPos = pos;
        timer.reset();


        if (Math.abs(error) > ERROR_DELTA) {
            static_force = Math.signum(error) * S;
        }
        else static_force=0;


        if(Math.abs(error)<ERROR_POS) error=0;
        // 5. Proportional Logic
        double pOutput = use_sqrt ? Math.sqrt(Math.abs(P * error)) * Math.signum(error) : (P * error);

        double angVel = (flw != null && flw.velocity() != null) ? flw.velocity().omega : 0.0;
        double output = pOutput
                + (I * integralSum)
                + (D * derivative)
                + (F*(-angVel)) // <-- Velocity Feedforward
                + (static_force);

        return Math.min(1, Math.max(-1, output));
    }

    // ... (rest of the setter methods remain the same)

    public void set_coeffs(double Kp, double Ki, double Kd, double Kf, double Ks) {
        this.P = Kp; this.I = Ki; this.D = Kd; this.F = Kf; this.S = Ks;
    }

    public void set_coeffs(double Kp, double Ki, double Kd, double Kf, double Ks, Follower follower) {
        this.P = Kp; this.I = Ki; this.D = Kd; this.F = Kf; this.S = Ks; flw = follower;
    }

    public void set_error_delta(double error_delta) { this.ERROR_DELTA = error_delta; }
    public void set_error_pos(double error_pos) { this.ERROR_POS = error_pos; }

}