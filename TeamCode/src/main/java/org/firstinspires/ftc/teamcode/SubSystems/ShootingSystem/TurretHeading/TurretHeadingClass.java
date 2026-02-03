package org.firstinspires.ftc.teamcode.SubSystems.ShootingSystem.TurretHeading;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TurretHeadingClass
{
    public static DcMotor headingMotor;

    public static double headingAngle;

    public static void init(HardwareMap hardwareMap)
    {
        headingMotor = hardwareMap.dcMotor.get("headingMotor");

        headingMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public static void operate(TurretHeadingStates turretHeadingStates)
    {
        switch(turretHeadingStates)
        {
            default:
            case LAUNCHZONE: {
                headingAngle = TurretHeadingConstants.launchZonePos;
                break;
            }
            case ATGOAL:
            {
                headingAngle = TurretHeadingConstants.atGoalPos;
                break;
            }
            case FARFROMGOAL: {
                headingAngle = TurretHeadingConstants.farFromGoalPos;
                break;
            }
            case START:
            {
                headingAngle = TurretHeadingConstants.startPos;
                break;
            }
        }
       headingMotor.setPower(TurretHeadingPID.moveToPos(headingAngle, headingMotor.getCurrentPosition()));
    }

    public static boolean inPosition (TurretHeadingStates turretHeadingStates)
    {
        double pos;
        switch (turretHeadingStates)
        {
            default:
            case LAUNCHZONE:
            {
                pos = TurretHeadingConstants.launchZonePos;
                break;
            }
            case ATGOAL:
            {
                pos = TurretHeadingConstants.atGoalPos;
                break;
            }
            case FARFROMGOAL: {
                pos = TurretHeadingConstants.farFromGoalPos;
                break;
            }
            case START:
            {
                pos = TurretHeadingConstants.startPos;
                break;
            }
        }
        return pos == headingMotor.getCurrentPosition();
    }

    public static void telemetry(Telemetry telemetry)
    {
        telemetry.addData("headingMotorPos" , headingMotor.getCurrentPosition());
    }

    public static class AtGoal implements Action
    {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            headingAngle = TurretHeadingConstants.atGoalPos;
            return false;
        }
    }
    public static Action atGoal()
    {
        return new AtGoal();
    }

    public static class FarFromGoal implements Action
    {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            headingAngle = TurretHeadingConstants.farFromGoalPos;
            return false;
        }
    }
    public static Action farFromGoal()
    {
        return new FarFromGoal();
    }

    public static class LaunchZone implements Action
    {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            headingAngle = TurretHeadingConstants.launchZonePos;
            return false;
        }
    }
    public static Action launchZone()
    {
        return new LaunchZone();
    }

    public static class Start implements Action
    {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            headingAngle = TurretHeadingConstants.startPos;
            return false;
        }
    }
    public static Action start()
    {
        return new Start();
    }
}
