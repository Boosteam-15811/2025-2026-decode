package org.firstinspires.ftc.teamcode.Autonomous.Blue.Close.BlueCloseLeave;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.SubSystems.DriveTrain.DriveClass;

@Autonomous(name = "BlueCloseLeave", group = "Autonomous" , preselectTeleOp = "CompISR")

public class BlueCloseLeave extends LinearOpMode
{
    @Override
    public void runOpMode() throws InterruptedException {

        DriveClass.init(hardwareMap);

        Pose2d initialPose = BlueCloseLeaveConstants.startingPos;
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder leave = drive.actionBuilder(initialPose)
                .strafeTo(BlueCloseLeaveConstants.leave);

        Action Leave = leave.build();

        waitForStart();

        Actions.runBlocking
        (
            Leave
        );
    }
}
