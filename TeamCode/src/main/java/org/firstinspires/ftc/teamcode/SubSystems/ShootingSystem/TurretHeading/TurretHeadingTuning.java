package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.IMU;
import com.seattlesolvers.solverslib.controller.PIDController;
import com.seattlesolvers.solverslib.controller.PIDFController;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleClass;
import org.firstinspires.ftc.teamcode.Utility.Camera.CameraClass;

@TeleOp
@Config
public class TurretHeadingTuning extends LinearOpMode
{
    private PIDFController controller;



    public static double p = 0 ,i = 0 ,d = 0 , f = 0;

    public static double tx = 0;

    private DcMotor turretHeadingMotor;

    private static final double encoderResolution = 537.7;

    private static final double gearRatio = 13.0 / 140.0;

    private static boolean flip = false;

    private static double fixAngle = 0;

    private static final int wantedAprilTagID = 20;

    private static double power = 0;


    @Override
    public void runOpMode() throws InterruptedException {
        controller = new PIDFController(p, i, d, f);
        telemetry = new MultipleTelemetry(telemetry , FtcDashboard.getInstance().getTelemetry());
        CameraClass.init(hardwareMap);
        HoodAngleClass.init(hardwareMap);


        turretHeadingMotor = hardwareMap.dcMotor.get("headingMotor");

        turretHeadingMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        turretHeadingMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        IMU imu = hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD,
                RevHubOrientationOnRobot.UsbFacingDirection.UP));

        imu.initialize(parameters);



        waitForStart();
        CameraClass.limeLight3A.start();

        while (opModeIsActive())
        {
            controller.setPIDF(p, i ,d, f);

            HoodAngleClass.setPos(0.06);

            YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
            CameraClass.limeLight3A.updateRobotOrientation(orientation.getYaw(AngleUnit.DEGREES));

            LLResult llResult = CameraClass.limeLight3A.getLatestResult();
            if(llResult != null && llResult.isValid() && CameraClass.compareID(wantedAprilTagID))
            {
                tx = llResult.getTx();
            }



            double motorAngle = turretHeadingMotor.getCurrentPosition()/TurretHeadingConstants.degreeInTicks;
            if (Math.abs(motorAngle) >= TurretHeadingConstants.angleLimit)
            {
                flip = true;
                fixAngle = Math.signum(motorAngle)*-TurretHeadingConstants.flipAngle;
            }
            else if(!flip)
            {
                double pid = controller.calculate(-tx, 0);

                power = pid;

            }
            if (flip)
            {
                double pid = controller.calculate(motorAngle, fixAngle);
                power = pid;

                if(pid < TurretHeadingConstants.pidStopper && pid > -TurretHeadingConstants.pidStopper)
                {
                    flip = false;
                }
            }
            turretHeadingMotor.setPower(power);

            //telemetry.addData("motorPos:" , motorPos/degreeInTicks);
            telemetry.addData("tx" ,tx);
            telemetry.addData("motorAngle" , motorAngle);
            telemetry.addData("power" , power);
            telemetry.addData("motorPower", turretHeadingMotor.getPower());
            telemetry.update();
        }

    }
}
