package org.firstinspires.ftc.teamcode.Autonomous.BlueClose;

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

@Autonomous (name = "blueClose", group = "Autonomous" , preselectTeleOp = "Blue")
public class BlueClose extends LinearOpMode
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

        Pose2d initialPose = BlueCloseConstants.startingPos;
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder shoot1 = drive.actionBuilder(initialPose)
                .strafeTo(BlueCloseConstants.startShootingPos);

        TrajectoryActionBuilder shoot2Part1 = drive.actionBuilder(new Pose2d(-16,-16, Math.toRadians(270)))
                .strafeTo(BlueCloseConstants.secondRow)
                .strafeTo(BlueCloseConstants.collectSecondRow);

        TrajectoryActionBuilder shoot2Part2 = drive.actionBuilder(new Pose2d(14,-58, Math.toRadians(270)))
                .strafeTo(BlueCloseConstants.shootingPos);

        TrajectoryActionBuilder gateShoot1 = drive.actionBuilder(new Pose2d(0,-11 , Math.toRadians(270)))
                .strafeToLinearHeading(BlueCloseConstants.collectGate, Math.toRadians(240))
                .strafeTo(BlueCloseConstants.Back)
                .strafeTo(BlueCloseConstants.collectGate);

        TrajectoryActionBuilder gateShoot1Part2 = drive.actionBuilder(new Pose2d(16,-61 , Math.toRadians(240)))
                .strafeToLinearHeading(BlueCloseConstants.shootingPos, Math.toRadians(270));


        TrajectoryActionBuilder gateShoot2 = drive.actionBuilder(new Pose2d(0,-11 , Math.toRadians(240)))
                .strafeToLinearHeading(BlueCloseConstants.collectGate, Math.toRadians(240))
                .strafeTo(BlueCloseConstants.Back)
                .strafeTo(BlueCloseConstants.collectGate);

        TrajectoryActionBuilder gateShoot2Part2 = drive.actionBuilder(new Pose2d(16,-61 , Math.toRadians(240)))
                .strafeToLinearHeading(BlueCloseConstants.shootingPos, Math.toRadians(270));

        TrajectoryActionBuilder gateShoot3 = drive.actionBuilder(new Pose2d(0,-11 , Math.toRadians(240)))
                .strafeToLinearHeading(BlueCloseConstants.collectGate, Math.toRadians(240))
                .strafeTo(BlueCloseConstants.Back)
                .strafeTo(BlueCloseConstants.collectGate);

        TrajectoryActionBuilder gateShoot3Part2 = drive.actionBuilder(new Pose2d(16,-61 , Math.toRadians(240)))
                .strafeToLinearHeading(BlueCloseConstants.shootingPos, Math.toRadians(270));

        TrajectoryActionBuilder leave = drive.actionBuilder(new Pose2d(0,-11 , Math.toRadians(240)))
                .strafeToLinearHeading(BlueCloseConstants.leave, Math.toRadians(270));



        Action Shoot1 = shoot1.build();
        Action Shoot2Part1 = shoot2Part1.build();
        Action Shoot2Part2 = shoot2Part2.build();
        Action GateShoot1 = gateShoot1.build();
        Action GateShoot2 = gateShoot2.build();
        Action GateShoot3 = gateShoot3.build();
        Action GateShoot1Part2 = gateShoot1Part2.build();
        Action GateShoot2Part2 = gateShoot2Part2.build();
        Action GateShoot3Part2 = gateShoot3Part2.build();

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
                    ShootingSpeedClass.shootClose1Dis(),
                    Shoot1,
                    new SleepAction(2),
                    ShootingSpeedClass.disabled(),
                    Shoot2Part1,
                    TurretHeadingClass.blueShootClose2Angle(),
                    Shoot2Part2,
                    ShootingSpeedClass.shootClose2Dis(),
                    new SleepAction(2),
                    ShootingSpeedClass.disabled(),
                    GateShoot1,
                    GateShoot1Part2,
                    ShootingSpeedClass.shootClose2Dis(),
                    new SleepAction(2),
                    ShootingSpeedClass.disabled(),
                    GateShoot2,
                    GateShoot2Part2,
                    ShootingSpeedClass.shootClose2Dis(),
                    new SleepAction(2),
                    ShootingSpeedClass.disabled(),
                    GateShoot3,
                    GateShoot3Part2,
                    ShootingSpeedClass.shootClose2Dis(),
                    new SleepAction(2),
                    ShootingSpeedClass.disabled(),
                    TurretHeadingClass.reset(),
                    Leave
                )
            )
        );
    }
}
