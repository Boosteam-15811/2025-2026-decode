package org.firstinspires.ftc.teamcode.TeleOps;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.SubSystems.DriveTrain.DriveClass;
import org.firstinspires.ftc.teamcode.SubSystems.IntakeSystem.IntakeClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleConstants;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedConstants;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.TurretHeadingClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.TurretHeadingPID;
import org.firstinspires.ftc.teamcode.Utility.Camera.CameraClass;
import org.firstinspires.ftc.teamcode.Utility.ShooterStateClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel.TransferWheelClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass;
import org.firstinspires.ftc.teamcode.Utility.DynamicShooting.DynamicShootingClass;



@TeleOp(name = "Red Teleop")
@Config
public class RedTeleOp extends LinearOpMode {

    public static double manualDistance = 0;
    private double distance = 0;

    private static final double minDistance = 65;
    private static final double maxDistance = 250;

    private int wantedAprilTagID = 24; ////red april tag
    private boolean lastChange = false;
    private boolean shooting = false;

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
        TurretHeadingClass.teleOpInit(hardwareMap);
        CameraClass.init(hardwareMap);
        TurretHeadingPID.init(hardwareMap);



        IMU imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));

        imu.initialize(parameters);

        waitForStart();
        CameraClass.limeLight3A.start();

        while (opModeIsActive())
        {

            if (gamepad1.options)
            {
                imu.resetYaw();
            }

            DriveClass.fieldArcade(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x,imu);

            YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
            CameraClass.limeLight3A.updateRobotOrientation(orientation.getYaw(AngleUnit.DEGREES));

            if (gamepad2.dpad_up)
            {
                TurretHeadingClass.headingMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                TurretHeadingClass.headingMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }


            LLResult llResult = CameraClass.limeLight3A.getLatestResult();
            if(llResult != null && llResult.isValid() && CameraClass.compareID(wantedAprilTagID))
            {
                distance = CameraClass.getDistanceFromTag(llResult.getTa());
                TurretHeadingClass.setTx(llResult.getTx());
            }


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

            TurretHeadingClass.operate();

            if (!lastChange)
            {
                if (gamepad1.dpad_up)
                {
                    manualToggle = !manualToggle;
                }
            }

            if (!manualToggle)
            {
                if (distance <= minDistance)
                {
                    HoodAngleClass.setPos(HoodAngleConstants.atGoalPos);
                }
                else if (distance >= maxDistance)
                {
                    HoodAngleClass.setPos(HoodAngleConstants.launchZonePos);
                }
                else
                {
                    HoodAngleClass.setPos(DynamicShootingClass.calcAngle(distance));
                }

                if (gamepad1.square)
                {
                    shooting = true;
                }

                if (gamepad1.cross)
                {
                    shooting = false;
                    ShooterStateClass.operate(ShootingSpeedConstants.disabledSpeed);
                }
                else if (shooting)
                {
                    if (distance <= minDistance)
                    {
                        ShooterStateClass.operate(ShootingSpeedConstants.atGoalSpeed);
                    }
                    else if (distance >= maxDistance)
                    {
                        ShooterStateClass.operate(ShootingSpeedConstants.launchZoneSpeed);
                    }
                    else
                    {
                        ShooterStateClass.operate(DynamicShootingClass.calcSpeed(distance));
                    }
                }
            }
            else
            {
                ShooterStateClass.setState(gamepad1);
                ShooterStateClass.manualOperate();
            }



            lastChange = gamepad1.dpad_up;

            HoodAngleClass.telemetry(telemetry);
            ShootingSpeedClass.telemetry(telemetry);
            TransferWheelClass.telemetry(telemetry);
            TurretHeadingClass.telemetry(telemetry);
            DynamicShootingClass.telemetry(telemetry, manualDistance);
            CameraClass.telemetry(telemetry , wantedAprilTagID);
            telemetry.addData("manual:", manualToggle);
            telemetry.update();
        }
    }
}
