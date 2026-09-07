package org.firstinspires.ftc.teamcode.Autonomous.Red.Close.RedClose3Plus9;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
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

@Autonomous(name = "RedClose3Plus9", group = "Autonomous", preselectTeleOp = "RedCloseTele")

public class RedClose3Plus9 extends LinearOpMode {
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
                .strafeTo(
                        RedClose3Plus9Constants.startShootingPos,
                        new TranslationalVelConstraint(85)
                );

        TrajectoryActionBuilder shootThirdRow =
                drive.actionBuilder(new Pose2d(-6, 16, Math.toRadians(90)))
                        .strafeTo(RedClose3Plus9Constants.thirdRow)
                        .strafeTo(RedClose3Plus9Constants.collectThirdRow)
                        .strafeTo(RedClose3Plus9Constants.shootingPos);

        TrajectoryActionBuilder collectSecondRow =
                drive.actionBuilder(new Pose2d(-16, 16, Math.toRadians(90)))
                        .strafeTo(RedClose3Plus9Constants.secondRow)
                        .strafeTo(RedClose3Plus9Constants.collectSecondRow)
                        .strafeTo(RedClose3Plus9Constants.back)
                        .strafeTo(RedClose3Plus9Constants.gate);

        TrajectoryActionBuilder shootSecondRow =
                drive.actionBuilder(new Pose2d(8, 56, Math.toRadians(90)))
                        .strafeTo(
                                RedClose3Plus9Constants.shootingPos,
                                new TranslationalVelConstraint(85)
                        );

        TrajectoryActionBuilder shootFirstRow =
                drive.actionBuilder(new Pose2d(-6, 16, Math.toRadians(90)))
                        .strafeTo(
                                RedClose3Plus9Constants.firstRow,
                                new TranslationalVelConstraint(85)
                        )
                        .strafeTo(
                                RedClose3Plus9Constants.collectFirstRow,
                                new TranslationalVelConstraint(85)
                        )
                        .strafeTo(
                                RedClose3Plus9Constants.shootingPosThirdMiddle,
                                new TranslationalVelConstraint(85)
                        )
                        .strafeTo(
                                RedClose3Plus9Constants.shootingPosThird,
                                new TranslationalVelConstraint(85)
                        );


        TrajectoryActionBuilder leave = drive.actionBuilder(new Pose2d(-8, 16, Math.toRadians(90)))
                .strafeTo(RedClose3Plus9Constants.leave, new TranslationalVelConstraint(85));

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
                                        new SequentialAction(
                                                new SleepAction(0.8),
                                                IntakeClass.activate()
                                        ),
                                        HoodAngleClass.shootDis(),
                                        new SequentialAction
                                                (
                                                        TurretHeadingClass.redCloseShootAngle1(),
                                                        ShootingSpeedClass.shootCloseDis(),
                                                        Shoot,
                                                        new SleepAction(2.4),
                                                        ShootingSpeedClass.disabled(),
                                                        TurretHeadingClass.redCloseShootAngle2(),
                                                        CollectSecondRow,
                                                        new SleepAction(0.3),
                                                        ShootSecondRow,
                                                        ShootingSpeedClass.shootCloseDis(),
                                                        new SleepAction(2),
                                                        ShootingSpeedClass.disabled(),
                                                        ShootThirdRow,
                                                        ShootingSpeedClass.shootCloseDis(),
                                                        new SleepAction(2),
                                                        ShootingSpeedClass.disabled(),
                                                        TurretHeadingClass.redCloseShootAngle3(),
                                                        new SleepAction(0.2),
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
