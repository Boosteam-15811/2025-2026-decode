package org.firstinspires.ftc.teamcode.Autonomous.Blue.Close.BlueClose6Plus6;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.Blue.Close.BlueCloseGate.BlueCloseGateConstants;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.SubSystems.DriveTrain.DriveClass;
import org.firstinspires.ftc.teamcode.SubSystems.IntakeSystem.IntakeClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel.TransferWheelClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.PinpointTurretHeadingPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.TurretHeadingClass;

@Autonomous(name = "BlueClose6Plus6", group = "Autonomous" , preselectTeleOp = "CompISR")

public class BlueClose6Plus6 extends LinearOpMode
{
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

        Pose2d initialPose = BlueClose6Plus6Constants.startingPos;
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder shoot = drive.actionBuilder(initialPose)
                .strafeTo(BlueClose6Plus6Constants.startShootingPos);

        TrajectoryActionBuilder shootThirdRow = drive.actionBuilder(new Pose2d(-16, -16, Math.toRadians(270)))
                .strafeTo(BlueClose6Plus6Constants.thirdRow)
                .strafeTo(BlueClose6Plus6Constants.collectThirdRow)
                .strafeTo(BlueClose6Plus6Constants.shootingPos);


        TrajectoryActionBuilder collectSecondRow = drive.actionBuilder(new Pose2d(-6, -16, Math.toRadians(270)))
                .strafeTo(BlueClose6Plus6Constants.secondRow)
                .strafeTo(BlueClose6Plus6Constants.collectSecondRow)
                .strafeTo(BlueClose6Plus6Constants.back)
                .strafeTo(BlueClose6Plus6Constants.gate);

        TrajectoryActionBuilder shootSecondRow = drive.actionBuilder(new Pose2d(8, -54, Math.toRadians(270)))
                .strafeTo(BlueClose6Plus6Constants.shootingPos);

        TrajectoryActionBuilder shootFirstRow = drive.actionBuilder(new Pose2d(-6, -16, Math.toRadians(270)))
                .strafeTo(BlueClose6Plus6Constants.firstRow)
                .strafeTo(BlueClose6Plus6Constants.collectFirstRow)
                .strafeTo(BlueClose6Plus6Constants.shootingPosThird);

        TrajectoryActionBuilder leave = drive.actionBuilder(new Pose2d(-21, -10, Math.toRadians(270)))
                .strafeTo(BlueClose6Plus6Constants.leave);

        Action Shoot = shoot.build();
        Action ShootThirdRow = shootThirdRow.build();
        Action CollectSecondRow = collectSecondRow.build();
        Action ShootSecondRow = shootSecondRow.build();
        Action ShootFirstRow = shootFirstRow.build();
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
                HoodAngleClass.shootDis(),
                new SequentialAction
                (
                    TurretHeadingClass.blueCloseShootAngle1(),
                    Shoot,
                    ShootingSpeedClass.shootCloseDis(),
                    new SleepAction(2),
                    ShootingSpeedClass.disabled(),
                    TurretHeadingClass.blueCloseShootAngle2(),
                    ShootThirdRow,
                    ShootingSpeedClass.shootCloseDis(),
                    new SleepAction(2),
                    ShootingSpeedClass.disabled(),
                    TurretHeadingClass.blueCloseShootAngle3(),
                    CollectSecondRow,
                    new SleepAction(1.2),
                    ShootSecondRow,
                    ShootingSpeedClass.shootCloseDis(),
                    new SleepAction(2),
                    ShootingSpeedClass.disabled(),
                    ShootFirstRow,
                    ShootingSpeedClass.shootCloseDis(),
                    new SleepAction(2),
                    ShootingSpeedClass.endAuto(),
                    TurretHeadingClass.endAutoAngle(),
                    Leave
                )
            )
        );
    }
}
