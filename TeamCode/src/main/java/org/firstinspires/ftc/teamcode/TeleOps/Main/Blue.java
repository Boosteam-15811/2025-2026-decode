package org.firstinspires.ftc.teamcode.TeleOps.Main;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;
import com.seattlesolvers.solverslib.geometry.Pose2d;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.SubSystems.DriveTrain.DriveClass;
import org.firstinspires.ftc.teamcode.SubSystems.IntakeSystem.IntakeClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleConstants;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedConstants;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel.TransferWheelClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.PinpointTurretHeadingPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.TurretHeadingClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.Utility.CameraClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.Utility.LocalizerClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.Utility.ShooterStateClass;
@TeleOp(group = "main")
public class Blue extends LinearOpMode {

    private static double distance = 0;

    private static final double minDistance = 35;
    private static final double maxDistance = 110;

    private boolean lastChange = false;

    private boolean shooting = false;

    public static double wantedAngle = 0;

    private static Pose2D blueAutonoumsEnd = new Pose2D(DistanceUnit.INCH, 15, -42, AngleUnit.DEGREES, 0);

    private static int blueId = 20;

    @Override
    public void runOpMode() throws InterruptedException
    {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        boolean manualToggle = false;

        LocalizerClass.init(blueAutonoumsEnd,hardwareMap);
        DriveClass.init(hardwareMap);
        ShootingSpeedPID.init(hardwareMap);
        ShootingSpeedClass.init(hardwareMap);
        PinpointTurretHeadingPID.init(hardwareMap);
        TurretHeadingClass.init(hardwareMap);
        HoodAngleClass.init(hardwareMap);
        IntakeClass.init(hardwareMap);
        TransferWheelClass.init(hardwareMap);
        CameraClass.init(hardwareMap);



        IMU imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));

        imu.initialize(parameters);


        waitForStart();
        LocalizerClass.pinpoint.setPosition(blueAutonoumsEnd);
        while (opModeIsActive())
        {
            if (gamepad1.options) {
                imu.resetYaw();
            }

            //Preload
            if (gamepad1.dpad_left) {
                LocalizerClass.pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, 22, 58, AngleUnit.DEGREES, 0));
            }
            else if(gamepad1.dpad_right)
            {
                LocalizerClass.pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, 0, 0, AngleUnit.DEGREES, 0));
            }


            DriveClass.fieldArcade(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x, imu);


            LocalizerClass.pinpoint.update();
            Pose2D robotPose2D = LocalizerClass.pinpoint.getPosition();

            LocalizerClass.calcTurretPose(robotPose2D);


            distance = LocalizerClass.blueGetDistance(new Pose2d(-72,-72,Math.toRadians(0)));

            wantedAngle = LocalizerClass.blueWantedTurretHeading(new Pose2d(-72, -72, Math.toRadians(0)));

            if (CameraClass.cameraDetecting()&& CameraClass.compareID(blueId) && CameraClass.inDisTolerance(distance))
            {
                LocalizerClass.setTurretPose(CameraClass.calcTurretPose());
                LocalizerClass.calcPinpointPose();
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
            else if(ShootingSpeedClass.masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio < 1800)
            {
                IntakeClass.operate(0);
                TransferWheelClass.operate(0);
            }


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
                  //  HoodAngleClass.setPos(DynamicShootingClass.calcAngle(distance));
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
                       // ShooterStateClass.operate(DynamicShootingClass.calcSpeed(distance));
                    }
                }
            }
            else
            {
                ShooterStateClass.setState(gamepad1);
                ShooterStateClass.manualOperate();
            }

            TurretHeadingClass.pinpointOperate(wantedAngle);

            lastChange = gamepad1.dpad_up;

            LocalizerClass.telemetry(telemetry);
            telemetry.addData("robotX", robotPose2D.getX(DistanceUnit.INCH));
            telemetry.addData("robotY", robotPose2D.getY(DistanceUnit.INCH));
            telemetry.addData("robot angle", robotPose2D.getHeading(AngleUnit.DEGREES));
            telemetry.addData("distance" , LocalizerClass.blueGetDistance(new Pose2d(-72,-72,Math.toRadians(0))));
            telemetry.addData("wanted angle" , wantedAngle);
            telemetry.addData("motorVelocity",  ShootingSpeedClass.masterShootingMotor.getVelocity() * ShootingSpeedConstants.tickToRPMRatio);
            telemetry.addData("in tolerance" , ShootingSpeedClass.inTolerence(ShootingSpeedConstants.farFromGoalSpeed, ShootingSpeedConstants.dynamicTolerance));
            IntakeClass.telemetry(telemetry);
            telemetry.update();


        }
    }
}
