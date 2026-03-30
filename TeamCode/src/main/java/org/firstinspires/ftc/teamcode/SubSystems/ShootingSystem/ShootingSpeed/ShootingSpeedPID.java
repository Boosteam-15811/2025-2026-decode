package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed;

import static org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass.masterShootingMotor;
import static org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed.ShootingSpeedClass.targetSpeed;
import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.controller.PIDController;
import com.seattlesolvers.solverslib.controller.PIDFController;

public class ShootingSpeedPID
{
    private static PIDController controller;

    public static void init(HardwareMap hardwareMap)
    {
        controller = new PIDController(ShootingSpeedConstants.p, ShootingSpeedConstants.i , ShootingSpeedConstants.d);
    }

    public static double updateMotorOutput(double targetSpeed, double currentSpeed)
    {
        controller.setPID(ShootingSpeedConstants.p, ShootingSpeedConstants.i , ShootingSpeedConstants.d);

        double pid = controller.calculate(currentSpeed , targetSpeed);

        double power = pid + ShootingSpeedConstants.fs + ShootingSpeedConstants.fv*targetSpeed;

        return power;
    }

    public static class PID implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            controller.setPID(ShootingSpeedConstants.p, ShootingSpeedConstants.i , ShootingSpeedConstants.d);

            double pid = controller.calculate(masterShootingMotor.getVelocity()*ShootingSpeedConstants.tickToRPMRatio, targetSpeed);

            if (targetSpeed == 0)
            {
                masterShootingMotor.setMotorDisable();
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
