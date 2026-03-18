package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.Utility;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.geometry.Pose2d;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.PinpointTurretHeadingPID;
import org.firstinspires.ftc.teamcode.TeleOps.Main.Blue;

import java.util.Random;

public class LocalizerClass
{
    public static GoBildaPinpointDriver pinpoint;
    public static Pose2D turretPose;

    public static void init (Pose2D pose2d , HardwareMap hardwareMap)
    {
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");

        configurePinpoint();

        pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, pose2d.getX(DistanceUnit.INCH), pose2d.getY(DistanceUnit.INCH), AngleUnit.DEGREES, pose2d.getHeading(AngleUnit.DEGREES)));
    }

    public static void calcTurretPose(Pose2D pinpointPos)
    {
        double turretY = pinpointPos.getX(DistanceUnit.INCH) + 2.31*Math.cos(pinpointPos.getHeading(AngleUnit.RADIANS)+180);
        double turretX = pinpointPos.getY(DistanceUnit.INCH) + 2.31*Math.sin(pinpointPos.getHeading(AngleUnit.RADIANS)+180);
        turretPose = new Pose2D(DistanceUnit.INCH, turretX, turretY, AngleUnit.DEGREES, Blue.wantedAngle);
    }

    public static void setTurretPose(Pose2D pose2d)
    {
        turretPose = pose2d;
    }

    public static void calcPinpointPose()
    {
        double pinpointY = turretPose.getX(DistanceUnit.INCH) + 2.31*Math.cos(turretPose.getHeading(AngleUnit.RADIANS));
        double pinpointX = turretPose.getY(DistanceUnit.INCH) + 2.31*Math.sin(turretPose.getHeading(AngleUnit.RADIANS));
        pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, pinpointX, pinpointY, AngleUnit.DEGREES, pinpoint.getHeading(AngleUnit.DEGREES)));
    }

    public static double blueGetDistance(Pose2d wantedPos)
    {
        return Math.sqrt(Math.pow(wantedPos.getX() - turretPose.getX(DistanceUnit.INCH), 2) + Math.pow(wantedPos.getY() - (turretPose.getY(DistanceUnit.INCH)*-1), 2));
    }

    public static double redGetDistance(Pose2d wantedPos)
    {
        return Math.sqrt(Math.pow(wantedPos.getX() - (turretPose.getX(DistanceUnit.INCH)*-1), 2) + Math.pow(wantedPos.getY() - turretPose.getY(DistanceUnit.INCH), 2));
    }

    public static double blueWantedTurretHeading(Pose2d wantedPos)
    {
        double wantedHeading = Math.toDegrees(Math.atan((wantedPos.getY() - (turretPose.getY(DistanceUnit.INCH)*-1))/(wantedPos.getX() - turretPose.getX(DistanceUnit.INCH))));

        double driveHeading = pinpoint.getHeading(AngleUnit.DEGREES);

        return 90 - wantedHeading + driveHeading;
    }

    public static double redWantedTurretHeading(Pose2d wantedPos)
    {
        double wantedHeading = Math.toDegrees(Math.atan((wantedPos.getY() - turretPose.getY(DistanceUnit.INCH))/(wantedPos.getX() - (turretPose.getX(DistanceUnit.INCH)*-1))));

        double driveHeading =  pinpoint.getHeading(AngleUnit.DEGREES);

        return (90 + wantedHeading)*-1 + driveHeading;
    }


    public static void configurePinpoint()
    {
        pinpoint.setOffsets(-76.0, -168.0, DistanceUnit.MM);

        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);

        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        pinpoint.resetPosAndIMU();
    }

    public static void telemetry(Telemetry telemetry)
    {
        telemetry.addData("turretX", turretPose.getX(DistanceUnit.INCH));
        telemetry.addData("turretY", turretPose.getY(DistanceUnit.INCH));
        telemetry.addData("turret angle", turretPose.getHeading(AngleUnit.DEGREES));
    }
}
