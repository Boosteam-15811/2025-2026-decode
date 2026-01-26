package org.firstinspires.ftc.teamcode.SubSystems.IntakeSystem;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class IntakeClass
{
    private static DcMotor IntakeMotor;

    public static void init(HardwareMap hardwareMap)
    {
        IntakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");

        IntakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        IntakeMotor.setDirection(DcMotor.Direction.REVERSE);
    }
    public static void operate(double power)
    {
        IntakeMotor.setPower(power);
    }

    public static void telemetry(Telemetry telemetry)
    {
        telemetry.addData("IntakeMotor:", IntakeMotor.getPower());
    }
}
