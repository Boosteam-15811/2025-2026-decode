package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingAngle;


import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class HoodAngleClass
{
    private static Servo hoodServo;
    private static double servoPos;

    private static boolean lastChange = false;


    public static void init(HardwareMap hardwareMap)
    {
        hoodServo = hardwareMap.servo.get("hoodServo");

        hoodServo.setDirection(Servo.Direction.REVERSE);
    }

    public static void operate(HoodAngleStates hoodAngleState)
    {
        switch (hoodAngleState)
        {
            case ATGOAL:
                servoPos = HoodAngleConstants.atGoalPos;
                break;
            case LAUNCHZONE:
                servoPos = HoodAngleConstants.launchZonePos;
                break;
            case FARFROMGOAL:
                servoPos = HoodAngleConstants.farFromGoalPos;
                break;
            case DISABLED:
                servoPos = HoodAngleConstants.disabledPos;
                break;
        }
        hoodServo.setPosition(servoPos);
    }


    public static void test(Gamepad gamepad)
    {
        if (gamepad.dpad_up)
        {
            if (!lastChange)
            {
                servoPos +=0.02;
                lastChange = true;
            }
        }
        else if(gamepad.dpad_down)
        {
            if (!lastChange)
            {
                servoPos -=0.02;
                lastChange = true;
            }
        }
        else
        {
            lastChange = false;
        }

        hoodServo.setPosition(servoPos);
    }

    public static void telemetry(Telemetry telemetry)
    {
        telemetry.addData("hoodServoAngle:" , hoodServo.getPosition());
    }
}
