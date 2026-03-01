package org.firstinspires.ftc.teamcode.Utility;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.geometry.Pose2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

public class LocalizerClass
{
    public static GoBildaPinpointDriver pinpoint;

    public static void init (Pose2D pose2d , HardwareMap hardwareMap)
    {
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");

        configurePinpoint();

        pinpoint.setPosition(new Pose2D(DistanceUnit.INCH, pose2d.getX(DistanceUnit.INCH), pose2d.getY(DistanceUnit.INCH), AngleUnit.DEGREES, pose2d.getHeading(AngleUnit.DEGREES)));
    }

    public static double blueGetDistance(Pose2d wantedPos , Pose2D pinpointPos)
    {
        return Math.sqrt(Math.pow(wantedPos.getX() - pinpointPos.getY(DistanceUnit.INCH), 2) + Math.pow(wantedPos.getY() - (pinpointPos.getX(DistanceUnit.INCH)*-1), 2));
    }

    public static double redGetDistance(Pose2d wantedPos , Pose2D pinpointPos)
    {
        return Math.sqrt(Math.pow(wantedPos.getX() - (pinpointPos.getY(DistanceUnit.INCH)*-1), 2) + Math.pow(wantedPos.getY() - pinpointPos.getX(DistanceUnit.INCH), 2));
    }

    public static double blueWantedTurretHeading(Pose2d wantedPos , Pose2D pinpointPos)
    {
        double wantedHeading = Math.toDegrees(Math.atan((wantedPos.getY() - (pinpointPos.getX(DistanceUnit.INCH)*-1))/(wantedPos.getX() - pinpointPos.getY(DistanceUnit.INCH))));

        double driveHeading = pinpointPos.getHeading(AngleUnit.DEGREES);

        return 90 - wantedHeading + driveHeading;
    }

    public static double redWantedTurretHeading(Pose2d wantedPos , Pose2D pinpointPos)
    {
        double wantedHeading = Math.toDegrees(Math.atan((wantedPos.getY() - pinpointPos.getX(DistanceUnit.INCH))/(wantedPos.getX() - (pinpointPos.getY(DistanceUnit.INCH)*-1))));

        double driveHeading =  pinpointPos.getHeading(AngleUnit.DEGREES);

        return (90 + wantedHeading)*-1 + driveHeading;
    }


    public static void configurePinpoint()
    {
        pinpoint.setOffsets(-72.5, -182.5, DistanceUnit.MM);

        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);

        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        pinpoint.resetPosAndIMU();
    }
}
