
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import util.VVPDFSvel;
import util.VVMotor;
@TeleOp
@Config
public class ShooterDavid extends LinearOpMode {
    public static boolean  lastState1= false;
    public static boolean state1 = false;

    public static DcMotor shooterDavid1;
   // public static DcMotor shooterDavid2;
    public static VVMotor encoderShooter;
    public static util.VVGamepad g1;
    public static util.VVGamepad g2;

    @Override//
    public void runOpMode() throws InterruptedException {

        g1 = new util.VVGamepad(gamepad1);
        g2 = new util.VVGamepad(gamepad2);
        shooterDavid1 = hardwareMap.get(DcMotor.class,"shooter");
        encoderShooter = new VVMotor(hardwareMap.get(DcMotorEx.class,"fatast"));
        shooterDavid1.setDirection(DcMotorSimple.Direction.REVERSE);
        encoderShooter.setDirection(DcMotorSimple.Direction.REVERSE, false);
        while (opModeInInit()) {}
        waitForStart();{}
        while (opModeIsActive()) {
            Shooter.update();
            g1.update();
            g2.update();


            if( g1.right_bumper.pressed&& !lastState1){
                state1 = !state1;
            }

            if(state1) {
                Shooter.trgPOS = 1250.0;
            }
            else {
                Shooter.trgPOS = 0.0;
            }

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