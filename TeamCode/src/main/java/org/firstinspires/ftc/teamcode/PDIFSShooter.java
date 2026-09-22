
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import util.VVPDFS;

@TeleOp
public class SliderDavid extends LinearOpMode {
    public static DcMotor sliderDavid1;
    public static DcMotor sliderDavid2;
    public static DcMotor encoderSlider;
    public static util.VVGamepad g1;
    public static util.VVGamepad g2;

    @Override
    public void runOpMode() throws InterruptedException {

        g1 = new util.VVGamepad(gamepad1);
        g2 = new util.VVGamepad(gamepad2);
        sliderDavid1 = hardwareMap.get(DcMotor.class,"slider1");
        sliderDavid2 = hardwareMap.get(DcMotor.class,"slider2");
        encoderSlider = hardwareMap.get(DcMotor.class,"fatast");
        sliderDavid1.setDirection(DcMotorSimple.Direction.REVERSE);
        sliderDavid2.setDirection(DcMotorSimple.Direction.REVERSE);
        encoderSlider.setDirection(DcMotorSimple.Direction.REVERSE);
        while (opModeInInit()) {}
        waitForStart();{}
        while (opModeIsActive()) {

            if (g1.left_trigger.pressed) {
                Sliders.trgPOS=0;
            }

            if (g1.right_trigger.pressed) {
                Sliders.trgPOS=40000;
            }


            if (g1.dpad_up.pressed) {
                Sliders.trgPOS +=10000;
            }


            if (g1.dpad_down.pressed) {
                Sliders.trgPOS -= 10000;
            }

            telemetry.addData("Destination", Sliders.trgPOS);
            telemetry.addData("Current Position", Sliders.actPOS);
            Sliders.update();
            g1.update();
            telemetry.update();
        }

    }
    public static class Sliders {
        public static double kP,kD,kF,kS,actPOS,trgPOS;
        public static util.VVPDFS Movement= new VVPDFS();

        public static void update() {
            actPOS= encoderSlider.getCurrentPosition();
            Movement.set_coeffs(kP,kD,kF,kS);
            Movement.set_max_pos(57000);
            Movement.set_min_pos(0);
            double power=Movement.update(actPOS, trgPOS);
            sliderDavid1.setPower(power);


        }


    }
}