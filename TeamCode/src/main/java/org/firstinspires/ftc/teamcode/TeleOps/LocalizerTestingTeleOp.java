package org.firstinspires.ftc.teamcode.TeleOps;

import static org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.TurretHeadingClass.headingMotor;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;
import com.seattlesolvers.solverslib.geometry.Pose2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.SubSystems.DriveTrain.DriveClass;
import org.firstinspires.ftc.teamcode.SubSystems.IntakeSystem.IntakeClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedConstants;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel.TransferWheelClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.PinpointTurretHeadingPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.TurretHeadingClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.TurretHeadingConstants;
import org.firstinspires.ftc.teamcode.Utility.Camera.CameraClass;
import org.firstinspires.ftc.teamcode.Utility.DynamicShooting.DynamicShootingClass;
import org.firstinspires.ftc.teamcode.Utility.LocalizerClass;
import org.firstinspires.ftc.teamcode.Utility.ShooterStateClass;

@TeleOp
public class LocalizerTestingTeleOp extends LinearOpMode {

    private static double distance = 0;

    private static double wantedAngle = 0;
    private boolean shooting = false;

    private static Pose2D blueAutonoumsEnd = new Pose2D(DistanceUnit.INCH, 15, -42, AngleUnit.DEGREES, 0);

    @Override
    public void runOpMode() throws InterruptedException
    {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        LocalizerClass.init(new Pose2D(DistanceUnit.INCH,0,0 , AngleUnit.DEGREES,0), hardwareMap);
        DriveClass.init(hardwareMap);
        ShootingSpeedPID.init(hardwareMap);
        ShootingSpeedClass.init(hardwareMap);
        PinpointTurretHeadingPID.init(hardwareMap);
        TurretHeadingClass.init(hardwareMap);
        HoodAngleClass.init(hardwareMap);
        IntakeClass.init(hardwareMap);
        TransferWheelClass.init(hardwareMap);




        IMU imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));

        imu.initialize(parameters);



        waitForStart();
        LocalizerClass.pinpoint.setPosition(blueAutonoumsEnd);
        while (opModeIsActive())
        {
            if (gamepad1.options)
            {
                imu.resetYaw();
            }

            DriveClass.fieldArcade(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x,imu);


            LocalizerClass.pinpoint.update();
            Pose2D robotPose2D = LocalizerClass.pinpoint.getPosition();


            distance = LocalizerClass.blueGetDistance(new Pose2d(-70,-70,Math.toRadians(0)), robotPose2D);

            wantedAngle = LocalizerClass.blueWantedTurretHeading(new Pose2d(-70, -70, Math.toRadians(0)), robotPose2D , 270);


            HoodAngleClass.setPos(DynamicShootingClass.calcAngle(distance));



            if (gamepad1.right_trigger > 0)
            {
                IntakeClass.operate(gamepad1.right_trigger);
                TransferWheelClass.operate(-gamepad1.right_trigger);
            }
            else if (gamepad1.left_trigger > 0)
            {
                IntakeClass.operate(-gamepad1.left_trigger);
            }
            else if(ShootingSpeedClass.masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio < 180)
            {
                IntakeClass.operate(0);
                TransferWheelClass.operate(0);
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
                ShooterStateClass.operate(DynamicShootingClass.calcSpeed(distance));
            }

            TurretHeadingClass.pinpointOperate(wantedAngle);





            telemetry.addData("X coordinate (IN)", robotPose2D.getX(DistanceUnit.INCH));
            telemetry.addData("Y coordinate (IN)", robotPose2D.getY(DistanceUnit.INCH));
            telemetry.addData("Current angle (DEGREES)", robotPose2D.getHeading(AngleUnit.DEGREES));
            telemetry.addData("Target distance" , LocalizerClass.blueGetDistance(new Pose2d(-70,-70,Math.toRadians(0)), robotPose2D));
           telemetry.addData("Target heading" , LocalizerClass.blueWantedTurretHeading(new Pose2d(-70, -70, Math.toRadians(0)), robotPose2D , 270));
            telemetry.update();

        }
    }
}
