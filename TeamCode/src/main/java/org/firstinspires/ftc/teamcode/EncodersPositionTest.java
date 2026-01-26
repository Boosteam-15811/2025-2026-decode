package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.ftc.OverflowEncoder;
import com.acmerobotics.roadrunner.ftc.RawEncoder;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
@Disabled
public class EncodersPositionTest extends LinearOpMode {

    private static DcMotor FRMotor;
    private static DcMotor FLMotor;
    private static DcMotor BRMotor;
    private static DcMotor BLMotor;

    @Override
    public void runOpMode() throws InterruptedException {

        FRMotor = hardwareMap.dcMotor.get("FRMotor");
        FLMotor = hardwareMap.dcMotor.get("FLMotor");
        BRMotor = hardwareMap.dcMotor.get("BRMotor");
        BLMotor = hardwareMap.dcMotor.get("BLMotor");

        FLMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        BLMotor.setDirection(DcMotorSimple.Direction.REVERSE);



        FRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BRMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BLMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        FRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BRMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BLMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();
        while (opModeIsActive())
        {
            telemetry.addData("FLPos", FLMotor.getCurrentPosition());
            telemetry.addData("BLPos", BLMotor.getCurrentPosition());
            telemetry.addData("BRPos", BRMotor.getCurrentPosition());
            telemetry.addData("FRPos", FRMotor.getCurrentPosition());
            telemetry.update();

        }

    }
}
