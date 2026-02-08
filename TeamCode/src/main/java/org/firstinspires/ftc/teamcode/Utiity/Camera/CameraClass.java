package org.firstinspires.ftc.teamcode.Utiity.Camera;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@TeleOp
public class CameraClass extends LinearOpMode {
    private Limelight3A limeLight3A;
    private double distance;

    public double getDistanceFromTag(double ta)
    {
        double scale = 1212.627;
        return Math.pow((scale/ta),(1/1.356486));
    }
    @Override
    public void runOpMode() throws InterruptedException {

        limeLight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limeLight3A.pipelineSwitch(0);

        IMU imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));

        imu.initialize(parameters);

        waitForStart();
        limeLight3A.start();

        while (opModeIsActive())
        {
            YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
            limeLight3A.updateRobotOrientation(orientation.getYaw(AngleUnit.DEGREES));

            LLResult llResult = limeLight3A.getLatestResult();
            if(llResult != null && llResult.isValid())
            {
                distance = getDistanceFromTag(llResult.getTa());

                telemetry.addData("Calculated Distance", distance);
                telemetry.addData("Target X", llResult.getTx());
                telemetry.addData("Target Area", llResult.getTa());
                telemetry.update();
            }
        }


    }
}
