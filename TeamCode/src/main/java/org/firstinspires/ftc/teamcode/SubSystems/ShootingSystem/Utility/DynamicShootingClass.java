package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.Utility;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DynamicShootingClass
{


    public static double calcDistance(double distance)
    {
        return  (0.08212363 - 0.0002178571*distance + 0.00001565934*Math.pow(distance, 2));
    }

    public static double calcSpeed(double distance)
    {
        return (11.6044*distance + 1758.681);
    }

    public static void telemetry(Telemetry telemetry , double distance)
    {
        telemetry.addData("angle:", calcDistance(distance));
        telemetry.addData("speed:", calcSpeed(distance));
    }

}
