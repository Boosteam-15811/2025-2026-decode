package org.firstinspires.ftc.teamcode.Utiity.DynamicShooting;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DynamicShootingClass
{


    public static double calcAngle(double distance)
    {
        return (0.06139407 + (0.001190078*distance) - (0.000001466979*Math.pow(distance, 2)) -  (5.439217*Math.pow(10, -9)*Math.pow(distance, 3)));
    }

    public static double calcSpeed(double distance)
    {
        return (3.92188*distance + 2249.057);
    }

    public static void telemetry(Telemetry telemetry , double distance)
    {
        telemetry.addData("angle:", calcAngle(distance));
        telemetry.addData("speed:", calcSpeed(distance));
    }
}
