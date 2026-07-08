package org.firstinspires.ftc.teamcode.Autonomous.BlueClose6Plus6;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.BlueClose.BlueCloseConstants;
import org.firstinspires.ftc.teamcode.Autonomous.BlueClose3Plus9.BlueClose3Plus9Constants;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.SubSystems.DriveTrain.DriveClass;
import org.firstinspires.ftc.teamcode.SubSystems.IntakeSystem.IntakeClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel.TransferWheelClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.PinpointTurretHeadingPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.TurretHeadingClass;

@Autonomous(name = "BlueClose6Plus6", group = "Autonomous" , preselectTeleOp = "Blue")

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


        TrajectoryActionBuilder leave = drive.actionBuilder(new Pose2d(-10, -16, Math.toRadians(270)))
                .strafeTo(BlueCloseConstants.leave);

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
                                        HoodAngleClass.shootClose2Dis(),
                                        new SequentialAction
                                                (
                                                        TurretHeadingClass.blueShootClose1Angle(),
                                                        Shoot,
                                                        ShootingSpeedClass.shootClose1Dis(),
                                                        new SleepAction(2),
                                                        ShootingSpeedClass.disabled(),
                                                        TurretHeadingClass.blueShootClose2Angle(),
                                                        ShootThirdRow,
                                                        ShootingSpeedClass.shootClose2Dis(),
                                                        new SleepAction(1),
                                                        ShootingSpeedClass.disabled(),
                                                        TurretHeadingClass.blueShootClose3Angle(),
                                                        CollectSecondRow,
                                                        new SleepAction(2),
                                                        ShootSecondRow,
                                                        ShootingSpeedClass.shootClose2Dis(),
                                                        new SleepAction(2),
                                                        ShootingSpeedClass.disabled(),
                                                        ShootFirstRow,
                                                        ShootingSpeedClass.shootClose2Dis(),
                                                        new SleepAction(2),
                                                        ShootingSpeedClass.disabled(),
                                                        TurretHeadingClass.blueEndAutoAngle(),
                                                        Leave
                                                )
                                )
                );
    }
}
