package org.firstinspires.ftc.teamcode.Autonomous.Blue.Far.BlueFar12;

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
@Autonomous(name = "BlueHumanPlayer" , group = "Autonomous" , preselectTeleOp = "Blue")
public class BlueFar12 extends LinearOpMode
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

        Pose2d initialPose = BlueFar12Constants.startingPos;
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder shoot1 = drive.actionBuilder(initialPose)
                .strafeTo(BlueFar12Constants.firstRow)
                .strafeTo(BlueFar12Constants.collectFirstRow)
                .strafeTo(BlueFar12Constants.shootingPos);

        TrajectoryActionBuilder shoot2 = drive.actionBuilder(new Pose2d(57,-14.5 , Math.toRadians(270)))
                .strafeTo(BlueFar12Constants.secondRow)
                .strafeTo(BlueFar12Constants.collectSecondRow)
                .strafeTo(BlueFar12Constants.shootingPosClose);

        TrajectoryActionBuilder shoot3 = drive.actionBuilder(new Pose2d(-5, -14.5, Math.toRadians(270)))
                .strafeTo(BlueFar12Constants.thirdRow)
                .strafeTo(BlueFar12Constants.collectThirdRow)
                .strafeTo(BlueFar12Constants.backThirdRow)
                .strafeTo(BlueFar12Constants.shootingPosClose);

        TrajectoryActionBuilder leave = drive.actionBuilder(new Pose2d(-5, -14.5, Math.toRadians(270)))
                .strafeTo(BlueFar12Constants.leaveClose);


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
                            TurretHeadingClass.blueFarShootAngle1(),
                            ShootingSpeedClass.shootFarDis(),
                            new SleepAction(5),
                            ShootingSpeedClass.disabled(),
                            TurretHeadingClass.blueFarShootAngle2(),
                            Shoot1,
                            ShootingSpeedClass.shootFarDis(),
                            new SleepAction(4),
                            ShootingSpeedClass.disabled(),
                            TurretHeadingClass.blueCloseShootAngle2(),
                            Shoot2,
                            ShootingSpeedClass.shootCloseDis(),
                            new SleepAction(2),
                            ShootingSpeedClass.disabled(),
                            Shoot3,
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
