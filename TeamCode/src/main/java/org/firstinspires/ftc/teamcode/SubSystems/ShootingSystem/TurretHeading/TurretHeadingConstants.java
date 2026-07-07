package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading;

import com.acmerobotics.dashboard.config.Config;

@Config
public class TurretHeadingConstants
{
    public static double p = 0.2  , i = 0.00035 , d = 0 , f = 0.07;

    private static final double encoderResolution = 537.7;

    private static final double gearRatio = (74.0/10.0)*-1;

    public static final double degreeInTicks = (encoderResolution*gearRatio)/360;


    public static final double angleLimit = 180;

    public static final double flipAngle = 170;
    public static final double pidStopper = 0.1;


}
