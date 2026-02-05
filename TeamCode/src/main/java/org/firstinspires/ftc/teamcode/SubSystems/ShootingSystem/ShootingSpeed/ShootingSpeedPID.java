package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed;

import static org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass.masterShootingMotor;
import static org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass.masterShootingMotorSpeed;
import static org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass.slaveShootingMotor;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.controller.PIDController;
import com.seattlesolvers.solverslib.controller.PIDFController;

import org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TransferWheel.TransferWheelClass;

public class ShootingSpeedPID
{
    private static PIDFController controller;

    public static void init(HardwareMap hardwareMap)
    {
        controller = new PIDFController(ShootingSpeedConstants.p, ShootingSpeedConstants.i , ShootingSpeedConstants.d,ShootingSpeedConstants.f);
    }

    public static double updateMotorOutput(double  targetSpeed, double currentSpeed)
    {
        controller.setPIDF(ShootingSpeedConstants.p, ShootingSpeedConstants.i , ShootingSpeedConstants.d,ShootingSpeedConstants.f);

        double pid = controller.calculate(currentSpeed , targetSpeed);

        double power = pid;

        if (targetSpeed < 1000)
        {
            return 0;
        }
        return power;
    }

    public static class PID implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            controller.setPIDF(ShootingSpeedConstants.p, ShootingSpeedConstants.i , ShootingSpeedConstants.d,ShootingSpeedConstants.f);

            double pid = controller.calculate(masterShootingMotor.getVelocity()*60/28, masterShootingMotorSpeed);

            if (masterShootingMotorSpeed < 1000)
            {
                masterShootingMotor.setMotorDisable();
                slaveShootingMotor.setMotorDisable();
            } else {
                masterShootingMotor.setPower(pid);
            }
            return true;
        }

    }
    public static Action pid() {
        return new PID();
    }
}
