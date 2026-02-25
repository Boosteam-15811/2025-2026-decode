package org.firstinspires.ftc.teamcode.Utility.Camera;

import android.text.style.IconMarginSpan;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.teamcode.PinpointLocalizer;

import java.util.List;

public class CameraClass {
    public static Limelight3A limeLight3A;
    private static double distance;

    public static void init(HardwareMap hardwareMap) {
        limeLight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limeLight3A.pipelineSwitch(0);
    }

    public static Pose2D getLimelightRobotPose(double turretAngle , Pose2D turretPose , Pose2D robotPose)
    {
        Pose2D limelightPose = new Pose2D(DistanceUnit.INCH, limeLight3A.getLatestResult().getBotpose().getPosition().x*39.3701 , limeLight3A.getLatestResult().getBotpose().getPosition().y*39.3701 , AngleUnit.DEGREES , turretAngle);
        double limelightTurretXOffset =  limelightPose.getX(DistanceUnit.INCH)+ Math.cos(turretAngle)*6.57283;
        double limelightTurretYOffset =  limelightPose.getY(DistanceUnit.INCH)+ Math.sin(turretAngle)*6.57283;
        Pose2D limelightTurretOffset = new Pose2D(DistanceUnit.INCH, limelightTurretXOffset, limelightTurretYOffset, AngleUnit.DEGREES, turretAngle);

        Pose2D finalPose = new Pose2D(DistanceUnit.INCH, limelightPose.getX(DistanceUnit.INCH)+limelightTurretOffset.getX(DistanceUnit.INCH) - turretPose.getX(DistanceUnit.INCH) , limelightPose.getY(DistanceUnit.INCH)+limelightTurretOffset.getY(DistanceUnit.INCH) - turretPose.getY(DistanceUnit.INCH) , AngleUnit.DEGREES , robotPose.getHeading(AngleUnit.DEGREES));

        return finalPose;
    }

    public static double getDistanceFromTag(double ta) {
        double scale = 3040.267;
        return Math.pow((scale / ta), (1 / 1.54168));
    }

    public static void telemetry(Telemetry telemetry , int wantedID) {
        LLResult llResult = limeLight3A.getLatestResult();
        if (llResult != null && llResult.isValid() && compareID(wantedID)) {
            distance = getDistanceFromTag(llResult.getTa());

            telemetry.addData("Calculated Distance", distance);
            telemetry.addData("Target X", llResult.getTx());
            telemetry.addData("Target Area", llResult.getTa());
            telemetry.update();
        }
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
