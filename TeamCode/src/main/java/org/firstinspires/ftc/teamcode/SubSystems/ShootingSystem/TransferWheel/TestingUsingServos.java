package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TestingUsingServos
{
    private static CRServo rightServo;
    private static CRServo leftServo;

    private static CRServo middleServo;



    public static void init (HardwareMap hardwareMap)
    {
        rightServo = hardwareMap.get(CRServo.class, "rightServo");
        leftServo = hardwareMap.get(CRServo.class, "leftServo");
        middleServo = hardwareMap.get(CRServo.class, "middleServo");

        rightServo.setDirection(CRServo.Direction.REVERSE);

    }

    public static void operate(double power)
    {
        rightServo.setPower(power);
        leftServo.setPower(power);
        middleServo.setPower(power);
    }

}
