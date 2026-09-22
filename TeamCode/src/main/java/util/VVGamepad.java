package util;

import com.qualcomm.robotcore.hardware.Gamepad;

public class VVGamepad {
    private Gamepad internal_gamepad;

    public class button {
        public boolean pressed, held;
        public void update_held(boolean held_value) {
            pressed = held_value && !held;
            held = held_value;
        }
        button() {
            pressed = held = false;
        }
    }

    public class joystick {
        public double x,y;

        joystick() {
            x = y = 0;
        }
    }

    public button dpad_up = new button();
    public button dpad_right = new button();
    public button dpad_down = new button();
    public button dpad_left = new button();
    public button triangle = new button();
    public button circle = new button();
    public button cross = new button();
    public button square = new button();
    public button options = new button();
    public button share = new button();
    public button left_bumper = new button();
    public button right_bumper = new button();
    public button ps = new button();
    public button left_joystick_button = new button();
    public button right_joystick_button = new button();
    public button left_finger_button = new button();
    public button right_finger_button = new button();
    public button left_trigger = new button();
    public button right_trigger = new button();

    public joystick left_joystick = new joystick();
    public joystick right_joystick = new joystick();
    public joystick left_finger = new joystick();
    public joystick right_finger = new joystick();

    public VVGamepad(Gamepad gamepad) {
        internal_gamepad = gamepad;
    }

    public Gamepad get_internal() {
        return internal_gamepad;
    }

    public void update() {
        left_joystick.x = internal_gamepad.left_stick_x;
        left_joystick.y = internal_gamepad.left_stick_y;
        right_joystick.x = internal_gamepad.right_stick_x;
        right_joystick.y = internal_gamepad.right_stick_y;
        left_finger.x = (internal_gamepad.touchpad_finger_1_x + 1.0)/2.0;
        left_finger.y = (internal_gamepad.touchpad_finger_1_y + 1.0)/2.0;
        right_finger.x = (internal_gamepad.touchpad_finger_2_x + 1.0)/2.0;
        right_finger.y = (internal_gamepad.touchpad_finger_2_y + 1.0)/2.0;

        dpad_up.update_held(internal_gamepad.dpad_up);
        dpad_right.update_held(internal_gamepad.dpad_right);
        dpad_down.update_held(internal_gamepad.dpad_down);
        dpad_left.update_held(internal_gamepad.dpad_left);
        triangle.update_held(internal_gamepad.triangle);
        circle.update_held(internal_gamepad.circle);
        cross.update_held(internal_gamepad.cross);
        square.update_held(internal_gamepad.square);
        options.update_held(internal_gamepad.options);
        share.update_held(internal_gamepad.share);
        left_bumper.update_held(internal_gamepad.left_bumper);
        right_bumper.update_held(internal_gamepad.right_bumper);
        ps.update_held(internal_gamepad.ps);
        left_joystick_button.update_held(internal_gamepad.left_stick_button);
        right_joystick_button.update_held(internal_gamepad.right_stick_button);
        left_finger_button.update_held(internal_gamepad.touchpad_finger_1);
        right_finger_button.update_held(internal_gamepad.touchpad_finger_2);

        left_trigger.update_held(internal_gamepad.left_trigger > 0.05);
        right_trigger.update_held(internal_gamepad.right_trigger > 0.05);
    }
}
