package org.firstinspires.ftc.teamcode.TeleOps;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.SubSystems.DriveTrain.DriveClass;
import org.firstinspires.ftc.teamcode.SubSystems.IntakeSystem.IntakeClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleConstants;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedConstants;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.TurretHeadingClass;
import org.firstinspires.ftc.teamcode.TwoDeadWheelLocalizer;
import org.firstinspires.ftc.teamcode.Utiity.ShooterStateClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel.TransferWheelClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass;
import org.firstinspires.ftc.teamcode.messages.MecanumLocalizerInputsMessage;
import org.firstinspires.ftc.teamcode.Utiity.DynamicShooting.DynamicShootingClass;



@TeleOp(name = "Working Teleop")
@Config
public class TeleOpCentric extends LinearOpMode {

        public static double manualDistance = 0;

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        boolean manualToggle = false;

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
            else if(ShootingSpeedClass.masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio < 300)
            {
                IntakeClass.operate(0);
                TransferWheelClass.operate(0);
            }

            if (gamepad1.dpad_up)
            {
                manualToggle = !manualToggle;
            }

            if (!manualToggle)
            {
                HoodAngleClass.setPos(DynamicShootingClass.calcAngle(manualDistance));
                if (gamepad1.square)
                {
                    ShooterStateClass.operate(DynamicShootingClass.calcSpeed(manualDistance));
                }
                else if (gamepad1.cross)
                {
                    ShootingSpeedClass.setSpeed(ShootingSpeedConstants.disabledSpeed);
                }
            }
            else
            {
                ShooterStateClass.setState(gamepad1);
                ShooterStateClass.manualOperate();
            }

            TurretHeadingClass.test(gamepad2);

            HoodAngleClass.telemetry(telemetry);
            ShootingSpeedClass.telemetry(telemetry);
            TransferWheelClass.telemetry(telemetry);
            TurretHeadingClass.telemetry(telemetry);
            DynamicShootingClass.telemetry(telemetry, manualDistance);
            telemetry.addData("manual:", manualToggle);
            telemetry.update();
        }
    }
}
