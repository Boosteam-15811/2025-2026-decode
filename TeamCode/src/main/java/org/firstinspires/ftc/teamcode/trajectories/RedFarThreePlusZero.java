package org.firstinspires.ftc.teamcode.trajectories;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
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
@Autonomous(name = "3+0 far Red", group = "Autonomous")
public class RedFarThreePlusZero extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {


        IntakeClass.init(hardwareMap);
        HoodAngleClass.init(hardwareMap);
        ShootingSpeedClass.init(hardwareMap);
        TransferWheelClass.init(hardwareMap);
        ShootingSpeedPID.init(hardwareMap);
        //TurretHeadingClass.init(hardwareMap);
        //PinpointTurretHeadingPID.init(hardwareMap);

        Pose2d initialPose = new Pose2d(62, 17.8, Math.toRadians(180));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder shoot1 = drive.actionBuilder(initialPose)
                .strafeToLinearHeading(new Vector2d(58 , 17.8), Math.toRadians(158));

        TrajectoryActionBuilder leave1 = drive.actionBuilder(new Pose2d(57,17.8, Math.toRadians(158)))
                //leave the launch line
                .strafeToLinearHeading(new Vector2d(58 , 22), Math.toRadians(90));

        Action shoot = shoot1.build();
        Action leave = leave1.build();

        waitForStart();

        Actions.runBlocking(
                new ParallelAction(
                        ShootingSpeedPID.pid(),
                        TransferWheelClass.activate(),
                        IntakeClass.shootFar(),
                        //   PinpointTurretHeadingPID.pid(),
                        new SequentialAction(
                                shoot,
                                new SleepAction(3),
                                HoodAngleClass.farFromGoal(),
                                ShootingSpeedClass.far(),
                                new SleepAction(5),
                                ShootingSpeedClass.disabled(),
                                leave
                        )
                )
        );
    }
}
