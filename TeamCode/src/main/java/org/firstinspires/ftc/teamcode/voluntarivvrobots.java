package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Simple Motor Control", group = "Test")
public class voluntarivvrobots extends LinearOpMode {

    public DcMotor buliga;

    @Override
    public void runOpMode() {
        buliga = hardwareMap.get(DcMotor.class, "luca");
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            double motorPower = -gamepad1.left_stick_y;
            buliga.setPower(motorPower);

            telemetry.addData("Motor Power", motorPower);
            telemetry.update();
        }
    }
}