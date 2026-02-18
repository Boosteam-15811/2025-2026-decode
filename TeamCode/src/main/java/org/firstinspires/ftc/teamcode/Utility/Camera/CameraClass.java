package org.firstinspires.ftc.teamcode.Utility.Camera;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

public class CameraClass {
    public static Limelight3A limeLight3A;
    private static double distance;

    public static void init(HardwareMap hardwareMap) {
        limeLight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limeLight3A.pipelineSwitch(0);
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
