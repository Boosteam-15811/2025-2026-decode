package org.firstinspires.ftc.teamcode.Autonomous.BlueClose3Marks;

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
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.SubSystems.DriveTrain.DriveClass;
import org.firstinspires.ftc.teamcode.SubSystems.IntakeSystem.IntakeClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel.TransferWheelClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.PinpointTurretHeadingPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.TurretHeadingClass;
import org.firstinspires.ftc.teamcode.TeleOps.Main.Blue;

@Autonomous(name = "blueClose3Marks", group = "Autonomous" , preselectTeleOp = "Blue")

public class BlueClose3Marks extends LinearOpMode
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

        Pose2d initialPose = BlueCloseConstants3Marks.startingPos;
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder shoot = drive.actionBuilder(initialPose)
                .strafeTo(BlueCloseConstants3Marks.startShootingPos);

        TrajectoryActionBuilder shootThirdRow = drive.actionBuilder(new Pose2d(-16, -16, Math.toRadians(270)))
                .strafeTo(BlueCloseConstants3Marks.thirdRow)
                .strafeTo(BlueCloseConstants3Marks.collectThirdRow)
                .strafeTo(BlueCloseConstants3Marks.shootingPos);


        TrajectoryActionBuilder shootSecondRow = drive.actionBuilder(new Pose2d(0, -16, Math.toRadians(270)))
                .strafeTo(BlueCloseConstants3Marks.secondRow)
                .strafeTo(BlueCloseConstants3Marks.collectSecondRow)
                .strafeTo(BlueCloseConstants3Marks.shootingPos);

        TrajectoryActionBuilder shootFirstRow = drive.actionBuilder(new Pose2d(0, -16, Math.toRadians(270)))
                .strafeTo(BlueCloseConstants3Marks.firstRow)
                .strafeTo(BlueCloseConstants3Marks.collectFirstRow)
                .strafeTo(BlueCloseConstants3Marks.shootingPos);



        TrajectoryActionBuilder leave = drive.actionBuilder(new Pose2d(0, -16, Math.toRadians(270)))
                .strafeTo(BlueCloseConstants.leave);

        Action Shoot = shoot.build();
        Action ShootThirdRow = shootThirdRow.build();
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
                            ShootingSpeedClass.shootClose1Dis(),
                            Shoot,
                            new SleepAction(2),
                            ShootingSpeedClass.disabled(),
                            TurretHeadingClass.blueShootClose2Angle(),
                            ShootThirdRow,
                            ShootingSpeedClass.shootClose2Dis(),
                            new SleepAction(2),
                            ShootingSpeedClass.disabled(),
                            ShootSecondRow,
                            ShootingSpeedClass.shootClose2Dis(),
                            new SleepAction(2),
                            ShootingSpeedClass.disabled(),
                            ShootFirstRow,
                            ShootingSpeedClass.shootClose2Dis(),
                            new SleepAction(2),
                            ShootingSpeedClass.disabled(),
                            Leave

                            )
            )
        );
    }
}
