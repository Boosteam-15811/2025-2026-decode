package org.firstinspires.ftc.teamcode.Autonomous.Red.Close.RedClose3Plus9;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.Blue.Close.BlueClose3Plus9.BlueClose3Plus9Constants;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.SubSystems.DriveTrain.DriveClass;
import org.firstinspires.ftc.teamcode.SubSystems.IntakeSystem.IntakeClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel.TransferWheelClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.PinpointTurretHeadingPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.TurretHeadingClass;

@Autonomous(name = "RedClose3Plus9", group = "Autonomous" , preselectTeleOp = "CompISR")

public class RedClose3Plus9 extends LinearOpMode
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

        Pose2d initialPose = RedClose3Plus9Constants.startingPos;
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder shoot = drive.actionBuilder(initialPose)
                .strafeTo(RedClose3Plus9Constants.startShootingPos);

        TrajectoryActionBuilder shootThirdRow = drive.actionBuilder(new Pose2d(-6, 16, Math.toRadians(90)))
                .strafeTo(RedClose3Plus9Constants.thirdRow)
                .strafeTo(RedClose3Plus9Constants.collectThirdRow)
                .strafeTo(RedClose3Plus9Constants.shootingPos);

        TrajectoryActionBuilder collectSecondRow = drive.actionBuilder(new Pose2d(-16, 16, Math.toRadians(90)))
                .strafeTo(RedClose3Plus9Constants.secondRow)
                .strafeTo(RedClose3Plus9Constants.collectSecondRow)
                .strafeTo(RedClose3Plus9Constants.back)
                .strafeTo(RedClose3Plus9Constants.gate);

        TrajectoryActionBuilder shootSecondRow = drive.actionBuilder(new Pose2d(8, 54, Math.toRadians(90)))
                .strafeTo(RedClose3Plus9Constants.shootingPos);

        TrajectoryActionBuilder shootFirstRow = drive.actionBuilder(new Pose2d(-6, 16, Math.toRadians(90)))
                .strafeTo(RedClose3Plus9Constants.firstRow)
                .strafeTo(RedClose3Plus9Constants.collectFirstRow)
                .strafeTo(RedClose3Plus9Constants.shootingPosThird);


        TrajectoryActionBuilder leave = drive.actionBuilder(new Pose2d(-35, 12, Math.toRadians(90)))
                .strafeTo(RedClose3Plus9Constants.leave);

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
                    TurretHeadingClass.redCloseShootAngle1(),
                    ShootingSpeedClass.shootCloseDis(),
                    Shoot,
                    new SleepAction(3),
                    ShootingSpeedClass.disabled(),
                    TurretHeadingClass.redCloseShootAngle2(),
                    CollectSecondRow,
                    new SleepAction(1),
                    ShootSecondRow,
                    ShootingSpeedClass.shootCloseDis(),
                    new SleepAction(2),
                    ShootingSpeedClass.disabled(),
                    TurretHeadingClass.redCloseShootAngle3(),
                    new SleepAction(1.2),
                    ShootThirdRow,
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
