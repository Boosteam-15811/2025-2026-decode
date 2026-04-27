package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.Gate;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class GateClass
{
    private static Servo Gate;
    private static double servoPos;
    private static boolean lastChange = false;

    public static void init(HardwareMap hardwareMap)
    {
        Gate = hardwareMap.get(Servo.class, "Gate");
        close();
    }

    public static void operate(double pos)
    {
        Gate.setPosition(pos);
    }

    public static void close()
    {
        operate(GateClassConstants.closePos);
    }
    public static void open()
    {
        operate(GateClassConstants.openPos);
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

        Gate.setPosition(servoPos);
    }

    public static void telemetry (Telemetry telemetry)
    {
        telemetry.addData("GatePos" , Gate.getPosition());
    }
}
