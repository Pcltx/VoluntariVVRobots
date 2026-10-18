package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import util.VVGamepad;

@Config
@TeleOp(name = "buliga")
public class LabutaCod extends LinearOpMode {
    public static boolean  lastState2 = false;
    public static boolean state2 = false;
    public static double posDeschis= 0.8;
    public static  double posInchis= 1;
    public static VVGamepad g1;
    public Servo labaServo;

    @Override
    public void runOpMode(){
        g1 = new VVGamepad(gamepad1);

        labaServo = hardwareMap.get(Servo.class,"labutza");

        while (opModeInInit()){

        }


        waitForStart();

        while (opModeIsActive()){
            g1.update();

            if(g1.triangle.pressed&& !lastState2){
                state2 = !state2;
            }

            if(state2) {
               labaServo.setPosition(posDeschis);
            }
            else {
                labaServo.setPosition(posInchis);
            }
        }


    }

}
