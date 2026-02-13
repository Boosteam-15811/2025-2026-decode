package org.firstinspires.ftc.teamcode.SubSystems.IntakeSystem;
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
public class IntakeClass
{
    private static DcMotor intakeMotor;

    public static void init(HardwareMap hardwareMap)
    {
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");

        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakeMotor.setDirection(DcMotor.Direction.REVERSE);
    }
    public static void operate(double power)
    {
        intakeMotor.setPower(power);
    }

    public static void telemetry(Telemetry telemetry)
    {
        telemetry.addData("intakeMotor:", intakeMotor.getPower());
    }
    public static class Activate implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            operate(1);
            return false;
        }
    }
    public static Action activate() {
        return new Activate();
    }
    public static class Deactivate implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            operate(0);
            return false;
        }
    }
    public static Action deactivate() {
        return new Deactivate();
    }
}