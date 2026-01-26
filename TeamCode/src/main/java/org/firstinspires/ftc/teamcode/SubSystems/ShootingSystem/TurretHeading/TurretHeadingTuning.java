package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.seattlesolvers.solverslib.controller.PIDController;

@TeleOp
@Config
public class TurretHeadingTuning extends LinearOpMode
{
    private PIDController controller;

    public static double p = 0 ,i = 0 ,d = 0;

    public static int target = 0;

    private DcMotor turretHeadingMotor;

    private static final double encoderResolution = 384.5;

    private static final double gearRatio = 1.0 / 7.0;

    public static final double degreeInTicks = (encoderResolution*gearRatio)/360;

    public static double angleToTicks = target*degreeInTicks;

    @Override
    public void runOpMode() throws InterruptedException {
        controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry , FtcDashboard.getInstance().getTelemetry());

        turretHeadingMotor = hardwareMap.dcMotor.get("TurretHeadingMotor");

        turretHeadingMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        turretHeadingMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();
        while (opModeIsActive())
        {
            angleToTicks = target*degreeInTicks;
            controller.setPID(p, i ,d);

            int motorPos = turretHeadingMotor.getCurrentPosition();
            double pid = controller.calculate(motorPos , angleToTicks);

            double power = pid;

            turretHeadingMotor.setPower(power);

            telemetry.addData("motorPos:" , motorPos/degreeInTicks);
            telemetry.addData("target" , target);
            telemetry.update();
        }

    }
}
