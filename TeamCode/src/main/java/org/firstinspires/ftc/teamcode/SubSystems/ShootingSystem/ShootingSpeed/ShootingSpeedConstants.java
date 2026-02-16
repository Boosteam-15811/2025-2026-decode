package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed;

import com.acmerobotics.dashboard.config.Config;

@Config
public class ShootingSpeedConstants {
    public static final double launchZoneSpeed = 3350;
    public static final double atGoalSpeed = 2300;
    public static final double farFromGoalSpeed = 2700;
    public static final double disabledSpeed = 0;

    public static int tolerance = 70;
    public static int atGoalTolerance = 70;
    public static final double tickToRPMRatio = 60.0 / 28.0;

    public static double p = 0.0018 ,i = 0 ,d = 0.000001, f = 0.00025;

}

