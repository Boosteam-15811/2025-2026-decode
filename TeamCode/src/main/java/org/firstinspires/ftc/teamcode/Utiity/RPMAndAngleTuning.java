package org.firstinspires.ftc.teamcode.Utiity;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.SubSystems.DriveTrain.DriveClass;
import org.firstinspires.ftc.teamcode.SubSystems.IntakeSystem.IntakeClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel.TransferWheelClass;
import org.firstinspires.ftc.teamcode.Utiity.Camera.CameraClass;
import org.firstinspires.ftc.teamcode.Utiity.Camera.CameraTeleOp;

@TeleOp
@Config
public class RPMAndAngleTuning extends LinearOpMode {

    public static double motorRPM = 0;
    public static double servoPos = 0;

    public static double distance;

    @Override
    public void runOpMode() throws InterruptedException {
        DriveClass.init(hardwareMap);
        HoodAngleClass.init(hardwareMap);
        ShootingSpeedClass.init(hardwareMap);
        ShootingSpeedPID.init(hardwareMap);
        IntakeClass.init(hardwareMap);
        TransferWheelClass.init(hardwareMap);
        //CameraClass.init(hardwareMap);



        IMU imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));

        imu.initialize(parameters);

        waitForStart();
        //CameraClass.limeLight3A.start();

        while (opModeIsActive()) {

            if (gamepad1.options)
            {
                imu.resetYaw();
            }

            DriveClass.fieldArcade(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x,imu);

            HoodAngleClass.setPos(servoPos);
            ShootingSpeedClass.setSpeed(motorRPM);

//            YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
//            CameraClass.limeLight3A.updateRobotOrientation(orientation.getYaw(AngleUnit.DEGREES));
//
//            LLResult llResult = CameraClass.limeLight3A.getLatestResult();
//            if(llResult != null && llResult.isValid())
//            {
//                distance = CameraClass.getDistanceFromTag(llResult.getTa());
//            }
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
                }
                if (gamepad1.right_bumper)
                {
                    TransferWheelClass.operate(1);

                } else if (gamepad1.left_bumper)
                {
                    TransferWheelClass.operate(-1);
                }
                else
                {
                    TransferWheelClass.operate(0);
                }

            HoodAngleClass.telemetry(telemetry);
            ShootingSpeedClass.telemetry(telemetry);
            //CameraClass.telemetry(telemetry);

            telemetry.update();
        }

    }
}
