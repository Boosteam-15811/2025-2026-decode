package org.firstinspires.ftc.teamcode.Autonomous.Blue.Far.BlueFar6;

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

@Autonomous(name = "BlueFar6", group = "Autonomous", preselectTeleOp = "BlueFarTele")
public class BlueFar6 extends LinearOpMode {
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

        Pose2d initialPose = BlueFar6Constants.startingPos;
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder shoot1 = drive.actionBuilder(initialPose)
                .strafeTo(BlueFar6Constants.firstRow)
                .strafeTo(BlueFar6Constants.collectFirstRow)
                .strafeTo(BlueFar6Constants.shootingPos);

        TrajectoryActionBuilder leave =
                drive.actionBuilder(new Pose2d(57, -14.5, Math.toRadians(270)))
                        .strafeTo(BlueFar6Constants.leave);


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
                                                        TurretHeadingClass.blueFarShootAngle1(),
                                                        ShootingSpeedClass.shootFarDis(),
                                                        new SleepAction(12),
                                                        ShootingSpeedClass.disabled(),
                                                        TurretHeadingClass.blueFarShootAngle2(),
                                                        Shoot1,
                                                        ShootingSpeedClass.shootFarDis(),
                                                        new SleepAction(10),
                                                        ShootingSpeedClass.endAuto(),
                                                        TurretHeadingClass.endAutoAngle(),
                                                        Leave

                                                )
                                )
                );

    }
}
