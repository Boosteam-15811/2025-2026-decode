package org.firstinspires.ftc.teamcode.trajectories;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous(name = "AtRedGoal", group = "Autonomous")
public class AtRedGoal extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        Pose2d initialPose = new Pose2d(-49, 49 , Math.toRadians(125));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-49,40))
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(-7, 29, Math.toRadians(90)), Math.toRadians(90))
                .lineToYConstantHeading(47)
                .lineToYConstantHeading(42)
                .turn(Math.toRadians(90)).strafeTo(new Vector2d(7,55))
                .waitSeconds(1.1)
                .setTangent(180)
                .splineToLinearHeading(new Pose2d(-6, -6 , Math.toRadians(135)), Math.toRadians(135))

                .splineToLinearHeading(new Pose2d(20, 22, Math.toRadians(90)), Math.toRadians(90))
                .lineToYConstantHeading(45)

                .setTangent(180)
                .splineToLinearHeading(new Pose2d(-7, -7 , Math.toRadians(130)), Math.toRadians(130));

        Action traj = tab1.build();

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        traj
                )
        );
    }
}
