package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.seattlesolvers.solverslib.controller.PIDController;
import com.seattlesolvers.solverslib.controller.PIDFController;

import org.firstinspires.ftc.teamcode.SubSystems.DriveTrain.DriveClass;
import org.firstinspires.ftc.teamcode.SubSystems.IntakeSystem.IntakeClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle.HoodAngleClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel.TransferWheelClass;

@TeleOp
@Config
public class ShootingSpeedTuning extends LinearOpMode {

    public static PIDFController controller;

    public static double p = 0.0018 ,i = 0 ,d = 0.000001, f = 0.00025;

    public static int targetVelocity = 0;

    private static DcMotorEx masterShootingSpeedMotor;
    private static DcMotorEx slaveShootingSpeedMotor;

    @Override
    public void runOpMode() throws InterruptedException {

        controller = new PIDFController(p,i,d,f);

        TransferWheelClass.init(hardwareMap);
        IntakeClass.init(hardwareMap);
        DriveClass.init(hardwareMap);
        HoodAngleClass.init(hardwareMap);
        HoodAngleClass.test(gamepad1);

        telemetry = new MultipleTelemetry(telemetry , FtcDashboard.getInstance().getTelemetry());

        masterShootingSpeedMotor = hardwareMap.get(DcMotorEx.class , "masterShootingMotorSpeed");

        masterShootingSpeedMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        masterShootingSpeedMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        masterShootingSpeedMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.FLOAT);

        waitForStart();
        while (opModeIsActive())
        {
            controller.setPIDF(p,i,d,f);

            double currentVelocity = (masterShootingSpeedMotor.getVelocity()/28)*60;

            double pid = controller.calculate(currentVelocity , targetVelocity);

            double power = pid;

            if(targetVelocity < 100)
            {
                masterShootingSpeedMotor.setMotorDisable();
            }
            else {
                masterShootingSpeedMotor.setPower(power);
            }

                DriveClass.arcade(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);

            if(gamepad1.right_bumper)
            {
                TransferWheelClass.operate(1);
            }
            else {
                TransferWheelClass.operate(0);
            }

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
            HoodAngleClass.test(gamepad1);
            HoodAngleClass.telemetry(telemetry);

            telemetry.addData("motorPower:" , masterShootingSpeedMotor.getPower());
            telemetry.addData("currentVelocity:" ,currentVelocity);
            telemetry.addData("targetVelocity" , targetVelocity);
            telemetry.addData("currentError:", (targetVelocity-currentVelocity));
            telemetry.update();
        }
    }
}
