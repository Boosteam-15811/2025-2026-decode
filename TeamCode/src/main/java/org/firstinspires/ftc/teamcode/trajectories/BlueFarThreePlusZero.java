package org.firstinspires.ftc.teamcode.trajectories;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.SubSystems.IntakeSystem.IntakeClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel.TransferWheelClass;

@Config
@Autonomous(name = "3+0 far Blue", group = "Autonomous")
public class BlueFarThreePlusZero extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        Pose2d initialPose = new Pose2d(62, -17.8, Math.toRadians(180));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder shoot1 = drive.actionBuilder(initialPose)
                .strafeToLinearHeading(new Vector2d(57 , -35), Math.toRadians(205));

        TrajectoryActionBuilder leave1 = drive.actionBuilder(initialPose)
                //leave the launch line
                .strafeToLinearHeading(new Vector2d(57 , -35), Math.toRadians(270));

        Action shoot = shoot1.build();
        Action leave = leave1.build();

        waitForStart();

        Actions.runBlocking(
                new ParallelAction(
                        ShootingSpeedPID.pid(),
                        TransferWheelClass.activate(),
                        IntakeClass.shootFar(),
                        new SequentialAction(
                                shoot,
                                new SleepAction(3),
                                ShootingSpeedClass.far(),
                                HoodAngleClass.farFromGoal(),
                                new SleepAction(5),
                                ShootingSpeedClass.disabled(),
                                leave
                        )
                )
        );
    }
}
