package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.Utility;

import android.net.MacAddress;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedConstants;
import org.opencv.core.Mat;

public class DynamicShootingClass
{


    public static double calcAngle(double distance)
    {
        if (distance < 70)
        {
            return (0.57 - 0.03733333*distance + 0.0009*Math.pow(distance,2) - 0.000006666667*Math.pow(distance,3));
        }
        return 0.195;
    }

    public static double calcSpeed(double distance)
    {
        if(distance > 120)
        {
            return ShootingSpeedConstants.launchZoneSpeed;
        }
        return (2050.971 + 13.07563*distance - 0.0534188*Math.pow(distance,2) + 0.0002266252* Math.pow(distance,3));
    }

    public static void telemetry(Telemetry telemetry , double distance)
    {
        telemetry.addData("angle:", calcAngle(distance));
        telemetry.addData("speed:", calcSpeed(distance));
    }

}
