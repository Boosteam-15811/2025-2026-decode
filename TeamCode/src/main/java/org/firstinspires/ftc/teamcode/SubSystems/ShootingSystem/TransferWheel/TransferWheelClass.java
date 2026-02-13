package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel;
import static org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass.masterShootingMotor;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass;
public class TransferWheelClass
{
    private static DcMotor transferWheel;

    public static void init(HardwareMap hardwareMap) {
        transferWheel = hardwareMap.get(DcMotor.class,"transferWheel");
    }
    public static void operate(double power) {
        transferWheel.setPower(power);
    }
    public static void telemetry(Telemetry telemetry)
    {
        telemetry.addData("transferPower:" , transferWheel.getPower());
    }
    public static class Activate implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if(ShootingSpeedClass.targetSpeed == 0)
            {
                operate(0);
            }
            else if (ShootingSpeedClass.targetSpeed - masterShootingMotor.getVelocity()*60/28 < 200) {
                operate(1);
            }
            else {
                operate(0);
            }
            return true;
        }
    }
    public static Action activate() {
        return new Activate();
    }
}