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


    public static void init(HardwareMap hardwareMap)
    {
        headingMotor = hardwareMap.dcMotor.get("headingMotor");

        headingMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public static void operate(double tx)
    {
       headingMotor.setPower(TurretHeadingPID.moveToPos(tx));
    }

    public static boolean inPosition ()
    {
        return (0.05 > TurretHeadingPID.moveToPos(0));
    }

    public static void telemetry(Telemetry telemetry)
    {
        telemetry.addData("headingMotorPos" , headingMotor.getCurrentPosition());
    }

//    public static class AtGoal implements Action
//    {
//        @Override
//        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//            headingAngle = TurretHeadingConstants.atGoalPos;
//            return false;
//        }
//    }
//    public static Action atGoal()
//    {
//        return new AtGoal();
//    }
//
//    public static class FarFromGoal implements Action
//    {
//        @Override
//        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//            headingAngle = TurretHeadingConstants.farFromGoalPos;
//            return false;
//        }
//    }
//    public static Action farFromGoal()
//    {
//        return new FarFromGoal();
//    }
//
//    public static class LaunchZone implements Action
//    {
//        @Override
//        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//            headingAngle = TurretHeadingConstants.launchZonePos;
//            return false;
//        }
//    }
//    public static Action launchZone()
//    {
//        return new LaunchZone();
//    }
//
//    public static class Start implements Action
//    {
//        @Override
//        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
//            headingAngle = TurretHeadingConstants.startPos;
//            return false;
//        }
//    }
//    public static Action start()
//    {
//        return new Start();
//    }
}
