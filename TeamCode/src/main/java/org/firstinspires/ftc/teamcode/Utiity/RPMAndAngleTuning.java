package org.firstinspires.ftc.teamcode.Utiity;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.SubSystems.DriveTrain.DriveClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass;

@TeleOp
@Config
public class RPMAndAngleTuning extends LinearOpMode {

    public static double motorRPM = 0;
    public static double servoPos = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        DriveClass.init(hardwareMap);
        HoodAngleClass.init(hardwareMap);
        ShootingSpeedClass.init(hardwareMap);



        IMU imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));

        imu.initialize(parameters);

        waitForStart();
        while (opModeIsActive()) {

            if (gamepad1.options)
            {
                imu.resetYaw();
            }

            DriveClass.fieldArcade(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x,imu);

            HoodAngleClass.tuning(servoPos);
            ShootingSpeedClass.tuning(motorRPM);

            HoodAngleClass.telemetry(telemetry);
            ShootingSpeedClass.telemetry(telemetry);

            telemetry.update();
        }

    }
}
