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
            return (-0.05320367 + 0.006221939*distance - 0.00005216283*Math.pow(distance,2) + 1.45965e-7*Math.pow(distance,3));
        }
        return 0.195;
    }

    public static double calcSpeed(double distance)
    {
        if(distance > 120)
        {
            return ShootingSpeedConstants.launchZoneSpeed;
        }
        return (1803.786 + 25.08829*distance - 0.2966177*Math.pow(distance,2) + 0.001716616* Math.pow(distance,3));
    }

    public static void telemetry(Telemetry telemetry , double distance)
    {
        telemetry.addData("angle:", calcAngle(distance));
        telemetry.addData("speed:", calcSpeed(distance));
    }

}
