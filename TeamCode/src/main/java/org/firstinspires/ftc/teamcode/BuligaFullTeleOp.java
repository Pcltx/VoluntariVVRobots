package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Timer;

import util.VVGamepad;
import util.VVMotor;
import util.VVPDFSvel;

@TeleOp
public class BuligaFullTeleOp extends LinearOpMode {


    public enum States {
        HOME,
        INTAKE,
        SHOOT
    }

    private States current = States.HOME;
public ElapsedTime timer =new ElapsedTime();
public boolean timerStarted=false;
    public static DcMotor intake;
    public static DcMotor transfer;
    public static VVGamepad g1;
    public static DcMotor fatadr;
    public static DcMotor fatast;
    public static DcMotor spatedr;
    public static DcMotor spatest;
    public static DcMotor shooterDavid1;
    public static DcMotor shooterDavid2;

    public static VVMotor encoderShooter;
    public Servo labaServo;
    public static double posDeschis = 0;
    public static double posInchis = 0.12;
    public static double intakePower = 1.0;
    public static double transferPower = 0.8;

    @Override
    public void runOpMode() {
        g1 = new VVGamepad(gamepad1);
        transfer = hardwareMap.get(DcMotor.class, "transfer");
        intake = hardwareMap.get(DcMotor.class, "intake");
        intake.setDirection(DcMotorSimple.Direction.FORWARD);
        transfer.setDirection(DcMotorSimple.Direction.REVERSE);
        labaServo = hardwareMap.get(Servo.class, "labutza");
        shooterDavid1 = hardwareMap.get(DcMotor.class, "dreapta");
        shooterDavid2 = hardwareMap.get(DcMotor.class, "stanga");
        encoderShooter = new VVMotor(hardwareMap.get(DcMotorEx.class, "fatast"));
        shooterDavid1.setDirection(DcMotorSimple.Direction.REVERSE);
        encoderShooter.setDirection(DcMotorSimple.Direction.REVERSE, false);
        fatast = hardwareMap.dcMotor.get("fatast");
        fatadr = hardwareMap.dcMotor.get("fatadr");
        spatest = hardwareMap.dcMotor.get("spatest");
        spatedr = hardwareMap.dcMotor.get("spatedr");
        fatast.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        spatest.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fatadr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        spatedr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        fatast.setDirection(DcMotor.Direction.REVERSE);
        spatest.setDirection(DcMotor.Direction.REVERSE);

        while (opModeInInit()) {
        }

        waitForStart();

        while (opModeIsActive()) {
            g1.update();
            Shooter.update();

            switch (current) {
                case HOME:
                    transfer.setPower(0);
                    intake.setPower(0);
                    Shooter.trgPOS = 0;
                    labaServo.setPosition(posInchis);
                    if (g1.right_bumper.pressed) {
                        current = States.SHOOT;
                    }
                    if (g1.right_trigger.pressed) {
                        current = States.INTAKE;
                    }
                    break;

                case INTAKE:
                    transfer.setPower(transferPower);
                    intake.setPower(intakePower);
                    Shooter.trgPOS = 0;
                    labaServo.setPosition(posInchis);
                    if (g1.right_trigger.pressed) {
                        current = States.HOME;
                    }
                    if (g1.right_bumper.pressed) {
                        current = States.SHOOT;
                    }
                    break;

                case SHOOT:
                    transfer.setPower(transferPower);
                    if(!timerStarted) {
                        timer.reset();
                        timerStarted = true;
                    }
                    if (timerStarted && timer.seconds()>2)
                    labaServo.setPosition(posDeschis);
                    if (g1.right_bumper.pressed) {
                        current = States.HOME;
                        timerStarted = false;
                    }
                    if (g1.right_trigger.pressed) {
                        current = States.INTAKE;
                        timerStarted = false;
                    }
                    if(g1.dpad_up.pressed) {
                        Shooter.configPos= Shooter.configPos+ 50;
                    }

                    if(g1.dpad_down.pressed) {
                        Shooter.configPos= Shooter.configPos-50;
                    }



                    Shooter.trgPOS = Shooter.configPos;
                    break;

                default:
                    current = States.HOME;
                    break;
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
        public static double configPos = 1700;
        public static double kP = 0.0004;
        public static double kD = 0.0003;
        public static double kF = 0;
        public static double kS = 0.1;

        public static double actPOS = 0, trgPOS = 0;
        public static util.VVPDFSvel Movement = new VVPDFSvel();

        public static void update() {
            actPOS = encoderShooter.getVelocity();
            Movement.set_coeffs(kP, kD, kF, kS);
            double power = Movement.update(actPOS, trgPOS);
            shooterDavid1.setPower(power);
            shooterDavid2.setPower(power);
        }
    }
}