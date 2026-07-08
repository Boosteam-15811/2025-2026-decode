package org.firstinspires.ftc.teamcode.Autonomous.Blue.Close.BlueClose9;

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
import org.firstinspires.ftc.teamcode.Autonomous.Blue.Close.BlueClose6Plus6.BlueClose6Plus6Constants;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.SubSystems.DriveTrain.DriveClass;
import org.firstinspires.ftc.teamcode.SubSystems.IntakeSystem.IntakeClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel.TransferWheelClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.PinpointTurretHeadingPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.TurretHeadingClass;

@Autonomous(name = "BlueClose9", group = "Autonomous" , preselectTeleOp = "CompISR")

public class BlueClose9 extends LinearOpMode
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

        Pose2d initialPose = BlueClose9Constants.startingPos;
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder shoot = drive.actionBuilder(initialPose)
                .strafeTo(BlueClose9Constants.startShootingPos);

        TrajectoryActionBuilder shootThirdRow = drive.actionBuilder(new Pose2d(-16, -16, Math.toRadians(270)))
                .strafeTo(BlueClose9Constants.thirdRow)
                .strafeTo(BlueClose9Constants.collectThirdRow)
                .strafeTo(BlueClose9Constants.shootingPos);

        TrajectoryActionBuilder shootSecondRow = drive.actionBuilder(new Pose2d(-6, -16, Math.toRadians(270)))
                .strafeTo(BlueClose9Constants.secondRow)
                .strafeTo(BlueClose9Constants.collectSecondRow)
                .strafeTo(BlueClose9Constants.shootingPos);


        TrajectoryActionBuilder leave = drive.actionBuilder(new Pose2d(-6, -16, Math.toRadians(270)))
                .strafeTo(BlueClose9Constants.leave);

        Action Shoot = shoot.build();
        Action ShootThirdRow = shootThirdRow.build();
        Action ShootSecondRow = shootSecondRow.build();
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
                    ShootSecondRow,
                    ShootingSpeedClass.shootCloseDis(),
                    new SleepAction(2),
                    ShootingSpeedClass.disabled(),
                    TurretHeadingClass.endAutoAngle(),
                    Leave
                )
            )
        );
    }
}
