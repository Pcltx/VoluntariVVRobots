package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import util.VVGamepad;
import util.VVMotor;
import util.VVPDFSvel;

@TeleOp
public class BuligaFullTeleOp extends LinearOpMode {
    public static boolean  lastState3 = false;
    public static boolean state3  = false;
    public static DcMotor intake;
    public static DcMotor transfer;
    public static VVGamepad g1;
    public static boolean  lastState2 = false;
    public static boolean state2 = false;
    public static double posDeschis= 0.8;
    public static  double posInchis= 1;
    public static DcMotor fatadr;
    public static DcMotor fatast;
    public static DcMotor spatedr;
    public static DcMotor spatest;
    public Servo labaServo;
    public static boolean  lastState1= false;
    public static boolean state1 = false;

    public static DcMotor shooterDavid1;
    // public static DcMotor shooterDavid2;
    public static VVMotor encoderShooter;

    @Override
    public void runOpMode(){
        g1 = new VVGamepad(gamepad1);
        transfer = hardwareMap.get(DcMotor.class,"transfer");
        intake = hardwareMap.get(DcMotor.class,"intake");
        intake.setDirection(DcMotorSimple.Direction.REVERSE);
        transfer.setDirection(DcMotorSimple.Direction.REVERSE);
        labaServo = hardwareMap.get(Servo.class,"labutza");
        shooterDavid1 = hardwareMap.get(DcMotor.class,"shooter");
        encoderShooter = new VVMotor(hardwareMap.get(DcMotorEx.class,"fatast"));
        shooterDavid1.setDirection(DcMotorSimple.Direction.REVERSE);
        encoderShooter.setDirection(DcMotorSimple.Direction.REVERSE, false);
        fatast = hardwareMap.dcMotor.get("fatast");
        fatadr = hardwareMap.dcMotor.get("fatadr");
        spatest = hardwareMap.dcMotor.get("spatest");
        spatedr = hardwareMap.dcMotor.get("spatedr");

        fatast.setDirection(DcMotor.Direction.REVERSE);
        spatest.setDirection(DcMotor.Direction.REVERSE);
        while (opModeInInit()){

        }


        waitForStart();

        while (opModeIsActive()){
            g1.update();
          Shooter.update();
            if(g1.right_trigger.pressed && !lastState3){
                state3 = !state3;
            }

            if(state3) {
                transfer.setPower(0.8);
                intake.setPower(1);
            }
            else {
                transfer.setPower(0);
                intake.setPower(0);
            }

            if(g1.triangle.pressed&& !lastState2){
                state2 = !state2;
            }

            if(state2) {
                labaServo.setPosition(posDeschis);
            }
            else {
                labaServo.setPosition(posInchis);
            }

            if( g1.right_bumper.pressed&& !lastState1){
                state1 = !state1;
            }

            if(state1) {
                Shooter.trgPOS = 1600;
            }
            else {
               Shooter.trgPOS = 0.0;
            }

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


    @Config
    public static class Shooter {
        public static double kP=0.0004,kD=0.0003,kF=0,kS=0.1,actPOS=0,trgPOS=0;
        public static util.VVPDFSvel Movement= new VVPDFSvel();

        public static void update() {
            actPOS= encoderShooter.getVelocity();
            Movement.set_coeffs(kP,kD,kF,kS);
            double power=Movement.update(actPOS, trgPOS);
            shooterDavid1.setPower(power);


        }


    }





















}
