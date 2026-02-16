package org.firstinspires.ftc.teamcode.TeleOps;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.SubSystems.DriveTrain.DriveClass;
import org.firstinspires.ftc.teamcode.SubSystems.IntakeSystem.IntakeClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.TurretHeadingClass;
import org.firstinspires.ftc.teamcode.TwoDeadWheelLocalizer;
import org.firstinspires.ftc.teamcode.Utiity.ShooterStateClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel.TransferWheelClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass;
import org.firstinspires.ftc.teamcode.messages.MecanumLocalizerInputsMessage;

@TeleOp(name = "Working Teleop")
@Config
public class TeleOpCentric extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        DriveClass.init(hardwareMap);
        HoodAngleClass.init(hardwareMap);
        ShootingSpeedClass.init(hardwareMap);
        IntakeClass.init(hardwareMap);
        TransferWheelClass.init(hardwareMap);
        ShootingSpeedPID.init(hardwareMap);
        TurretHeadingClass.init(hardwareMap);



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
                TransferWheelClass.operate(-gamepad1.right_trigger);
            }
            else if (gamepad1.left_trigger > 0)
            {
                IntakeClass.operate(-gamepad1.left_trigger);
            }
            else if(ShooterStateClass.shooterState == ShooterStateClass.ShooterStates.DISABLED)
            {
                IntakeClass.operate(0);
                TransferWheelClass.operate(0);
            }


            ShooterStateClass.setState(gamepad1);
            ShooterStateClass.operate();
            TurretHeadingClass.test(gamepad2);

            HoodAngleClass.telemetry(telemetry);
            ShootingSpeedClass.telemetry(telemetry);
            TransferWheelClass.telemetry(telemetry);
            TurretHeadingClass.telemetry(telemetry);
            telemetry.update();
        }
    }
}
