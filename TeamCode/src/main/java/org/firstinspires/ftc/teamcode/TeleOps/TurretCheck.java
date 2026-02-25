package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp
@Disabled
public class TurretCheck extends LinearOpMode
{
    public static DcMotor headingMotor;

    @Override
    public void runOpMode() throws InterruptedException
    {
        headingMotor = hardwareMap.dcMotor.get("headingMotor");

        headingMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        headingMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        waitForStart();
        while (opModeIsActive())
        {
            telemetry.addData("rotationNumber" , headingMotor.getCurrentPosition()/537.7);
            telemetry.update();
        }
    }
}
