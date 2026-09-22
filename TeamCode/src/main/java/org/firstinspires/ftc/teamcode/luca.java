package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import util.VVGamepad;

@TeleOp(name="ceva")
public class luca extends LinearOpMode {
    public static DcMotor motor1;
    public static VVGamepad g1;




    public void runOpMode(){


        motor1=hardwareMap.get(DcMotor.class,"fatast");
        g1=new VVGamepad(gamepad1);





        while (opModeInInit()){

        }




        waitForStart();

        while (opModeIsActive()){

            motor1.setPower(g1.right_joystick.y);

         g1.update();
        }



    }




}
