package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

public class gabitzu {




    @TeleOp(name = "Servo Set Position", group = "Linear Opmode")
    public class ServoSetPositionExample extends LinearOpMode {

        private Servo myServo = null;

        @Override
        public void runOpMode() {
            // Inițializare servo din Hardware Map
            myServo = hardwareMap.get(Servo.class, "left_hand"); // Înlocuiește cu numele din Config



            waitForStart();

            if (opModeIsActive()) {
                // Setează poziția la 0.5 (la jumătatea cursei) la apăsarea butonului sau imediat
                myServo.setPosition(0.5);

                // Buclă principală
                while (opModeIsActive()) {
                    // Exemplu: Apăsarea butonului A setează poziția la 1.0, B la 0.0
                    if (gamepad1.a) {
                        myServo.setPosition(1.0);
                    } else if (gamepad1.b) {
                        myServo.setPosition(0.0);
                    }

                    telemetry.addData("Poziție Servo", myServo.getPosition());
                    telemetry.update();
                }
            }
        }
    }




}
