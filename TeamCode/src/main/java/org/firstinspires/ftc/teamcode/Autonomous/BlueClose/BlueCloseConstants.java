package org.firstinspires.ftc.teamcode.Autonomous.BlueClose;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
@Config
public class BlueCloseConstants
{
    public static Pose2d startingPos = new Pose2d(-38,-55, Math.toRadians(270));

    public static Vector2d startShootingPos = new Vector2d(-16,-16);

    public static Vector2d secondRow = new Vector2d(12,-30);
    public static Vector2d collectSecondRow = new Vector2d(12,-54);
    public static Vector2d shootingPos = new Vector2d(0,-16);
    public static Vector2d collectGate = new Vector2d(12,-58.5);
    public static Vector2d Back = new Vector2d(12,-56);
    public static Vector2d leave = new Vector2d(7,-16);

}
