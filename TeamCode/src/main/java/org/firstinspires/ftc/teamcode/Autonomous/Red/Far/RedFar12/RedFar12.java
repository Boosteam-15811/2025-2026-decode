package org.firstinspires.ftc.teamcode.Autonomous.Red.Far.RedFar12;

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
@Autonomous(name = "RedFar12" , group = "Autonomous" , preselectTeleOp = "RedCloseTele")
public class RedFar12 extends LinearOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        DriveClass.init(hardwareMap);
        IntakeClass.init(hardwareMap);
        HoodAngleClass.init(hardwareMap);
        ShootingSpeedClass.init(hardwareMap);
        ShootingSpeedPID.init(hardwareMap);
        TransferWheelClass.init(hardwareMap);
        TurretHeadingClass.init(hardwareMap);
        PinpointTurretHeadingPID.init(hardwareMap);

        Pose2d initialPose = RedFar12Constants.startingPos;
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder shoot1 = drive.actionBuilder(initialPose)
                .strafeTo(RedFar12Constants.firstRow)
                .strafeTo(RedFar12Constants.collectFirstRow)
                .strafeTo(RedFar12Constants.shootingPos);

        TrajectoryActionBuilder shoot2 = drive.actionBuilder(new Pose2d(57,14.5 , Math.toRadians(90)))
                .strafeTo(RedFar12Constants.secondRow)
                .strafeTo(RedFar12Constants.collectSecondRow)
                .strafeTo(RedFar12Constants.shootingPosClose);

        TrajectoryActionBuilder shoot3 = drive.actionBuilder(new Pose2d(-6, 16, Math.toRadians(90)))
                .strafeTo(RedFar12Constants.thirdRow)
                .strafeTo(RedFar12Constants.collectThirdRow)
                .strafeTo(RedFar12Constants.backThirdRow)
                .strafeTo(RedFar12Constants.shootingPosClose);

        TrajectoryActionBuilder leave = drive.actionBuilder(new Pose2d(-6, 16, Math.toRadians(90)))
                .strafeTo(RedFar12Constants.leaveClose);


        Action Shoot1 = shoot1.build();
        Action Shoot2 = shoot2.build();
        Action Shoot3 = shoot3.build();
        Action Leave = leave.build();


        waitForStart();

        Actions.runBlocking
                (

                        new ParallelAction
                                (
                                        ShootingSpeedPID.pid(),
                                        PinpointTurretHeadingPID.pid(),
                                        TransferWheelClass.activate(),
                                        HoodAngleClass.shootDis(),
                                        new SequentialAction(
                                                new SleepAction(0.7),
                                                IntakeClass.activate()
                                        ),
                                        new SequentialAction
                                                (
                                                        TurretHeadingClass.redFarShootAngle1(),
                                                        ShootingSpeedClass.shootFarDis(),
                                                        new SleepAction(4.9),
                                                        ShootingSpeedClass.disabled(),
                                                        TurretHeadingClass.redFarShootAngle2(),
                                                        Shoot1,
                                                        ShootingSpeedClass.shootFarDis(),
                                                        new SleepAction(3.8),
                                                        ShootingSpeedClass.disabled(),
                                                        TurretHeadingClass.redCloseShootAngle2(),
                                                        Shoot2,
                                                        ShootingSpeedClass.shootCloseDis(),
                                                        new SleepAction(2.1),
                                                        ShootingSpeedClass.disabled(),
                                                        Shoot3,
                                                        ShootingSpeedClass.shootCloseDis(),
                                                        new SleepAction(2.1),
                                                        ShootingSpeedClass.disabled(),
                                                        TurretHeadingClass.endAutoAngle(),
                                                        Leave

                                                )
                                )
                );

    }
}
