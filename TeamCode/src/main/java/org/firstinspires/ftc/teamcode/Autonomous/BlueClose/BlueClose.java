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

        TrajectoryActionBuilder shoot2 = drive.actionBuilder(new Pose2d(-16,-16, Math.toRadians(270)))
                .strafeTo(BlueCloseConstants.secondRow)
                .strafeTo(BlueCloseConstants.collectSecondRow)
                .strafeTo(BlueCloseConstants.shootingPos);

        TrajectoryActionBuilder gateShoot = drive.actionBuilder(new Pose2d(0,-16 , Math.toRadians(270)))
                .strafeToLinearHeading(BlueCloseConstants.collectGate, Math.toRadians(240))
                .strafeTo(BlueCloseConstants.Back)
                .strafeTo(BlueCloseConstants.collectGate)
                .strafeToLinearHeading(BlueCloseConstants.shootingPos, Math.toRadians(270));

        TrajectoryActionBuilder leave = drive.actionBuilder(new Pose2d(0,-16 , Math.toRadians(270)))
                .strafeTo(BlueCloseConstants.leave);



        Action Shoot1 = shoot1.build();
        Action Shoot2 = shoot2.build();
        Action GateShoot = gateShoot.build();
        Action Leave = leave.build();

        waitForStart();

        Actions.runBlocking
        (
                new ParallelAction
                        (
                                ShootingSpeedPID.pid(),
                                TransferWheelClass.activate(),
                                IntakeClass.activate(),
                                PinpointTurretHeadingPID.pid(),
                                new SequentialAction
                                        (
                                                Shoot1,
                                                new SleepAction(3),
                                                ShootingSpeedClass.disabled(),
                                                Shoot2,
                                                new SleepAction(3),
                                                ShootingSpeedClass.disabled(),
                                                GateShoot,
                                                new SleepAction(3),
                                                ShootingSpeedClass.disabled(),
                                                Leave
                                        )

                        )
        );


    }
}
