package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.ShootingSpeed;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.controller.PIDController;

public class ShootingSpeedPID
{
    private static PIDController controller;

    public static void init(HardwareMap hardwareMap)
    {
        controller = new PIDController(ShootingSpeedConstants.p, ShootingSpeedConstants.i , ShootingSpeedConstants.d);
    }

    public static double updateMotorOutput(double  targetSpeed, double currentSpeed)
    {
        controller.setPID(ShootingSpeedConstants.p, ShootingSpeedConstants.i , ShootingSpeedConstants.d);

        double pid = controller.calculate(currentSpeed , targetSpeed);

        double power = pid;

        if (targetSpeed < 1000)
        {
            return 0;
        }
        return power;
    }
}
