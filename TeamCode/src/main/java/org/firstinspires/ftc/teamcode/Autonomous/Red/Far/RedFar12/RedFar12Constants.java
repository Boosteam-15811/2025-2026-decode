package org.firstinspires.ftc.teamcode.Autonomous.Red.Far.RedFar12;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

public class RedFar12Constants {
    public static final Vector2d firstRow = new Vector2d(38, 30);
    public static final Vector2d collectFirstRow = new Vector2d(38, 60);

    public static final Pose2d startingPos = new Pose2d(62,14.5, Math.toRadians(90));
    public static final Vector2d shootingPos = new Vector2d(57,14.5);
    public static final Vector2d shootingPosClose = new Vector2d(-6,16);

    public static final Vector2d leave = new Vector2d(47,23);
    public static final Vector2d leaveClose = new Vector2d(12,16);
    public static final Vector2d secondRow = new Vector2d(14,30);
    public static final Vector2d collectSecondRow = new Vector2d(14,60);

    public static final Vector2d thirdRow = new Vector2d(-10,30);
    public static final Vector2d collectThirdRow = new Vector2d(-10,54);
    public static final Vector2d backThirdRow = new Vector2d(-10,50);
}
