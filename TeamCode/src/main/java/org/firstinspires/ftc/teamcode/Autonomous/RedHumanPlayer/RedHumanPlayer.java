package org.firstinspires.ftc.teamcode.Autonomous.RedHumanPlayer;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.BlueHumanPlayer.BlueHumanPlayerConstants;
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
public class RedHumanPlayer extends LinearOpMode
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

        Pose2d initialPose = RedHumanPlayerConstants.startingPos;
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder shoot1 = drive.actionBuilder(initialPose)
                .setTangent(180)
                .splineToLinearHeading(RedHumanPlayerConstants.firstRow, Math.toRadians(90))
                .strafeTo(RedHumanPlayerConstants.shootingPos);

        TrajectoryActionBuilder shoot2 = drive.actionBuilder(new Pose2d(62,14.5 , Math.toRadians(90)))
                .strafeTo(RedHumanPlayerConstants.collectHumanPlayer)
                .strafeTo(RedHumanPlayerConstants.back)
                .strafeTo(RedHumanPlayerConstants.collectHumanPlayer)
                .strafeTo(RedHumanPlayerConstants.shootingPos);

        TrajectoryActionBuilder leave = drive.actionBuilder(new Pose2d(62,14.5 , Math.toRadians(90)))
                .strafeTo(BlueHumanPlayerConstants.leave);



        Action Shoot1 = shoot1.build();
        Action Shoot2 = shoot2.build();
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
                                                       // HoodAngleClass.changeHoodAngle(),
                                                        Shoot1,
                                                       // ShootingSpeedClass.setSpeed(),
                                                        new SleepAction(3),
                                                        ShootingSpeedClass.disabled(),
                                                        Shoot2,
                                                      //  HoodAngleClass.changeHoodAngle(),
                                                       // ShootingSpeedClass.setSpeed(),
                                                        new SleepAction(3),
                                                        ShootingSpeedClass.disabled(),
                                                        Leave
                                                )

                                )
                );
    }
}
