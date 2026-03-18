package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.Utility;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.PinpointTurretHeadingPID;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.TurretHeadingClass;
import org.firstinspires.ftc.teamcode.TeleOps.Main.Blue;

import java.util.List;

public class CameraClass {
    public static Limelight3A limeLight3A;
    private static double distance;

    private static final int minDisTolerance = 40;
    private static final int maxDisTolerance = 80;


    public static void init(HardwareMap hardwareMap) {
        limeLight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limeLight3A.pipelineSwitch(0);
    }

    public static boolean inDisTolerance(double distance)
    {
        return distance < maxDisTolerance && distance > minDisTolerance;
    }

    public static boolean cameraDetecting()
    {
        LLResult result = limeLight3A.getLatestResult();

        return result != null && result.isValid();
    }

    public static Pose2D calcTurretPose()
    {
            LLResult result = limeLight3A.getLatestResult();

            double robotX = result.getBotpose().getPosition().x + 6.5*Math.cos(Blue.wantedAngle)+180;
            double robotY = result.getBotpose().getPosition().y + 6.5*Math.sin(Blue.wantedAngle)+180;

            return new Pose2D(DistanceUnit.INCH, robotX, robotY, AngleUnit.DEGREES , Blue.wantedAngle);
    }


    public static boolean compareID(int wantedID)
    {
        LLResult result = limeLight3A.getLatestResult();

        List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();
        for (LLResultTypes.FiducialResult fiducial : fiducials) {
            int id = fiducial.getFiducialId();

            if (id == wantedID) {
                return true;
            }
        }
        return false;
    }

}
