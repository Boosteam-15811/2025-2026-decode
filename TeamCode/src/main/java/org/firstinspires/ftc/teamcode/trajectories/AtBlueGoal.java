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
@Autonomous(name = "AtBlueGoal", group = "Autonomous")
public class AtBlueGoal extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        Pose2d initialPose = new Pose2d(-49, -49 , Math.toRadians(235));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(-49,-40))
                .setTangent(360)
                .splineToLinearHeading(new Pose2d(-5, -30, Math.toRadians(270)), Math.toRadians(270))
                .lineToYConstantHeading(-50)
                .lineToYConstantHeading(-45)
                .turn(Math.toRadians(-90))
                .strafeTo(new Vector2d(9,-59))
                .waitSeconds(1.1)
                .setTangent(360)
                .splineToLinearHeading(new Pose2d(3 , 3 , Math.toRadians(225)), Math.toRadians(270))

                .splineToLinearHeading(new Pose2d(23, -30, Math.toRadians(270)), Math.toRadians(270))
                .lineToYConstantHeading(-48)

                .setTangent(360)
                .splineToLinearHeading(new Pose2d(3 , 3 , Math.toRadians(225)), Math.toRadians(270));




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
