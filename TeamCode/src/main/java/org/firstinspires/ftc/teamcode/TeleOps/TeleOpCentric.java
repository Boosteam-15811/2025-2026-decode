package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.SubSystems.DriveTrain.DriveClass;
import org.firstinspires.ftc.teamcode.SubSystems.IntakeSystem.IntakeClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShooterStateClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel.TransferWheelClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass;

@TeleOp
public class TeleOpCentric extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        DriveClass.init(hardwareMap);
        HoodAngleClass.init(hardwareMap);
        ShootingSpeedClass.init(hardwareMap);
        IntakeClass.init(hardwareMap);
        TransferWheelClass.init(hardwareMap);
        ShootingSpeedPID.init(hardwareMap);


        IMU imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));

        imu.initialize(parameters);

        waitForStart();
        while (opModeIsActive())
        {
            if (gamepad1.options)
            {
                imu.resetYaw();
            }

                DriveClass.fieldArcade(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x,imu);

            if (gamepad1.right_trigger > 0)
            {
                IntakeClass.operate(gamepad1.right_trigger);
            }
            else if (gamepad1.left_trigger > 0)
            {
                IntakeClass.operate(-gamepad1.left_trigger);
            }
            else
            {
                IntakeClass.operate(0);
                TransferWheelClass.operate(0);
            }


            ShooterStateClass.setState(gamepad1);
            ShooterStateClass.operate();

            HoodAngleClass.telemetry(telemetry);
            ShootingSpeedClass.telemetry(telemetry);
            TransferWheelClass.telemetry(telemetry);
            telemetry.update();
        }
    }
}
