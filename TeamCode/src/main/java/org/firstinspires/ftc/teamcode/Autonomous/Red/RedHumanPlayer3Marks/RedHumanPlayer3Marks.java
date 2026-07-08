package org.firstinspires.ftc.teamcode.Autonomous.Red.RedHumanPlayer3Marks;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.Blue.Far.BlueFar12.BlueFar12Constants;
import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.SubSystems.DriveTrain.DriveClass;
import org.firstinspires.ftc.teamcode.SubSystems.IntakeSystem.IntakeClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel.TransferWheelClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.PinpointTurretHeadingPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.TurretHeadingClass;

@Autonomous(name = "RedHumanPlayer" , group = "Autonomous" , preselectTeleOp = "Red")
public class RedHumanPlayer3Marks extends LinearOpMode
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

        Pose2d initialPose = RedHumanPlayerConstants3Marks.startingPos;
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder shoot1 = drive.actionBuilder(initialPose)
                .setTangent(180)
                .splineToLinearHeading(RedHumanPlayerConstants3Marks.firstRow, Math.toRadians(90))
                .strafeTo(RedHumanPlayerConstants3Marks.shootingPos);

        TrajectoryActionBuilder shoot2 = drive.actionBuilder(new Pose2d(62,14.5 , Math.toRadians(90)))
                .strafeTo(RedHumanPlayerConstants3Marks.secondRow)
                .strafeTo(RedHumanPlayerConstants3Marks.collectSecondRow)
                .strafeTo(RedHumanPlayerConstants3Marks.shootingPos);

        TrajectoryActionBuilder shoot3 = drive.actionBuilder(new Pose2d(62,14.5 , Math.toRadians(90)))
                .strafeTo(RedHumanPlayerConstants3Marks.thirdRow)
                .strafeTo(RedHumanPlayerConstants3Marks.collectThirdRow)
                .strafeTo(RedHumanPlayerConstants3Marks.backThirdRow)
                .strafeTo(RedHumanPlayerConstants3Marks.shootingPos);

        TrajectoryActionBuilder leave = drive.actionBuilder(new Pose2d(62,14.5 , Math.toRadians(90)))
                .strafeTo(BlueFar12Constants.leave);



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
                                        IntakeClass.activate(),
                                        HoodAngleClass.shootDis(),
                                        new SequentialAction
                                                (
                                                        TurretHeadingClass.redCloseShootAngle1(),
                                                        ShootingSpeedClass.shootFarDis(),
                                                        new SleepAction(4),
                                                        ShootingSpeedClass.disabled(),
                                                        Shoot1,
                                                        new SleepAction(3),
                                                        ShootingSpeedClass.disabled(),
                                                        Shoot2,
                                                        ShootingSpeedClass.shootFarDis(),
                                                        new SleepAction(3),
                                                        ShootingSpeedClass.disabled(),
                                                        Shoot3,
                                                        ShootingSpeedClass.shootFarDis(),
                                                        new SleepAction(3),
                                                        ShootingSpeedClass.disabled(),
                                                        Leave

                                                )

                                )
                );
    }
}
