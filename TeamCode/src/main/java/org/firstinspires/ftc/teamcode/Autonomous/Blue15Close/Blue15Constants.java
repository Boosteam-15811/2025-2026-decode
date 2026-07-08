package org.firstinspires.ftc.teamcode.Autonomous.Blue15Close;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

public class Blue15Constants
{
    public static final Pose2d startingPos = new Pose2d(-38,-55, Math.toRadians(270));

    public static final Vector2d startShootingPos = new Vector2d(-16,-16);

    public static final Vector2d secondRow = new Vector2d(14,-30);
    public static final Vector2d collectSecondRow = new Vector2d(14,-60);
    public static final Vector2d firstRow = new Vector2d(36, -30);
    public static final Vector2d collectFirstRow = new Vector2d(36, -60);
    public static final Vector2d shootingPos = new Vector2d(-6,-16);
    public static final Vector2d shootingPosThird = new Vector2d(-10,-16);

    public static final Vector2d thirdRow = new Vector2d(-4, -30);
    public static final Vector2d collectThirdRow = new Vector2d(-4, -60);
    public static final Vector2d leave = new Vector2d(7,-16);
}
