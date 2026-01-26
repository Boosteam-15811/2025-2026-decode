package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.controller.PIDController;

public class TurretHeadingPID
{
    private static PIDController controller;



    public static void init(HardwareMap hardwareMap)
    {
        controller = new PIDController(TurretHeadingConstants.p, TurretHeadingConstants.i , TurretHeadingConstants.d);
    }

    public static double moveToPos(double angle , double motorPos)
    {
        controller.setPID(TurretHeadingConstants.p, TurretHeadingConstants.i , TurretHeadingConstants.d);
        double pid =controller.calculate(motorPos, angleToTicks(angle));

        double power = pid;

        return power;
    }
    public static double angleToTicks(double angle)
    {
        return angle*TurretHeadingConstants.degreeInTicks;
    }

}
