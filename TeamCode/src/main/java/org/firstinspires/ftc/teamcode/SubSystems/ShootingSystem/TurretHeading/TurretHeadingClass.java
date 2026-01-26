package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TurretHeadingClass
{
    private static DcMotor HeadingMotor;

    private static double HeadingPos;

    public static void init(HardwareMap hardwareMap)
    {
        HeadingMotor = hardwareMap.dcMotor.get("headingMotor");

        HeadingMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public static void operate(TurretHeadingStates turretHeadingStates)
    {
        switch(turretHeadingStates)
        {
            default:
            case LAUNCHZONE:
                HeadingPos = TurretHeadingConstants.LaunchZonePos;
                break;
            case GOAL:
                HeadingPos = TurretHeadingConstants.GoalPos;
                break;
        }
       HeadingMotor.setPower(TurretHeadingPID.moveToPos(HeadingPos , HeadingMotor.getCurrentPosition()));
    }

    public static boolean inPosition (TurretHeadingStates turretHeadingStates)
    {
        double pos;
        switch (turretHeadingStates)
        {
            default:
            case LAUNCHZONE:
            {
                pos = TurretHeadingConstants.LaunchZonePos;
                break;
            }
            case GOAL:
            {
                pos = TurretHeadingConstants.GoalPos;
                break;
            }
        }
        return pos == HeadingMotor.getCurrentPosition();
    }

    public static void Telemetry (Telemetry telemetry)
    {
        telemetry.addData("headingMotorPos" , HeadingMotor.getCurrentPosition());
    }
}
