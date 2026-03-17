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

import java.util.List;

public class CameraClass {
    public static Limelight3A limeLight3A;
    private static double distance;



    public static void init(HardwareMap hardwareMap) {
        limeLight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limeLight3A.pipelineSwitch(0);
    }

    public static boolean inDisTolerance(double distance)
    {
        return distance < 80 && distance > 40;
    }

    public static boolean cameraDetecting()
    {
        LLResult result = limeLight3A.getLatestResult();

        return result != null && result.isValid();
    }

    public static Pose2D calcTurretPose()
    {
            LLResult result = limeLight3A.getLatestResult();

            double robotX = result.getBotpose().getPosition().x + 6.5*Math.cos(PinpointTurretHeadingPID.getTrueAngle());
            double robotY = result.getBotpose().getPosition().y + 6.5*Math.sin(PinpointTurretHeadingPID.getTrueAngle());

            return new Pose2D(DistanceUnit.INCH, robotX, robotY, AngleUnit.DEGREES , PinpointTurretHeadingPID.getTrueAngle());
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
