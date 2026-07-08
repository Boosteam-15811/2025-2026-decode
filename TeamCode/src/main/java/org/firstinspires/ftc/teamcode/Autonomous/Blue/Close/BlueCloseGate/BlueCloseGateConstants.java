package org.firstinspires.ftc.teamcode.Autonomous.Blue.Close.BlueCloseGate;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
@Config
public class BlueCloseGateConstants
{
    public static Pose2d startingPos = new Pose2d(-39,-55, Math.toRadians(270));

    public static Vector2d startShootingPos = new Vector2d(-16,-16);

    public static final Vector2d secondRow = new Vector2d(16,-30);
    public static final Vector2d collectSecondRow = new Vector2d(16,-60);
    public static final Vector2d shootingPos = new Vector2d(-6,-16);
    public static Vector2d collectGate = new Vector2d(16,-62);
    public static Vector2d Back = new Vector2d(16,-52);
    public static final Vector2d thirdRow = new Vector2d(-13, -30);
    public static final Vector2d collectThirdRow = new Vector2d(-13, -56);
    public static final Vector2d leave = new Vector2d(10,-18);

}
