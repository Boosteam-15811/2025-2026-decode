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
            return (-0.07393958 + 0.009568759*distance - 0.00008260177*Math.pow(distance,2) + 2.316434e-7*Math.pow(distance,3));
        }
        return 0.285;
    }

    public static double calcSpeed(double distance)
    {
        if(distance > 135)
        {
            return ShootingSpeedConstants.launchZoneSpeed;
        }
        return (2223.466 - 0.9785007*distance + 0.1198177*Math.pow(distance,2) - 0.0003205128* Math.pow(distance,3));
    }

    public static void telemetry(Telemetry telemetry , double distance)
    {
        telemetry.addData("angle:", calcAngle(distance));
        telemetry.addData("speed:", calcSpeed(distance));
    }

}
