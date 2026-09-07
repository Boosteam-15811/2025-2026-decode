package org.firstinspires.ftc.teamcode.Autonomous.Red.Far.RedFar6;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.SubSystems.DriveTrain.DriveClass;
import org.firstinspires.ftc.teamcode.SubSystems.IntakeSystem.IntakeClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel.TransferWheelClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.PinpointTurretHeadingPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.TurretHeadingClass;

@Autonomous(name = "RedFar6", group = "Autonomous", preselectTeleOp = "RedFarTele")
public class RedFar6 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DriveClass.init(hardwareMap);
        IntakeClass.init(hardwareMap);
        HoodAngleClass.init(hardwareMap);
        ShootingSpeedClass.init(hardwareMap);
        ShootingSpeedPID.init(hardwareMap);
        TransferWheelClass.init(hardwareMap);
        TurretHeadingClass.init(hardwareMap);
        PinpointTurretHeadingPID.init(hardwareMap);

        Pose2d initialPose = RedFar6Constants.startingPos;
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder shoot1 = drive.actionBuilder(initialPose)
                .strafeTo(RedFar6Constants.firstRow)
                .strafeTo(RedFar6Constants.collectFirstRow)
                .strafeTo(RedFar6Constants.shootingPos);

        TrajectoryActionBuilder leave = drive.actionBuilder(new Pose2d(57, 14.5, Math.toRadians(90)))
                .strafeTo(RedFar6Constants.leave);


        Action Shoot1 = shoot1.build();
        Action Leave = leave.build();


        waitForStart();

        Actions.runBlocking
                (

                        new ParallelAction
                                (
                                        ShootingSpeedPID.pid(),
                                        PinpointTurretHeadingPID.pid(),
                                        TransferWheelClass.activate(),
                                        HoodAngleClass.shootDis(),
                                        new SequentialAction(
                                                new SleepAction(0.7),
                                                IntakeClass.activate()
                                        ),
                                        new SequentialAction
                                                (
                                                        TurretHeadingClass.redFarShootAngle1(),
                                                        ShootingSpeedClass.shootFarDis(),
                                                        new SleepAction(4.9),
                                                        ShootingSpeedClass.disabled(),
                                                        TurretHeadingClass.redFarShootAngle2(),
                                                        Shoot1,
                                                        ShootingSpeedClass.shootFarDis(),
                                                        new SleepAction(3.8),
                                                        ShootingSpeedClass.endAuto(),
                                                        TurretHeadingClass.endAutoAngle(),
                                                        Leave

                                                )
                                )
                );

    }
}
