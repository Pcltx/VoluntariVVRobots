package org.firstinspires.ftc.teamcode;

import android.graphics.LinearGradient;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import util.VVGamepad;
import util.VVMotor;

@TeleOp(name ="buligaTransfer")
public class Intake extends LinearOpMode {
    public static boolean  lastState3 = false;
    public static boolean state3  = false;
    public static DcMotor intake;
    public static DcMotor transfer;
    public static VVGamepad g1;

    @Override
    public void runOpMode(){
        g1 = new VVGamepad(gamepad1);
        transfer = hardwareMap.get(DcMotor.class,"transfer");
        intake = hardwareMap.get(DcMotor.class,"intake");
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        transfer.setDirection(DcMotorSimple.Direction.REVERSE);
        while (opModeInInit()){

        }


        waitForStart();

        while (opModeIsActive()){
            g1.update();

            if(g1.right_trigger.pressed && !lastState3){
               state3 = !state3;
            }

            if(state3) {
                transfer.setPower(1);
                intake.setPower(1);
            }
            else {
                transfer.setPower(0);
                intake.setPower(0);
            }
            }
        }


    }


















