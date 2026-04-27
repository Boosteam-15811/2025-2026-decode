package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Vector2d;
import com.seattlesolvers.solverslib.geometry.Pose2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.Utility.DynamicShootingClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.Utility.LocalizerClass;

public class GlobalData
{
    public static double ShootHumanPlayerDis = calcDistance(new Pose2D(DistanceUnit.INCH, 14.5,62, AngleUnit.DEGREES, 0));
    public static double blueShootHumanPlayerAngle = calcWantedAngleBlue(new Pose2D(DistanceUnit.INCH, 14.5,62, AngleUnit.DEGREES, 0));

    public static double ShootClose1Dis = calcDistance(new Pose2D(DistanceUnit.INCH, 16,-16, AngleUnit.DEGREES, 0));
    public static double blueShootClose1Angle = calcWantedAngleBlue(new Pose2D(DistanceUnit.INCH, 16,-16, AngleUnit.DEGREES, 0));

    public static double ShootClose2Dis = calcDistance(new Pose2D(DistanceUnit.INCH, 16,0 , AngleUnit.DEGREES , 0));
    public static double blueShootClose2Angle = calcWantedAngleBlue(new Pose2D(DistanceUnit.INCH, 16,0 , AngleUnit.DEGREES , 0));


    //public static double redShootHumanPlayerDis = calcDistanceRed(new Pose2D(DistanceUnit.INCH,14.5,-62 , AngleUnit.DEGREES , 0));
    public static double redShootHumanPlayerAngle = calcWantedAngleRed(new Pose2D(DistanceUnit.INCH,14.5,-62,AngleUnit.DEGREES, 0));

    //public static double redShootClose1Dis = calcDistanceRed(new Pose2D(DistanceUnit.INCH,16,16 , AngleUnit.DEGREES , 0));
    public static double redShootClose1Angle = calcWantedAngleRed(new Pose2D(DistanceUnit.INCH,16,16 , AngleUnit.DEGREES , 0));

    //public static double redShootClose2Dis = calcDistanceRed(new Pose2D(DistanceUnit.INCH,16,0 , AngleUnit.DEGREES , 0));
    public static double redShootClose2Angle = calcWantedAngleRed(new Pose2D(DistanceUnit.INCH,16,0 , AngleUnit.DEGREES , 0));

    public static double currentDistance = 0;
    public static double currentAngle = 0;


    public static double calcDistance(Pose2D roadRunnerPos)
    {
        LocalizerClass.calcTurretPose(roadRunnerPos);
        return LocalizerClass.blueGetDistance(new Pose2d(-72,-72,Math.toRadians(0)));
    }

    public static double calcDistanceRed(Pose2D roadRunnerPos)
    {
        LocalizerClass.calcTurretPose(roadRunnerPos);
        return LocalizerClass.redGetDistance(new Pose2d(-72,72,Math.toRadians(0)));
    }

    public static double calcWantedAngleBlue(Pose2D roadRunnerPos)
    {
        LocalizerClass.calcTurretPose(roadRunnerPos);
        return LocalizerClass.blueWantedTurretHeading(new Pose2d(-72, -72, Math.toRadians(0)));
    }
    public static double calcWantedAngleRed(Pose2D roadRunnerPos)
    {
        LocalizerClass.calcTurretPose(roadRunnerPos);
        return LocalizerClass.redWantedTurretHeading(new Pose2d(-72, 72, Math.toRadians(0)));
    }



}
