package org.firstinspires.ftc.teamcode;
import com.pedropathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import static com.pedropathing.api.Paths.*;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
@Autonomous
public class PrimaAutonomie extends OpMode {
    private Follower follower;
    private final PoseFactory p = PoseFactory.degrees();
    private final Pose startPose = p.of(132.700, 79, 180);
    private final Pose controlPose1 = p.of(99.4, 104.2, 180);
    private final Pose garden = p.of(132.8, 130.2, 90);
    private final Pose scorePose = p.of(48, 48, -90);
    private final Pose controlPose2= p.of(129, 111.4, 180);
    private final Pose controlPose3= p.of(94.3, 25.9, 180);
    private final Pose parkPose = p.of(130.6, 37.3, 180);
    private Path startTograden(){
        return curve(startPose, controlPose1, garden).linear(startPose, garden);
    }

    private Path gradenToscore() {
        return line(garden, scorePose).linear(garden, scorePose);
    }

    private Path park() {
        return curve(startPose, controlPose2,controlPose3,parkPose).linear(startPose, parkPose);
    }

    private Command autoRoutine() {
        return sequential(
                follow(follower, startTograden()),
                // pt intake
                follow(follower, gradenToscore()),

                //pt shooter
                follow(follower, park())
        );
    }

    @Override
    public void init() {
        Scheduler.reset();

        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
        follower.update();

    }

    @Override
    public void start() {
        schedule(autoRoutine());



    }
    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();


    }
}
