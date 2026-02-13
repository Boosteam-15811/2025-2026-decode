package org.firstinspires.ftc.teamcode.Utiity.Camera;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class CameraClass {
    public static Limelight3A limeLight3A;
    private static double distance;

    public static void init(HardwareMap hardwareMap) {
        limeLight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limeLight3A.pipelineSwitch(0);
    }

    public static double getDistanceFromTag(double ta) {
        double scale = 1212.627;
        return Math.pow((scale / ta), (1 / 1.356486));
    }

    public static void telemetry(Telemetry telemetry) {
        LLResult llResult = limeLight3A.getLatestResult();
        if (llResult != null && llResult.isValid()) {
            distance = getDistanceFromTag(llResult.getTa());

            telemetry.addData("Calculated Distance", distance);
            telemetry.addData("Target X", llResult.getTx());
            telemetry.addData("Target Area", llResult.getTa());
            telemetry.update();
        }
    }

}
