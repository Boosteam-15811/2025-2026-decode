package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TransferWheelClass
{
    private static DcMotor transferWheel;

    public static void init(HardwareMap hardwareMap) {
        transferWheel = hardwareMap.dcMotor.get("transferWheel");

        transferWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public static void setPower(double power) {
        transferWheel.setPower(power);
    }
    public static void telemetry(Telemetry telemetry)
    {
        telemetry.addData("transferPower:" , transferWheel.getPower());
    }
}
