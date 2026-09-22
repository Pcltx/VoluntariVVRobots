package util;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class VVMotor {
    private double last_power = 0.0;
    private double override_delta = 0.005;
    private DcMotorEx internal, encoder;
    private double internal_dir = 1.0;
    private double denominator = 1.0;
    private ElapsedTime timer = new ElapsedTime();
    DcMotorSimple.Direction encoder_direction = DcMotorSimple.Direction.FORWARD;

    public boolean is_valid() {
        return internal != null;
    }

    public void setPower(double power) {
        if (internal == null) return;
        power *= internal_dir;
        if (Math.abs(power-last_power) >= override_delta) {
            internal.setPower(power);
            last_power = power;
            timer.reset();
        }
    }

    public double getPower() {
        return last_power * internal_dir;
    }

    public double getPosition() {
        if (encoder == null) return 0;
        double sign = encoder_direction.equals(encoder.getDirection()) ? 1 : -1;
        return encoder.getCurrentPosition() / denominator * sign;
    }

    public VVMotor setDenominator(double new_denominator) {
        denominator = new_denominator;
        return this;
    }

    public boolean is_cooked() {
        return internal.isOverCurrent();
    }

    public void setOverrideDelta(double delta) {
        override_delta = delta;
    }

    public double cache_time() {
        return timer.seconds();
    }

    public VVMotor(DcMotorEx x){
        internal = x;
        encoder = x;
        internal.setCurrentAlert(7.2, CurrentUnit.AMPS);
    }
    public VVMotor(DcMotorEx x, DcMotorEx y){
        internal = x;
        encoder = y;
        internal.setCurrentAlert(7.2, CurrentUnit.AMPS);
    }

    public VVMotor() {

    }

    public void assign(DcMotorEx smth) {
        internal = smth; internal.setCurrentAlert(7.2, CurrentUnit.AMPS);
    }

    public void setMode(DcMotor.RunMode runMode) {
        if (internal == null) return;
        internal.setMode(runMode);
        internal.setCurrentAlert(7.2, CurrentUnit.AMPS);
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior zeroPowerBehavior) {
        if (internal == null) return;
        internal.setZeroPowerBehavior(zeroPowerBehavior);
        internal.setCurrentAlert(7.2, CurrentUnit.AMPS);
    }

    public void setDirection(DcMotorSimple.Direction direction, boolean inverted_encoder) {
        if (internal == null) return;
        internal_dir = direction.equals(DcMotorSimple.Direction.FORWARD) ? 1 : -1;
        encoder_direction = inverted_encoder ? direction.inverted() : direction;
    }

    public double getVelocity() {
        if (encoder == null) return 0;
        double sign = encoder_direction.equals(encoder.getDirection()) ? 1 : -1;
        return encoder.getVelocity() * sign;
    }

    public double getAngularVelocity() {
        if (encoder == null) return 0;
        double sign = encoder_direction.equals(encoder.getDirection()) ? 1 : -1;
        return encoder.getVelocity(AngleUnit.DEGREES) * sign;
    }

    public double getAmps() {
        if (internal == null) return 0;
        return internal.getCurrent(CurrentUnit.AMPS);
    }
}