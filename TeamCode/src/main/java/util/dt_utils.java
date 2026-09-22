package util;

public class dt_utils {
    public static double calc_turn_angle(double current_angle, double desired_angle) {
        if (desired_angle < current_angle) desired_angle += Math.PI*2;
        double angle_diff = desired_angle - current_angle;
        if (Math.abs(angle_diff) > Math.PI) return angle_diff - Math.PI * 2 * Math.signum(angle_diff);
        return angle_diff;
    }


}
