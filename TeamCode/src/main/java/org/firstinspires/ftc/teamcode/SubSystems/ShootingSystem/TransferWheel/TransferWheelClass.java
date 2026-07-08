package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel;
import static org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass.masterShootingMotor;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedConstants;

public class TransferWheelClass
{
    private static CRServo rightTransferWheel;
    private static CRServo leftTransferWheel;


    public static void init(HardwareMap hardwareMap) {
        rightTransferWheel = hardwareMap.get(CRServo.class,"rightTransferWheel");
        leftTransferWheel = hardwareMap.get(CRServo.class, "leftTransferWheel");

        rightTransferWheel.setDirection(CRServo.Direction.REVERSE);
    }
    public static void operate(double power) {
        rightTransferWheel.setPower(power);
        leftTransferWheel.setPower(power);
    }
    public static void telemetry(Telemetry telemetry)
    {
        telemetry.addData("rightTransferWheel", rightTransferWheel.getPower());
        telemetry.addData("leftTransferWheel", leftTransferWheel.getPower());
    }
    public static class Activate implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if(ShootingSpeedClass.targetSpeed < 2650)
            {
                operate(-1);
            }
            else if (ShootingSpeedClass.inTolerence(ShootingSpeedClass.targetSpeed, ShootingSpeedConstants.dynamicTolerance)) {
                operate(1);
            }
            else
            {
                operate(-1);
            }
            return true;
        }
    }
    public static Action activate() {
        return new Activate();
    }
}