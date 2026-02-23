package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading;

import static org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading.TurretHeadingClass.headingMotor;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.controller.PIDController;

public class PinpointTurretHeadingPID
{
    private static PIDController controller;
    private static double trueAngle = 0;
    private static double power = 0;



    public static void init(HardwareMap hardwareMap)
    {
        controller = new PIDController(TurretHeadingConstants.p, TurretHeadingConstants.i , TurretHeadingConstants.d);
    }


    public static double moveToPos(double wantedAngle)
    {
        controller.setPID(TurretHeadingConstants.p, TurretHeadingConstants.i , TurretHeadingConstants.d);

        double motorAngle = headingMotor.getCurrentPosition()/TurretHeadingConstants.degreeInTicks;

        if (wantedAngle > 180)
        {
            trueAngle = wantedAngle - 360;
        }
        else if (wantedAngle < -180)
        {
            trueAngle = 360 + wantedAngle;
        }
        else
        {
            trueAngle = wantedAngle;
        }

        double pid = controller.calculate(motorAngle, trueAngle);

        power = pid;

        return power + (TurretHeadingConstants.f * Math.signum(trueAngle));
    }
}
