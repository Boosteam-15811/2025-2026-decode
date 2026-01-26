package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.SubSystems.DriveTrain.DriveClass;

@TeleOp
@Disabled
public class TeleOpAlpha extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        DriveClass.init(hardwareMap);

        waitForStart();
        while (opModeIsActive())
        {
            if(gamepad1.right_trigger>0)
            {
                DriveClass.drive(gamepad1.right_trigger);
            }
            else if(gamepad1.left_trigger > 0)
            {
                DriveClass.drive(-gamepad1.left_trigger);
            }
            else
            {
                DriveClass.arcade(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);
            }
        }
    }
}
