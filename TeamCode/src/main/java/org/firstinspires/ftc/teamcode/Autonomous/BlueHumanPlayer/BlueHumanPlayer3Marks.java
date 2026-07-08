package org.firstinspires.ftc.teamcode.Autonomous.BlueHumanPlayer;

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
@Autonomous(name = "BlueHumanPlayer" , group = "Autonomous" , preselectTeleOp = "Blue")
public class BlueHumanPlayer3Marks extends LinearOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        DriveClass.init(hardwareMap);
        IntakeClass.init(hardwareMap);
        HoodAngleClass.init(hardwareMap);
        ShootingSpeedClass.init(hardwareMap);
        ShootingSpeedPID.init(hardwareMap);
        TransferWheelClass.init(hardwareMap);
        TurretHeadingClass.init(hardwareMap);
        PinpointTurretHeadingPID.init(hardwareMap);

        Pose2d initialPose = BlueHumanPlayerConstants.startingPos;
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder shoot1 = drive.actionBuilder(initialPose)
                .setTangent(90)
                .splineToLinearHeading(BlueHumanPlayerConstants.firstRow, Math.toRadians(270))
                .strafeTo(BlueHumanPlayerConstants.shootingPos);

        TrajectoryActionBuilder shoot2 = drive.actionBuilder(new Pose2d(62,-14.5 , Math.toRadians(270)))
                .strafeTo(BlueHumanPlayerConstants.secondRow)
                .strafeTo(BlueHumanPlayerConstants.collectSecondRow)
                .strafeTo(BlueHumanPlayerConstants.shootingPos);

        TrajectoryActionBuilder shoot3 = drive.actionBuilder(new Pose2d(62,-14.5 , Math.toRadians(270)))
                .strafeTo(BlueHumanPlayerConstants.thirdRow)
                .strafeTo(BlueHumanPlayerConstants.collectThirdRow)
                .strafeTo(BlueHumanPlayerConstants.backThirdRow)
                .strafeTo(BlueHumanPlayerConstants.shootingPos);

        TrajectoryActionBuilder leave = drive.actionBuilder(new Pose2d(62,-14.5 , Math.toRadians(270)))
                .strafeTo(BlueHumanPlayerConstants.leave);



        Action Shoot1 = shoot1.build();
        Action Shoot2 = shoot2.build();
        Action Shoot3 = shoot3.build();
        Action Leave = leave.build();


        waitForStart();

        Actions.runBlocking
                (

                    new ParallelAction
                    (
                    ShootingSpeedPID.pid(),
                    PinpointTurretHeadingPID.pid(),
                    TransferWheelClass.activate(),
                    IntakeClass.activate(),
                    HoodAngleClass.shootHumanPlayerDis(),
                    new SequentialAction
                    (
                            TurretHeadingClass.blueShootHumanPlayerAngle(),
                            ShootingSpeedClass.shootHumanPlayerDis(),
                            new SleepAction(4),
                            ShootingSpeedClass.disabled(),
                            Shoot1,
                            new SleepAction(3),
                            ShootingSpeedClass.disabled(),
                            Shoot2,
                            ShootingSpeedClass.shootHumanPlayerDis(),
                            new SleepAction(3),
                            ShootingSpeedClass.disabled(),
                            Shoot3,
                            ShootingSpeedClass.shootHumanPlayerDis(),
                            new SleepAction(3),
                            ShootingSpeedClass.disabled(),
                            Leave

                    )
                    )
                );

    }
}
