
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
    public static DcMotor shooterDavid1;
   // public static DcMotor shooterDavid2;
    public static VVMotor encoderShooter;
    public static util.VVGamepad g1;
    public static util.VVGamepad g2;

    @Override
    public void runOpMode() throws InterruptedException {

        g1 = new util.VVGamepad(gamepad1);
        g2 = new util.VVGamepad(gamepad2);
        shooterDavid1 = hardwareMap.get(DcMotor.class,"shooter");
      //shooter  shooterDavid2 = hardwareMap.get(DcMotor.class,"slider2");
        encoderShooter = new VVMotor(hardwareMap.get(DcMotorEx.class,"fatast"));
        shooterDavid1.setDirection(DcMotorSimple.Direction.REVERSE);
       //shooter shooterDavid2.setDirection(DcMotorSimple.Direction.REVERSE);
        encoderShooter.setDirection(DcMotorSimple.Direction.REVERSE, true);
        while (opModeInInit()) {}
        waitForStart();{}
        while (opModeIsActive()) {

            if (g1.left_trigger.pressed) {
                Shooter.trgPOS=0;
            }

            if (g1.right_trigger.pressed) {
                Shooter.trgPOS=40000;
            }


            if (g1.dpad_up.pressed) {
                Shooter.trgPOS +=10000;
            }


            if (g1.dpad_down.pressed) {
                Shooter.trgPOS -= 10000;
            }

            telemetry.addData("Destination", Shooter.trgPOS);
            telemetry.addData("Current Position", Shooter.actPOS);
            Shooter.update();
            g1.update();
            telemetry.update();
        }

    }
@Config
    public static class Shooter {
        public static double kP=0,kD=0,kF=0,kS=0,actPOS=0,trgPOS=0;
        public static util.VVPDFSvel Movement= new VVPDFSvel();

        public static void update() {
            actPOS= encoderShooter.getVelocity();
            Movement.set_coeffs(kP,kD,kF,kS);
            double power=Movement.update(actPOS, trgPOS);
            shooterDavid1.setPower(power);


        }


    }
}