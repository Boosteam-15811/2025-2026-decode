package org.firstinspires.ftc.teamcode.Utility.DynamicShooting;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedConstants;

public class DynamicShootingClass
{


    public static double calcAngle(double distance)
    {
       return  (0.08212363 - 0.0002178571*distance + 0.00001565934*Math.pow(distance, 2));
    }

    public static double calcSpeed(double distance)
    {
        return (11.6044*distance + 1758.681);
    }

    public static void telemetry(Telemetry telemetry , double distance)
    {
        telemetry.addData("angle:", calcAngle(distance));
        telemetry.addData("speed:", calcSpeed(distance));
    }

}
