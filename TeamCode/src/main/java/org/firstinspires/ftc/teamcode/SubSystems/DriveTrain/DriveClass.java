package org.firstinspires.ftc.teamcode.SubSystems.DriveTrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class DriveClass
{
    private static DcMotor frMotor;
    private static DcMotor flMotor;
    private static DcMotor brMotor;
    private static DcMotor blMotor;


    public static void init(HardwareMap hardwareMap)
    {
        frMotor = hardwareMap.dcMotor.get("frMotor");
        flMotor = hardwareMap.dcMotor.get("flMotor");
        brMotor = hardwareMap.dcMotor.get("brMotor");
        blMotor = hardwareMap.dcMotor.get("blMotor");

        flMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        blMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        brMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        blMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public static void drive(double power)
    {
        frMotor.setPower(power);
        flMotor.setPower(power);
        brMotor.setPower(power);
        blMotor.setPower(power);
    }

    public static void arcade (double x, double y , double rx)
    {
        frMotor.setPower(y - x - rx);
        flMotor.setPower(y + x + rx);
        brMotor.setPower(y + x - rx);
        blMotor.setPower(y - x + rx);
    }

    public static void fieldArcade(double x, double y , double rx, IMU imu)
    {
        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);


        rotX = rotX * 1.1;

        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);

        frMotor.setPower((rotY - rotX - rx) / denominator);
        flMotor.setPower((rotY + rotX + rx) / denominator);
        brMotor.setPower((rotY + rotX - rx) / denominator);
        blMotor.setPower((rotY - rotX + rx) / denominator);
    }
}
