package org.firstinspires.ftc.teamcode.Autonomous.Blue15Close;

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

@Autonomous(name = "blueClose15", group = "Autonomous" , preselectTeleOp = "Blue")

public class Blue15Auto extends LinearOpMode
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

        Pose2d initialPose = Blue15Constants.startingPos;
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder shoot = drive.actionBuilder(initialPose)
                .strafeTo(Blue15Constants.startShootingPos);

        TrajectoryActionBuilder shootThirdRow = drive.actionBuilder(new Pose2d(-16, -16, Math.toRadians(270)))
                .strafeTo(Blue15Constants.thirdRow)
                .strafeTo(Blue15Constants.collectThirdRow)
                .strafeTo(Blue15Constants.shootingPos);


        TrajectoryActionBuilder shootSecondRow = drive.actionBuilder(new Pose2d(-6, -16, Math.toRadians(270)))
                .strafeTo(Blue15Constants.secondRow)
                .strafeTo(Blue15Constants.collectSecondRow)
                .strafeTo(Blue15Constants.shootingPos);

        TrajectoryActionBuilder shootFirstRow = drive.actionBuilder(new Pose2d(-6, -16, Math.toRadians(270)))
                .strafeTo(Blue15Constants.firstRow)
                .strafeTo(Blue15Constants.collectFirstRow)
                .strafeTo(Blue15Constants.shootingPosThird);

        TrajectoryActionBuilder gateShoot1 = drive.actionBuilder(new Pose2d(-6,-16 , Math.toRadians(270)))
                .strafeToLinearHeading(BlueCloseConstants.collectGate, Math.toRadians(240))
                .strafeTo(BlueCloseConstants.Back)
                .strafeTo(BlueCloseConstants.collectGate);

        TrajectoryActionBuilder gateShoot1Part2 = drive.actionBuilder(new Pose2d(16,-61 , Math.toRadians(240)))
                .strafeToLinearHeading(BlueCloseConstants.shootingPos, Math.toRadians(270));


        TrajectoryActionBuilder leave = drive.actionBuilder(new Pose2d(-10, -16, Math.toRadians(270)))
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
                                                        TurretHeadingClass.blueEndAutoAngle(),
                                                        Leave

                                                )
                                )
                );
    }
}
