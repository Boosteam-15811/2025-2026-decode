package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed;

import com.acmerobotics.dashboard.config.Config;

@Config
public class ShootingSpeedConstants {
    public static double launchZoneSpeed = 3600;
    public static double atGoalSpeed = 2300;
    public static double farFromGoalSpeed = 3000;
    public static double disabledSpeed = 0;

    public static int farFromGoalTolerance = 100;
    public static int launchZoneTolerance = 130;
    public static int atGoalTolerance = 70;

    public static int dynamicTolerance = 200;

    public static int ejectionRPMThreshold = 0;
    public static final double tickToRPMRatio = 60.0 / 28.0 ;

    //old PIDF values yigal said to save
    //public static double p = 0.006 ,i = 0 ,d = 0.000001, f = 0.00025;

    public static double p = 0.006 ,i = 0 ,d = 0, fs = 0.1, fv = 0.00017;
}