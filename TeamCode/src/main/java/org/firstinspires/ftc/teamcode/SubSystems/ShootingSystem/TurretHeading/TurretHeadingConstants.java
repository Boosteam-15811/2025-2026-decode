package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading;

public class TurretHeadingConstants
{
    public static final double launchZonePos = 20;
    public static final double atGoalPos = 150;

    public static final double farFromGoalPos = 150;
    public static final double startPos = 0;

    public static double p = 0.0415 , i = 0 , d = 0.0016 , f = 0.05;

    private static final double encoderResolution = 537.7;

    private static final double gearRatio = 140.0/13;

    public static final double degreeInTicks = (encoderResolution*gearRatio)/360;


    public static final double angleLimit = 180;

    public static final double flipAngle = 170;
    public static final double pidStopper = 0.1;


}
