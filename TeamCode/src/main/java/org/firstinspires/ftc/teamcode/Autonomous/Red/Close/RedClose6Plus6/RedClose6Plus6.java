package org.firstinspires.ftc.teamcode.Autonomous.Red.Close.RedClose6Plus6;

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

@Autonomous(name = "RedClose6Plus6", group = "Autonomous", preselectTeleOp = "RedCloseTele")

public class RedClose6Plus6 extends LinearOpMode {
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

        Pose2d initialPose = RedClose6Plus6Constants.startingPos;
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder shoot = drive.actionBuilder(initialPose)
                .strafeTo(RedClose6Plus6Constants.startShootingPos, new TranslationalVelConstraint(85));

        TrajectoryActionBuilder shootThirdRow = drive.actionBuilder(new Pose2d(-16, 16, Math.toRadians(90)))
                .strafeTo(RedClose6Plus6Constants.thirdRow)
                .strafeTo(RedClose6Plus6Constants.collectThirdRow)
                .strafeTo(RedClose6Plus6Constants.shootingPos);

        TrajectoryActionBuilder collectSecondRow = drive.actionBuilder(new Pose2d(-6, 16, Math.toRadians(90)))
                .strafeTo(RedClose6Plus6Constants.secondRow)
                .strafeTo(RedClose6Plus6Constants.collectSecondRow)
                .strafeTo(RedClose6Plus6Constants.back)
                .strafeTo(RedClose6Plus6Constants.gate);

        TrajectoryActionBuilder shootSecondRow = drive.actionBuilder(new Pose2d(8, 56, Math.toRadians(90)))
                .strafeTo(RedClose6Plus6Constants.shootingPos, new TranslationalVelConstraint(85));

        TrajectoryActionBuilder shootFirstRow = drive.actionBuilder(new Pose2d(-6, 16, Math.toRadians(90)))
                .strafeTo(RedClose6Plus6Constants.firstRow, new TranslationalVelConstraint(85))
                .strafeTo(RedClose6Plus6Constants.collectFirstRow, new TranslationalVelConstraint(85))
                .strafeTo(RedClose6Plus6Constants.shootingPosThirdMiddle, new TranslationalVelConstraint(85))
                .strafeTo(RedClose6Plus6Constants.shootingPosThird, new TranslationalVelConstraint(85));


        TrajectoryActionBuilder leave = drive.actionBuilder(new Pose2d(-8, 16, Math.toRadians(90)))
                .strafeTo(RedClose6Plus6Constants.leave, new TranslationalVelConstraint(85));

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
                                                        new SleepAction(2.4),
                                                        ShootingSpeedClass.disabled(),
                                                        TurretHeadingClass.redCloseShootAngle2(),
                                                        ShootThirdRow,
                                                        ShootingSpeedClass.shootCloseDis(),
                                                        new SleepAction(2),
                                                        ShootingSpeedClass.disabled(),
                                                        CollectSecondRow,
                                                        new SleepAction(0.3),
                                                        ShootSecondRow,
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
