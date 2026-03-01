package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading;

import static org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.TurretHeadingClass.headingMotor;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.seattlesolvers.solverslib.controller.PIDController;
import com.seattlesolvers.solverslib.geometry.Pose2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleClass;
import org.firstinspires.ftc.teamcode.Utility.LocalizerClass;

@TeleOp
@Config
@Disabled
public class PinpointTurretHeadingTuning extends LinearOpMode
{
    private PIDController controller;

    public static double p = 0 ,i = 0 ,d = 0;

    public static double wantedAngle = 0;

    private DcMotor turretHeadingMotor;


    private static double trueAngle = 0;

    private static final int wantedAprilTagID = 20;

    private static double power = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry , FtcDashboard.getInstance().getTelemetry());

        LocalizerClass.init(new Pose2D(DistanceUnit.INCH,0,0 , AngleUnit.DEGREES,0), hardwareMap);
        HoodAngleClass.init(hardwareMap);

        turretHeadingMotor = hardwareMap.dcMotor.get("headingMotor");

        turretHeadingMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        turretHeadingMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        waitForStart();
        while (opModeIsActive())
        {
            controller.setPID(p,i,d);

            HoodAngleClass.setPos(0.06);

            double motorAngle = turretHeadingMotor.getCurrentPosition()/TurretHeadingConstants.degreeInTicks;

            LocalizerClass.pinpoint.update();
            Pose2D pose2D = LocalizerClass.pinpoint.getPosition();

            wantedAngle = LocalizerClass.blueWantedTurretHeading(new Pose2d(-72, -72 , Math.toRadians(0)), pose2D);

            if (wantedAngle > 180)
            {
                trueAngle = wantedAngle - 360;
            }
            else if (wantedAngle < -180)
            {
                trueAngle = 360 + wantedAngle;
            }
            else
            {
                trueAngle = wantedAngle;
            }

            double pid = controller.calculate(motorAngle, trueAngle);

            power = pid;

            turretHeadingMotor.setPower(power);

            telemetry.addData("motorAngle" , motorAngle);
            telemetry.addData("wantedAngle", wantedAngle);
            telemetry.addData("power" , power);
            telemetry.addData("motorPower", turretHeadingMotor.getPower());
            telemetry.update();
        }
    }
}
