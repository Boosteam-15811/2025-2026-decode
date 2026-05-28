package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.Utility;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DynamicShootingClass
{


    public static double calcAngle(double distance)
    {
        if (distance < 65)
        {
            return (-0.2165414 + 0.01102256*distance - 0.00009022556*Math.pow(distance,2));
        }
        return 0.195;
    }

    public static double calcSpeed(double distance)
    {
        return (2504.249 - 21.57958*distance + 0.6219769*Math.pow(distance,2) - 0.003570041*Math.pow(distance,3));
    }

    public static void telemetry(Telemetry telemetry , double distance)
    {
        telemetry.addData("angle:", calcAngle(distance));
        telemetry.addData("speed:", calcSpeed(distance));
    }

}
