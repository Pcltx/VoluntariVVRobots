package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import util.VVGamepad;

@TeleOp(name = "bdrive")
public class RobotOrientatedDrive extends LinearOpMode {

    public static VVGamepad g1;
    public static DcMotor fatadr;
    public static DcMotor fatast;
    public static DcMotor spatedr;
    public static DcMotor spatest;




    @Override
    public void runOpMode() {
        g1 = new VVGamepad(gamepad1);
         fatast = hardwareMap.dcMotor.get("fatast");
         fatadr = hardwareMap.dcMotor.get("fatadr");
        spatest = hardwareMap.dcMotor.get("spatest");
        spatedr = hardwareMap.dcMotor.get("spatedr");

        fatast.setDirection(DcMotor.Direction.REVERSE);
       spatest.setDirection(DcMotor.Direction.REVERSE);


        while (opModeInInit()) {

        }


        waitForStart();

        while (opModeIsActive()) {
            double y = -g1.left_joystick.y;
            double x = g1.left_joystick.x * 1.1;
            double rx = g1.right_joystick.x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double fatastp = (y + x + rx) / denominator;
            double spatestp = (y - x + rx) / denominator;
            double fatadrp = (y - x - rx) / denominator;
            double spatedrp = (y + x - rx) / denominator;

            fatadr.setPower(fatadrp);
            spatest.setPower(spatestp);
            fatast.setPower(fatastp);
            spatedr.setPower(spatedrp);

        }
    }
}
