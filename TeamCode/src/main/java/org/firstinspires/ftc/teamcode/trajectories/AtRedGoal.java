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
        //TurretHeadingClass.init(hardwareMap);
        //PinpointTurretHeadingPID.init(hardwareMap);

        Pose2d initialPose = new Pose2d(-49, 49 , Math.toRadians(125));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder shoot1 = drive.actionBuilder(initialPose)
                //from start to shooting a bit farther
                .strafeToLinearHeading(new Vector2d(-16,17),Math.toRadians(130));

        TrajectoryActionBuilder collect1 = drive.actionBuilder(new Pose2d(-16, 17, Math.toRadians(130)))
                //from start to collecting the third row
                .setTangent(270)
                .splineToLinearHeading(new Pose2d(-15,23, Math.toRadians(90)), Math.toRadians(90))
                .lineToYConstantHeading(54);

        TrajectoryActionBuilder shoot2 = drive.actionBuilder(new Pose2d(-16, 54, Math.toRadians(90)))
                //from first row to shooting from medium range
                .strafeToLinearHeading(new Vector2d(-16,17),Math.toRadians(130));

        TrajectoryActionBuilder collect2 = drive.actionBuilder(new Pose2d(-16, 17 , Math.toRadians(130)))
                //from medium range to second row
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(9,27, Math.toRadians(90)), Math.toRadians(90))
                .lineToYConstantHeading(54);


        TrajectoryActionBuilder shoot3 = drive.actionBuilder(new Pose2d(7, 54, Math.toRadians(90)))
                //from second row to shooting from medium range
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(-16, 17 , Math.toRadians(130)), Math.toRadians(130));


        TrajectoryActionBuilder collect3 = drive.actionBuilder(new Pose2d(-16, 17, Math.toRadians(130)))
                //from medium range to first row
                .setTangent(0)
                .splineToLinearHeading(new Pose2d(31,30, Math.toRadians(90)), Math.toRadians(90))
                .lineToYConstantHeading(54);

        TrajectoryActionBuilder shoot4 = drive.actionBuilder(new Pose2d(31, 54, Math.toRadians(90)))
                //from first row to shooting from medium range
                .setTangent(180)
                .splineToLinearHeading(new Pose2d(-16, 17 , Math.toRadians(130)), Math.toRadians(130));

        TrajectoryActionBuilder leave1 = drive.actionBuilder(new Pose2d(-16, 17 , Math.toRadians(130)))
                //leave the launch line
                .strafeToLinearHeading(new Vector2d(-42,15), Math.toRadians(90));


        Action Collect1 = collect1.build();
        Action Collect2 = collect2.build();
        Action Collect3 = collect3.build();

        Action Shoot1 = shoot1.build();
        Action Shoot2 = shoot2.build();
        Action Shoot3 = shoot3.build();
        Action Shoot4 = shoot4.build();

        Action leave = leave1.build();




        waitForStart();

        Actions.runBlocking(
                new ParallelAction(
                        ShootingSpeedPID.pid(),
                        TransferWheelClass.activate(),
                        IntakeClass.shootfarfromgoal(),
                        //   PinpointTurretHeadingPID.pid()
                        new SequentialAction(
                                ShootingSpeedClass.farFromGoal(),
                                Shoot1,
                                HoodAngleClass.farFromGoal(),
                                //  IntakeClass.activate(),
                                new SleepAction(4),
                                ShootingSpeedClass.disabled(),
                                Collect1,
                                Shoot2,
                                HoodAngleClass.farFromGoal(),
                                ShootingSpeedClass.farFromGoal(),
                                //    IntakeClass.activate(),
                                new SleepAction(4),
                                ShootingSpeedClass.disabled(),
                                Collect2,
                                Shoot3,
                                HoodAngleClass.farFromGoal(),
                                ShootingSpeedClass.farFromGoal(),
                                new SleepAction(4),
                                ShootingSpeedClass.disabled(),
                                leave

//                                ShootingSpeedClass.farFromGoal(),
//                                HoodAngleClass.farFromGoal(),
//                                IntakeClass.activate(),
//                                leave

                        )
                )
        );
    }
}
