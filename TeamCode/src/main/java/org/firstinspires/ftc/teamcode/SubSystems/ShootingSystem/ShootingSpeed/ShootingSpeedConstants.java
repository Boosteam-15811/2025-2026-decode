package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed;

public class ShootingSpeedConstants {
    public static final double launchZoneSpeed = 3400;
    public static final double atGoalSpeed = 2100;
    public static final double farFromGoalSpeed = 2950;
    public static final double disabledSpeed = 0;

    public static final int tolerance = 500;
    public static final int atGoalTolerance = 200;
    public static final double tickToRPMRatio = 60.0 / 28.0;

    public static double p = 0.0015, i = 0, d = 0 , f = 0.00021375;

}

