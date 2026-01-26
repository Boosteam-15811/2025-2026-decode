package org.firstinspires.ftc.teamcode.SubSystems.DriveTrain;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class DriveClass
{
    private static DcMotor FRMotor;
    private static DcMotor FLMotor;
    private static DcMotor BRMotor;
    private static DcMotor BLMotor;


    public static void init(HardwareMap hardwareMap)
    {
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
    }

    public static void drive(double power)
    {
        FRMotor.setPower(power);
        FLMotor.setPower(power);
        BRMotor.setPower(power);
        BLMotor.setPower(power);
    }

    public static void arcade (double x, double y , double rx)
    {
        FRMotor.setPower(y - x - rx);
        FLMotor.setPower(y + x + rx);
        BRMotor.setPower(y + x - rx);
        BLMotor.setPower(y - x + rx);
    }

    public static void fieldArcade(double x, double y , double rx, IMU imu)
    {
        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);


        rotX = rotX * 1.1;

        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);

        FRMotor.setPower((rotY - rotX - rx) / denominator);
        FLMotor.setPower((rotY + rotX + rx) / denominator);
        BRMotor.setPower((rotY + rotX - rx) / denominator);
        BLMotor.setPower((rotY - rotX + rx) / denominator);
    }
}
