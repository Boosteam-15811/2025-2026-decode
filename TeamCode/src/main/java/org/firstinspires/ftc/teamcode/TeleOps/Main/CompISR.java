package org.firstinspires.ftc.teamcode.TeleOps.Main;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.teamcode.SubSystems.DriveTrain.DriveClass;
import org.firstinspires.ftc.teamcode.SubSystems.IntakeSystem.IntakeClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedConstants;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel.TransferWheelClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.Utility.ShooterStateClass;
@Config
@TeleOp(group = "main")
public class CompISR extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException
    {
        DriveClass.init(hardwareMap);
        ShootingSpeedPID.init(hardwareMap);
        ShootingSpeedClass.init(hardwareMap);
        HoodAngleClass.init(hardwareMap);
        IntakeClass.init(hardwareMap);
        TransferWheelClass.init(hardwareMap);

        IMU imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));

        imu.initialize(parameters);

        imu.resetYaw();

        waitForStart();
        while (opModeIsActive())
        {
            if (gamepad1.options) {
                imu.resetYaw();
            }

            DriveClass.fieldArcade(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x, imu);

            if (gamepad1.right_trigger > 0)
            {
                IntakeClass.operate(gamepad1.right_trigger);
                TransferWheelClass.operate(-gamepad1.right_trigger);
            }
            else if (gamepad1.left_trigger > 0)
            {
                IntakeClass.operate(-gamepad1.left_trigger);
            }
            else if(ShootingSpeedClass.masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio <= 2000)
            {
                IntakeClass.operate(0);
                TransferWheelClass.operate(0);
            }

            ShooterStateClass.setState(gamepad1);
            ShooterStateClass.manualOperate();
        }
    }
}