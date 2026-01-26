package org.firstinspires.ftc.teamcode.trajectories;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.SubSystems.IntakeSystem.IntakeClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel.TransferWheelClass;

@Config
@Autonomous(name = "At Red Goal", group = "Autonomous")
public class AtRedGoal extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        IntakeClass.init(hardwareMap);
        HoodAngleClass.init(hardwareMap);
        ShootingSpeedClass.init(hardwareMap);
        TransferWheelClass.init(hardwareMap);
        ShootingSpeedPID.init(hardwareMap);

        Pose2d initialPose = new Pose2d(-49, 49 , Math.toRadians(125));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder collect1 = drive.actionBuilder(initialPose)
                //from goal to collecting the first row
                .strafeTo(new Vector2d(-49,40))
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(-7, 29, Math.toRadians(90)), Math.toRadians(90))
                .lineToYConstantHeading(47);

        TrajectoryActionBuilder shoot1 = drive.actionBuilder(new Pose2d(-7, 29, Math.toRadians(90)))
                //from first row to shooting from medium range
                .strafeToLinearHeading(new Vector2d(-40, 40), Math.toRadians(125))
                .strafeTo(new Vector2d(-60,46));

        TrajectoryActionBuilder leave1 = drive.actionBuilder(new Pose2d(-60, 46 , Math.toRadians(125)))
                //leave the launch line
                .strafeTo(new Vector2d(-55,15));



        //second cycle
        //.splineToLinearHeading(new Pose2d(23, 29, Math.toRadians(90)), Math.toRadians(90))
        //.lineToYConstantHeading(42)
        //.setTangent(180)
        //.splineToLinearHeading(new Pose2d(-6, -6 , Math.toRadians(135)), Math.toRadians(135))




        Action collect = collect1.build();

        Action shoot = shoot1.build();

        Action leave = leave1.build();

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new ParallelAction(
                        ShootingSpeedPID.pid(),
                        TransferWheelClass.activate(),
                        new SequentialAction(
                                ShootingSpeedClass.atGoal(),
                                HoodAngleClass.atGoal(),
                                IntakeClass.activate(),
                                new SleepAction(8),
                                ShootingSpeedClass.disabled(),
                                collect,
                                shoot,
                                ShootingSpeedClass.atGoal(),
                                HoodAngleClass.atGoal(),
                                new SleepAction(8),
                                leave,
                                IntakeClass.deactivate(),
                                ShootingSpeedClass.disabled()
                        )
                )
        );
    }
}
